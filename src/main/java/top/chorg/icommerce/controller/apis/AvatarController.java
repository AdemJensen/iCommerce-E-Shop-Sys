package top.chorg.icommerce.controller.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.chorg.icommerce.bean.dto.ApiResponse;
import top.chorg.icommerce.bean.dto.FileUploadResult;
import top.chorg.icommerce.common.AuthSession;
import top.chorg.icommerce.common.StringRules;
import top.chorg.icommerce.service.FileService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/api/avatar")
public class AvatarController {

    private final FileService fileService;
    private final AuthSession authSession;

    public AvatarController(FileService fileService, AuthSession authSession) {
        this.fileService = fileService;
        this.authSession = authSession;
    }

    @RequestMapping(value = "test")
    String test() {
        return "debug/AvatarControllerTest";
    }

    @PostMapping(value = "upload")
    public @ResponseBody
    ApiResponse<FileUploadResult> uploadAvatar(
            HttpSession session,
            @RequestParam("file") MultipartFile file
    ) {
        return fileService.uploadAvatar(
                authSession.getSessionUserType(session),
                authSession.getSessionUserId(session), file
        );
    }

    @GetMapping(value = "get/{userType}/{filename}")
    public ResponseEntity<byte[]> downloadAvatar(
            @PathVariable("userType") String userType,
            @PathVariable("filename") String filename
    ) {
        return fileService.downloadAvatar(
                StringRules.getUserTypeFromAvatarUrlPrefixString(userType),
                filename
        );
    }

}
