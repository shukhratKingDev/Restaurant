package uz.uzkassa.smartposrestaurant.service;

import io.minio.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.uzkassa.smartposrestaurant.domain.FileUpload;
import uz.uzkassa.smartposrestaurant.dto.fileUpload.BaseFileUploadDTO;
import uz.uzkassa.smartposrestaurant.enums.EntityTypeEnum;
import uz.uzkassa.smartposrestaurant.repository.FileUploadRepository;
import uz.uzkassa.smartposrestaurant.web.rest.errors.BadRequestException;

import java.io.ByteArrayInputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:55
 */
@Service
@Transactional
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FileUploadService extends BaseService {

    FileUploadRepository fileUploadRepository;
    MinioClient minioClient;

    public FileUploadService(FileUploadRepository fileUploadRepository, MinioClient minioClient) {
        this.fileUploadRepository = fileUploadRepository;
        this.minioClient = minioClient;
    }

    public FileUpload fileUpload(MultipartFile file) {
        return this.fileUpload(file, null, null, true);
    }

    private FileUpload fileUpload(MultipartFile file, String entityId, EntityTypeEnum entityType, boolean isPublic) {
        this.validateFile(file);

        BaseFileUploadDTO baseFileUploadDTO;

        try {
            baseFileUploadDTO = uploadToStorage(file.getBytes(), file.getOriginalFilename(), file.getContentType(), isPublic);


        } catch (Exception e) {
            log.error("An unaccepted error has occurred while uploading file: ", e);
            throw new BadRequestException("An unaccepted error has occurred while uploading file");

        }
        FileUpload fileUpload = new FileUpload();
        fileUpload.setContentType(file.getContentType());
        fileUpload.setSize(file.getSize());
        fileUpload.setFileId(baseFileUploadDTO.getName());

        fileUpload.setPath(baseFileUploadDTO.getPath());
        fileUpload.setUrl(baseFileUploadDTO.getUrl());
        fileUpload.setEntityId(entityId);
        fileUpload.setEntityType(entityType);
        fileUpload = fileUploadRepository.save(fileUpload);
        return fileUpload;
    }

    private BaseFileUploadDTO uploadToStorage(byte[] file, String fileName, String contentType, boolean isPublic) {

        UUID uuid = UUID.randomUUID();
        String filename = encodeFileName(fileName);
        String objectName = this.getObjectName(fileName, uuid, isPublic);

        ByteArrayInputStream stream = new ByteArrayInputStream(file);
        log.info("FILENAME: {} -------------------------------------", fileName);
        log.info("objectName: {} -------------------------------------", objectName);
        log.info("contentType: {} -------------------------------------", contentType);
        log.info("objectSize: {} -------------------------------------", file.length);

        BaseFileUploadDTO uploadDTO = null;

        try {
            this.uploadWithPutObject(
                PutObjectArgs.builder()
                    .bucket(applicationProperties.getMinioStorageConfig().getBucket())
                    .object(objectName)
                    .stream(stream, file.length, -1)
                    .contentType(contentType)
                    .build()
            );
            uploadDTO = new BaseFileUploadDTO();
            uploadDTO.setId(uuid.toString());
            uploadDTO.setName(filename);
            uploadDTO.setPath(String.format("%s/%s", applicationProperties.getMinioStorageConfig().getBucket(), objectName));

            if (isPublic) {
                uploadDTO.setUrl(applicationProperties.getMinioStorageConfig().getWithBaseUrl(uploadDTO.getPath()));
            }
        } catch (Exception e) {
            log.error("Close uploaded file error: {}", e.getMessage());
        } finally {
            try {
                stream.close();
            } catch (Exception e) {
                log.error("Close uploaded file error: {}", e.getMessage());
            }
        }
        return uploadDTO;

    }

    private void uploadWithPutObject(PutObjectArgs objectArgs) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(objectArgs.bucket()).build())) {
                throw new BadRequestException("Bucket does not exist");
            }
            Optional.ofNullable(this.minioClient.putObject(objectArgs)).map(ObjectWriteResponse::etag);
        } catch (Exception e) {
            log.error("Error upload file: {}", e.getMessage());
            throw new BadRequestException("Error upload file");
        }

    }

    private String getObjectName(String fileName, UUID uuid, boolean isPublic) {

        String correctedName = encodeFileName(fileName);
        String filename = FileNameUtils.getBaseName(correctedName);
        String extension = FileNameUtils.getExtension(fileName);

        if (isPublic) {
            filename = String.format("public/%s", filename);
        }
        return filename.concat("-").concat(uuid.toString()).concat(StringUtils.isEmpty(extension) ? "" : '.' + extension);
    }

    private String encodeFileName(String originalFileName) {

        String fileName = null;

        try {
            fileName = URLEncoder.encode(originalFileName, UTF_8);
            fileName = URLDecoder.decode(fileName, UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return fileName;
    }
}
