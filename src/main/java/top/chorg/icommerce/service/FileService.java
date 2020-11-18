package top.chorg.icommerce.service;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import top.chorg.icommerce.bean.dto.ApiResponse;
import top.chorg.icommerce.bean.dto.FileUploadResult;
import top.chorg.icommerce.common.enums.UserType;

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

    /**
     * Store a uploaded avatar, no DB storage.
     *
     * @param userType The type of requester
     * @param userId The id of requester. Admin or Customer id.
     * @param file File object.
     * @return API format response.
     */
    ApiResponse<FileUploadResult> uploadAvatar(UserType userType, int userId, MultipartFile file);

    /**
     * Return a avatar file entity object by filename directly.
     *
     * @param userType The type of requester
     * @param fileName The hashed file name.
     *                 CAUTION: this name rule should be identical to the upload fileName rules.
     * @return File entity object.
     */
    ResponseEntity<byte[]> downloadAvatar(UserType userType, String fileName);

    /**
     * Return the processed template to display all the uploaded files.
     *
     * @param model The model object for template configurations.
     * @param page Pagination data. Starts from 1.
     * @return The template.
     */
    String getFileList(Model model, int page);
}
