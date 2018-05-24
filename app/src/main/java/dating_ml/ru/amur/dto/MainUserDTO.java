package dating_ml.ru.amur.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by danildudin on 20.03.2018.
 */

public class MainUserDTO implements Parcelable {
    private String tinderToken;
    private String tinderId;
    private String facebookToken;
    private String facebookId;

    private String baseUrl;

    private String name;
    private String birthDate;
    private int gender;
    private String bio;
    private int maxAge;
    private int minAge;
    private int maxDist;
    private String mainPhotoUrl;

    public MainUserDTO(String tinderToken, String tinderId, String facebookToken, String facebookId) {
        this.tinderToken = tinderToken;
        this.tinderId = tinderId;
        this.facebookToken = facebookToken;
        this.facebookId = facebookId;
    }

    public MainUserDTO() {
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

    public String getFacebookToken() {
        return facebookToken;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxDist() {
        return maxDist;
    }

    public void setMaxDist(int maxDist) {
        this.maxDist = maxDist;
    }

    public String getMainPhotoUrl() {
        return mainPhotoUrl;
    }

    public void setMainPhotoUrl(String mainPhotoUrl) {
        this.mainPhotoUrl = mainPhotoUrl;
    }

    public static MainUserDTO getMockMainUserDTO() {
        MainUserDTO res = new MainUserDTO();
        res.setTinderId("This is tinder id");
        res.setTinderToken("This is tinder token");
        res.setFacebookId("This is facebook id");
        res.setFacebookToken("This is facebook token");

        res.setName("Carmen");
        res.setBirthDate("25.02.1998");
        res.setGender(1);
        res.setBio("This is my bio.");
        res.setMaxAge(25);
        res.setMinAge(18);
        res.setMaxDist(100);
        res.setMainPhotoUrl("https://78.media.tumblr.com/fcfbb9f51b54168356c2063e2cd7d111/tumblr_oj0w3feQTC1sjnhf0o1_1280.png");

        return res;
    }

    protected MainUserDTO(Parcel in) {
        tinderToken = in.readString();
        tinderId = in.readString();
        facebookToken = in.readString();
        facebookId = in.readString();
        baseUrl = in.readString();
        name = in.readString();
        birthDate = in.readString();
        gender = in.readInt();
        bio = in.readString();
        maxAge = in.readInt();
        minAge = in.readInt();
        maxDist = in.readInt();
        mainPhotoUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tinderToken);
        dest.writeString(tinderId);
        dest.writeString(facebookToken);
        dest.writeString(facebookId);
        dest.writeString(baseUrl);
        dest.writeString(name);
        dest.writeString(birthDate);
        dest.writeInt(gender);
        dest.writeString(bio);
        dest.writeInt(maxAge);
        dest.writeInt(minAge);
        dest.writeInt(maxDist);
        dest.writeString(mainPhotoUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MainUserDTO> CREATOR = new Parcelable.Creator<MainUserDTO>() {
        @Override
        public MainUserDTO createFromParcel(Parcel in) {
            return new MainUserDTO(in);
        }

        @Override
        public MainUserDTO[] newArray(int size) {
            return new MainUserDTO[size];
        }
    };
}