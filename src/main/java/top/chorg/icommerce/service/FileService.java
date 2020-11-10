package top.chorg.icommerce.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import top.chorg.icommerce.bean.dto.ApiResponse;
import top.chorg.icommerce.bean.dto.FileUploadResult;

public interface FileService {
    ApiResponse<FileUploadResult> upload(String pathPrefix, String open_session, int userId, MultipartFile file);
    ResponseEntity<byte[]> download(String code);
}
