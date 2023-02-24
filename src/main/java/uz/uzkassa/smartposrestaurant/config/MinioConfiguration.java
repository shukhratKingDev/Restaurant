package uz.uzkassa.smartposrestaurant.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 12.02.2022 19:16
 */
@Configuration
public class MinioConfiguration {

    private final ApplicationProperties applicationProperties;

    public MinioConfiguration(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    MinioClient minioClient() {
        return MinioClient.builder()
            .endpoint(applicationProperties.getMinioStorageConfig().getHost())
            .credentials(
                applicationProperties.getMinioStorageConfig().getUsername(),
                applicationProperties.getMinioStorageConfig().getPassword()
            ).build();
    }
}
