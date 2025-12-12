package gestion_restaurant.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService() {
        // Accept CLOUDINARY_URL first (format cloudinary://API_KEY:API_SECRET@CLOUD_NAME)
        String url = System.getenv("CLOUDINARY_URL");
        if (url != null && !url.isBlank()) {
            this.cloudinary = new Cloudinary(url);
            return;
        }

        // fallback to separate env vars
        String cloudName = trim(System.getenv("CLOUDINARY_CLOUD_NAME"));
        String apiKey = trim(System.getenv("CLOUDINARY_API_KEY"));
        String apiSecret = trim(System.getenv("CLOUDINARY_API_SECRET"));

        if (cloudName == null || apiKey == null || apiSecret == null) {
            throw new IllegalStateException("Cloudinary non configuré. Définir CLOUDINARY_URL ou "
                    + "CLOUDINARY_CLOUD_NAME / CLOUDINARY_API_KEY / CLOUDINARY_API_SECRET.");
        }

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);

        this.cloudinary = new Cloudinary(config);
    }

    private static String trim(String s) {
        return s == null ? null : s.trim();
    }

    public UploadResult upload(File file, Map<String, Object> options) throws Exception {
        if (file == null || !file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("Fichier introuvable ou invalide : " + (file == null ? "null" : file.getAbsolutePath()));
        }

        @SuppressWarnings("unchecked")
        Map<?, ?> res = cloudinary.uploader().upload(file, options == null ? ObjectUtils.emptyMap() : options);

        String secureUrl = res.get("secure_url") != null ? res.get("secure_url").toString() : null;
        String publicId = res.get("public_id") != null ? res.get("public_id").toString() : null;

        return new UploadResult(secureUrl, publicId, res);
    }

    public void delete(String publicId) throws Exception {
        if (publicId == null || publicId.isBlank()) return;
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    public static class UploadResult {
        public final String secureUrl;
        public final String publicId;
        public final Map<?, ?> raw;

        public UploadResult(String secureUrl, String publicId, Map<?, ?> raw) {
            this.secureUrl = secureUrl;
            this.publicId = publicId;
            this.raw = raw;
        }
    }
}
