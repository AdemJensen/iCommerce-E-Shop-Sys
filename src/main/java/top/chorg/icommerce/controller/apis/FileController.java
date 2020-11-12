package top.chorg.icommerce.controller.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.chorg.icommerce.bean.dto.ApiResponse;
import top.chorg.icommerce.bean.dto.FileUploadResult;
import top.chorg.icommerce.dao.impl.FileDaoImpl;
import top.chorg.icommerce.service.FileService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/api/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService, FileDaoImpl fileDao) {
        this.fileService = fileService;
    }

    @RequestMapping(value = "test")
    public @ResponseBody
    String test() {
        return "[FileController] Test OK";
    }

    @PostMapping(value = "upload")
    public @ResponseBody
    ApiResponse<FileUploadResult> uploadFile(HttpServletRequest request,
                                             @RequestParam("file") MultipartFile file,
                                             @RequestParam("open_session") String open_session,
                                             @RequestParam("userId") int userId
    ) {
        return fileService.upload(
                String.format("%s://%s%s/%s",
                        request.getScheme(),
                        request.getServerName(),
                        ((request.getServerPort() == 80 || request.getServerPort() == 443) ? "" : ":" + request.getServerPort()),
                        request.getContextPath()),
                open_session, userId, file
        );
    }

    @RequestMapping(value = "get", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<byte[]> downloadFile(@RequestParam("fileId") String fileId) {
        return fileService.download(fileId);
    }

}
