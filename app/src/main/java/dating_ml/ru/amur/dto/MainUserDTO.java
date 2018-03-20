package dating_ml.ru.amur.dto;

/**
 * Created by danildudin on 20.03.2018.
 */

public class MainUserDTO {
    private String tinderToken;
    private String tinderId;

    public MainUserDTO(String tinderToken, String tinderId) {
        this.tinderToken = tinderToken;
        this.tinderId = tinderId;
    }

    public String getTinderToken() {
        return tinderToken;
    }

    public String getTinderId() {
        return tinderId;
    }

    public void setTinderToken(String tinderToken) {
        this.tinderToken = tinderToken;
    }

    public void setTinderId(String tinderId) {
        this.tinderId = tinderId;
    }
}
