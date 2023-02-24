package uz.uzkassa.smartposrestaurant.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Getter
@Setter
public class ApplicationProperties {
    private final CtsConfig ctsConfig = new CtsConfig();
    private final SmsConfig smsConfig = new SmsConfig();
    private final SoliqConfig soliqConfig = new SoliqConfig();
    private final ApayDevConfig supplyDevConfig = new ApayDevConfig();
    private final MinioStorageConfig minioStorageConfig = new MinioStorageConfig();
    private final ApayConfig apayConfig = new ApayConfig();
    private final TasnifConfig tasnifConfig = new TasnifConfig();


    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Getter
    @Setter
    public static class Credential {
        String username;
        String password;
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Getter
    @Setter
    public static class CtsConfig extends Credential {

        String authUrl;
        String checkBalanceUrl;
        String transAddUrl;
        String infoByTinUrl;
        String customerByTinUrl;
        String bankListUrl;
        String activityTypeListUrl;
        String createTransactionFromAppUrl;
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Getter
    @Setter
    public static class SoliqConfig extends Credential {
        String host;
    }

    @Setter
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ApayDevConfig extends Credential {

        String url;
        String permissionListUrl;
    }

    @Setter
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SmsConfig {

        private String host;
        private String authUserHost;
        private String username;
        private String password;
        private String cgpn;
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Getter
    @Setter
    public static class MinioStorageConfig {

        String bucket;

        String host;

        String username;

        String password;

        public String getWithBaseUrl(String path) {
            return String.format("%s/%s", this.host, path);
        }
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Getter
    @Setter
    public static class ApayConfig extends Credential {

        String authUrl;
        String registerUrl;
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Getter
    @Setter
    public static class TasnifConfig {

        String url;
    }

}
