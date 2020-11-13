package top.chorg.icommerce.controller.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.chorg.icommerce.bean.dto.ApiResponse;
import top.chorg.icommerce.bean.dto.FileUploadResult;
import top.chorg.icommerce.common.AuthSession;
import top.chorg.icommerce.dao.impl.FileDaoImpl;
import top.chorg.icommerce.service.FileService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/api/file")
public class FileController {

    private final FileService fileService;
    private final AuthSession authSession;

    public FileController(FileService fileService, AuthSession authSession) {
        this.fileService = fileService;
        this.authSession = authSession;
    }

    @RequestMapping(value = "test")
    public @ResponseBody
    String test() {
        return "[FileController] Test OK";
    }

    @PostMapping(value = "upload")
    public @ResponseBody
    ApiResponse<FileUploadResult> uploadFile(
            HttpSession session,
            @RequestParam("file") MultipartFile file
    ) {
        return fileService.upload(authSession.getAdminUserId(session), file);
    }

    @RequestMapping(value = "get", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<byte[]> downloadFile(@RequestParam("fileId") String fileId) {
        return fileService.download(fileId);
    }

}
