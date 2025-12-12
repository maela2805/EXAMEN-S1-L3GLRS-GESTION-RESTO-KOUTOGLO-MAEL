package gestion_restaurant.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

public class CloudinaryService {

    private final Cloudinary cloudinary;
    private final boolean configured;

    public CloudinaryService() {
        String url = System.getenv("CLOUDINARY_URL");
        if (url != null && !url.isBlank()) {
            this.cloudinary = new Cloudinary(url);
            this.configured = true;
            return;
        }

        String cloudName = trim(System.getenv("CLOUDINARY_CLOUD_NAME"));
        String apiKey = trim(System.getenv("CLOUDINARY_API_KEY"));
        String apiSecret = trim(System.getenv("CLOUDINARY_API_SECRET"));

        if (cloudName == null || apiKey == null || apiSecret == null) {
            this.cloudinary = new Cloudinary();
            this.configured = false;
            return;
        }

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);

        this.cloudinary = new Cloudinary(config);
        this.configured = true;
    }

    private static String trim(String s) {
        return s == null ? null : s.trim();
    }

    /**
     * Upload a file to Cloudinary.
     * @param file local file (must exist)
     * @param options upload options map (can be null)
     * @return UploadResult with secureUrl and publicId
     * @throws Exception on upload problems or when not configured
     */
    public UploadResult upload(File file, Map<String, Object> options) throws Exception {
        if (!isEnabled()) {
            throw new IllegalStateException("Cloudinary non configuré. Définir CLOUDINARY_URL ou CLOUDINARY_CLOUD_NAME/CLOUDINARY_API_KEY/CLOUDINARY_API_SECRET.");
        }
        if (file == null || !file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("Fichier introuvable ou invalide : " + (file == null ? "null" : file.getAbsolutePath()));
        }

        @SuppressWarnings("unchecked")
        Map<?, ?> res = cloudinary.uploader().upload(file, options == null ? ObjectUtils.emptyMap() : options);

        String secureUrl = res.get("secure_url") != null ? res.get("secure_url").toString() : null;
        String publicId = res.get("public_id") != null ? res.get("public_id").toString() : null;

        return new UploadResult(secureUrl, publicId, res);
    }

    public UploadResult uploadBytes(byte[] bytes, Map<String, Object> options) throws Exception {
        if (!isEnabled()) {
            throw new IllegalStateException("Cloudinary non configuré.");
        }
        @SuppressWarnings("unchecked")
        Map<?, ?> res = cloudinary.uploader().upload(bytes, options == null ? ObjectUtils.emptyMap() : options);
        String secureUrl = res.get("secure_url") != null ? res.get("secure_url").toString() : null;
        String publicId = res.get("public_id") != null ? res.get("public_id").toString() : null;
        return new UploadResult(secureUrl, publicId, res);
    }

    public void delete(String publicId) throws Exception {
        if (!isEnabled()) return;
        if (publicId == null || publicId.isBlank()) return;
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    public boolean isEnabled() {
        return configured;
    }

    public boolean isAvailable() {
        return isEnabled();
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
