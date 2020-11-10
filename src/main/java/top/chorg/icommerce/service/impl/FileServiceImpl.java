package top.chorg.icommerce.service.impl;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.chorg.icommerce.bean.dto.ApiResponse;
import top.chorg.icommerce.bean.dto.FileUploadResult;
import top.chorg.icommerce.common.ResponseMaker;
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

    public FileServiceImpl(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    public ApiResponse<FileUploadResult> upload(String pathPrefix, String open_session, int userId, MultipartFile file) {
        //if (!userService.authenticate(userId, open_session)) return ResponseMaker.error(100, "用户未登录", null);
        if (file.isEmpty()) {
            return ResponseMaker.error(1, "上传失败，文件为空", null);
        }
        String fileUploadName = file.getOriginalFilename();
        String fileStorageName = fileDao.generateFileId(fileUploadName, userId);
        String filePath;
        File upload = new File("resources","uploaded");
        filePath = upload.getAbsolutePath();
        if(!upload.exists()) {
            if (upload.mkdirs()) LOG.info("上传文件夹不存在，已自动创建（" + upload + ")");
            else {
                LOG.error("上传文件夹创建失败");
                return ResponseMaker.error(-1, "服务器内部错误(-1)", null);
            }
        }
        File dest;
        if (fileStorageName != null) {
            dest = new File(filePath, fileStorageName);
            try {
                file.transferTo(dest);
                LOG.info("上传文件（" + dest + "）成功");
                return ResponseMaker.success(new FileUploadResult(
                        fileStorageName, String.format("%sapi/file/get?fileId=%s", pathPrefix, fileStorageName)
                ));
            } catch (IOException e) {
                LOG.error("上传文件（" + dest + "）失败：" + e.getMessage());
            }
        } else {
            LOG.error("上传文件失败：文件名为空");
        }
        return ResponseMaker.error(-3, "服务器内部错误(-3)", null);
    }

    @Override
    public ResponseEntity<byte[]> download(String code) {
        // 根路径加上传参数的路径构成文件路径地址
        File downloadPath;
        String realName;
        try {
            downloadPath = new File("resources", "uploaded");
            realName = fileDao.getFileName(code);
        } catch (DataAccessException e) {
            LOG.warn("文件数据库无指定文件 (id=" + code + ")");
            return ResponseEntity.badRequest().body(null);
        }
        String realPath = new File(downloadPath, code).getAbsolutePath();
        File file = new File(realPath);
        // 构建响应
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();
        bodyBuilder.contentLength(file.length());
        // 二进制数据流
        bodyBuilder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        // 文件名编码
        try {
            String encodeFileName = URLEncoder.encode(realName, StandardCharsets.UTF_8);

            // 在浏览器中打开
            URL url = new URL("file:///" + file);
            bodyBuilder.header("Content-Type",url.openConnection().getContentType());
            bodyBuilder.header("Content-Disposition","inline;filename*=UTF-8''"+encodeFileName);

            // 下载成功返回二进制流
            return bodyBuilder.body(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
            LOG.warn("准备文件过程中发生 IOException");
        }
        // 下载失败直接返回错误的请求
        return ResponseEntity.badRequest().body(null);
    }
}
