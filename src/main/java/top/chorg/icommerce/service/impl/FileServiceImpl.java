package top.chorg.icommerce.service.impl;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.chorg.icommerce.bean.dto.ApiResponse;
import top.chorg.icommerce.bean.dto.FileUploadResult;
import top.chorg.icommerce.common.ResponseMaker;
import top.chorg.icommerce.common.StringRules;
import top.chorg.icommerce.common.enums.UserType;
import top.chorg.icommerce.common.utils.MD5;
import top.chorg.icommerce.config.StorageSettings;
import top.chorg.icommerce.dao.FileDao;
import top.chorg.icommerce.service.FileService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);

    private final FileDao fileDao;

    // Note that this 'resources' is not the project directory, but the execution directory.
    private final File uploadPath;
    private final File avatarPath;
    private final File adminAvatarPath;
    private final File customerAvatarPath;

    public void checkDirectory(File directory) {
        if (!directory.exists()) {
            if (directory.mkdirs()) LOG.info("文件存储目录不存在，已自动创建（" + directory.getAbsolutePath() + ")");
            else {
                LOG.error("文件存储目录创建失败（{}）", directory.getAbsolutePath());
            }
        } else {
            LOG.info("已确认文件存储目录正常（{}）", directory.getAbsolutePath());
        }
    }

    public FileServiceImpl(FileDao fileDao, StorageSettings storageSettings) {
        this.fileDao = fileDao;
        System.out.println(storageSettings.getUploadPath());
        this.uploadPath = new File(storageSettings.getBasePath(), storageSettings.getUploadPath());
        this.avatarPath = new File(storageSettings.getBasePath(), storageSettings.getAvatarsPath());
        checkDirectory(this.uploadPath);
        checkDirectory(this.avatarPath);
        adminAvatarPath = new File(avatarPath, "admin");
        customerAvatarPath = new File(avatarPath, "customer");
        checkDirectory(this.adminAvatarPath);
        checkDirectory(this.customerAvatarPath);
    }

    @Override
    public ApiResponse<FileUploadResult> upload(int adminId, MultipartFile file) {
        if (!uploadPath.exists()) {
            LOG.error("文件存储目录不存在，无法使用文件上传功能（设置为\"{}\"，应用开始时会自动检查并创建，请检查设置目录权限）", uploadPath.getAbsolutePath());
            return ResponseMaker.error(-1, "服务器内部错误(-1)", null);
        }
        if (adminId < 0) {
            LOG.debug("User doesn't have auth access. (adminId = {})", adminId);
            return ResponseMaker.error(100, "用户未登录或无权限", null);
        }
        if (file.isEmpty()) {
            LOG.debug("Got empty upload result.");
            return ResponseMaker.error(1, "上传失败，文件为空", null);
        }
        String fileUploadName = file.getOriginalFilename();

        String fileCode = fileDao.insertFileRecord(fileUploadName, adminId); // AKA file code.
        // The file code is also the name of storage name.
        if (fileCode == null) {
            LOG.error("Failed to insert file record, parameter is ('{}', '{}')", fileUploadName, adminId);
            return ResponseMaker.error(-2, "服务器内部错误(-2)", null);
        }

        String filePath = uploadPath.getAbsolutePath();

        File dest = new File(filePath, fileCode);
        return doUploadFile(file, fileCode, dest);
    }

    @Override
    public ResponseEntity<byte[]> download(String code) {
        if (!uploadPath.exists()) {
            LOG.error("文件存储目录不存在，无法使用文件下载功能（设置为\"{}\"，应用开始时会自动检查并创建，请检查设置目录权限）", uploadPath.getAbsolutePath());
            return ResponseEntity.badRequest().body(null);
        }
        // 根路径加上传参数的路径构成文件路径地址
        String realName = fileDao.getFileName(code);
        if (realName == null) {
            LOG.warn("请求文件的Code不存在 (fileCode = {})", code);
            return ResponseEntity.badRequest().body(null);
        }

        String realPath = new File(uploadPath, code).getAbsolutePath();
        return getFileEntity(realPath, realName);
    }

    @Override
    public ApiResponse<FileUploadResult> uploadAvatar(UserType userType, int userId, MultipartFile file) {
        if (!avatarPath.exists()) {
            LOG.error("文件存储目录不存在，无法使用文件上传功能（设置为\"{}\"，应用开始时会自动检查并创建，请检查设置目录权限）", avatarPath.getAbsolutePath());
            return ResponseMaker.error(-1, "服务器内部错误(-1)", null);
        }
        if (userId < 0) {
            LOG.debug("User doesn't have auth access. (UserType = {}, userId = {})", userType, userId);
            return ResponseMaker.error(100, "用户未登录或无权限", null);
        }
        if (file.isEmpty()) {
            LOG.debug("Got empty upload result.");
            return ResponseMaker.error(1, "上传失败，文件为空", null);
        }

        File filePath;
        switch (userType) {
            case Admin:
                filePath = adminAvatarPath;
                break;
            case Customer:
                filePath = customerAvatarPath;
                break;
            default:
                return ResponseMaker.error(-100, "不可能出现的错误(-100)", null);
        }

        String fileName = StringRules.getUserAvatarUploadFilename(userType, userId);
        File dest = new File(filePath.getAbsolutePath(), fileName);
        return doUploadFile(file, fileName, dest);
    }

    private ApiResponse<FileUploadResult> doUploadFile(MultipartFile file, String fileName, File dest) {
        try {
            file.transferTo(dest);
            LOG.info("上传文件（{}）成功", dest);
            return ResponseMaker.success(new FileUploadResult(fileName));
        } catch (IOException e) {
            LOG.error("上传文件（{}）失败：{}", dest, e.getMessage());
            return ResponseMaker.error(-3, "服务器内部错误(-3)", null);
        }
    }

    @Override
    public ResponseEntity<byte[]> downloadAvatar(UserType userType, String fileName) {
        if (!avatarPath.exists()) {
            LOG.error("文件存储目录不存在，无法使用文件下载功能（设置为\"{}\"，应用开始时会自动检查并创建，请检查设置目录权限）", avatarPath.getAbsolutePath());
            return ResponseEntity.badRequest().body(null);
        }

        String realPath;
        switch (userType) {
            case Admin:
                realPath = new File(adminAvatarPath, fileName).getAbsolutePath();
                break;
            case Customer:
                realPath = new File(customerAvatarPath, fileName).getAbsolutePath();
                break;
            default:
                return ResponseEntity.badRequest().body(null);
        }
        return getFileEntity(realPath, fileName + ".png");
    }

    private ResponseEntity<byte[]> getFileEntity(String realPath, String fileName) {
        File file = new File(realPath);
        // 构建响应
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();
        bodyBuilder.contentLength(file.length());
        // 二进制数据流
        bodyBuilder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        // 文件名编码
        try {
            String encodeFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

            // 在浏览器中打开
            URL url = new URL("file:///" + file);
            bodyBuilder.header("Content-Type",url.openConnection().getContentType());
            bodyBuilder.header("Content-Disposition","inline;filename*=UTF-8''"+encodeFileName);

            // 下载成功返回二进制流
            return bodyBuilder.body(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            LOG.warn("准备文件过程中发生IOException (Target path is {})", file.getAbsolutePath());
            // 下载失败，返回空回复
            return ResponseEntity.badRequest().body(null);
        }
    }

}
