package top.chorg.icommerce.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import top.chorg.icommerce.bean.dto.ApiResponse;
import top.chorg.icommerce.bean.dto.FileUploadResult;

public interface FileService {

    /**
     * Store a uploaded file to the vault, and make a record in the DB.
     *
     * @param adminId Upload operation committer.
     * @param file File object.
     * @return API format response.
     */
    ApiResponse<FileUploadResult> upload(int adminId, MultipartFile file);

    /**
     * Return a file entity object by access code.
     *
     * @param code File access code.
     * @return File entity object.
     */
    ResponseEntity<byte[]> download(String code);
}
