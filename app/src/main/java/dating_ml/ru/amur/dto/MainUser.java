package dating_ml.ru.amur.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MainUser extends User implements Parcelable{
    private int ageFilterMax;
    private int ageFilterMin;
    private int distanceFilter;
    private String email;
    private String facebookId;
    private int genderFilter;
    private String token;
    private String facebookToken;

    public MainUser() {
        super();
        ageFilterMax = 0;
        ageFilterMin = 0;
        distanceFilter = 0;
        email = "";
        facebookId = "";
        genderFilter = 0;
        token = "";
        facebookToken = "";
    }

    public MainUser(String id, String birthDate, int gender, String name, String bio, ArrayList<String> photos, int ageFilterMax, int ageFilterMin, int distanceFilter, String email, String facebookId, int genderFilter, String token, String facebookToken) {
        super(id, birthDate, gender, name, bio, photos);
        this.ageFilterMax = ageFilterMax;
        this.ageFilterMin = ageFilterMin;
        this.distanceFilter = distanceFilter;
        this.email = email;
        this.facebookId = facebookId;
        this.genderFilter = genderFilter;
        this.token = token;
        this.facebookToken = facebookToken;
    }

    public int getAgeFilterMax() {
        return ageFilterMax;
    }

    public void setAgeFilterMax(int ageFilterMax) {
        this.ageFilterMax = ageFilterMax;
    }

    public int getAgeFilterMin() {
        return ageFilterMin;
    }

    public void setAgeFilterMin(int ageFilterMin) {
        this.ageFilterMin = ageFilterMin;
    }

    public int getDistanceFilter() {
        return distanceFilter;
    }

    public void setDistanceFilter(int distanceFilter) {
        this.distanceFilter = distanceFilter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public int getGenderFilter() {
        return genderFilter;
    }

    public void setGenderFilter(int genderFilter) {
        this.genderFilter = genderFilter;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFacebookToken() {
        return facebookToken;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }

    public MainUser(Parcel in) {
        super(in);
        this.ageFilterMax = in.readInt();
        this.ageFilterMin = in.readInt();
        this.distanceFilter = in.readInt();
        this.email = in.readString();
        this.facebookId = in.readString();
        this.genderFilter = in.readInt();
        this.token = in.readString();
        this.facebookToken = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(ageFilterMax);
        dest.writeInt(ageFilterMin);
        dest.writeInt(distanceFilter);
        dest.writeString(email);
        dest.writeString(facebookId);
        dest.writeInt(genderFilter);
        dest.writeString(token);
        dest.writeString(facebookToken);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MainUser> CREATOR = new Parcelable.Creator<MainUser>() {
        @Override
        public MainUser createFromParcel(Parcel in) {
            return new MainUser(in);
        }

        @Override
        public MainUser[] newArray(int size) {
            return new MainUser[size];
        }
    };

    @Override
    public String toString() {
        return "MainUser{" + super.toString() + ", " +
                "ageFilterMax=" + ageFilterMax +
                ", ageFilterMin=" + ageFilterMin +
                ", distanceFilter=" + distanceFilter +
                ", email='" + email + '\'' +
                ", facebookId='" + facebookId + '\'' +
                ", genderFilter=" + genderFilter +
                ", token=" + token +
                ", facebookToken=" + facebookToken +
                '}';
    }

    //------------------------------ Mock ----------------------------------------------------------
    public static MainUser getMockMainUser() {
        MainUser res = new MainUser();

        res.setName("Danil");
        res.setBio("Хей, че как! Так все время говрит Алевтина. И что-то в этом есть.");
        res.setBirthDate("25.02.1998");

        ArrayList<String> photos = new ArrayList<>();
        photos.add("http://moziru.com/images/dipper-clipart-dipper-pines-clipart-2.jpg");
        photos.add("http://img3.wikia.nocookie.net/__cb20130511164448/gravityfalls/images/f/f9/S1e7_Dipper's_Birthmark.png");

        res.setPhotos(photos);
        res.setDistanceFilter(100);
        res.setAgeFilterMin(18);
        res.setAgeFilterMax(25);

        return res;
    }
}
