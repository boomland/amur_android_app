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

    public MainUser() {
        super();
        ageFilterMax = 0;
        ageFilterMin = 0;
        distanceFilter = 0;
        email = "";
        facebookId = "";
        genderFilter = 0;
    }

    public MainUser(String id, String birthDate, int gender, String name, String bio, ArrayList<String> photos, int ageFilterMax, int ageFilterMin, int distanceFilter, String email, String facebookId, int genderFilter) {
        super(id, birthDate, gender, name, bio, photos);
        this.ageFilterMax = ageFilterMax;
        this.ageFilterMin = ageFilterMin;
        this.distanceFilter = distanceFilter;
        this.email = email;
        this.facebookId = facebookId;
        this.genderFilter = genderFilter;
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

    public MainUser(Parcel in) {
        super(in);
        this.ageFilterMax = in.readInt();
        this.ageFilterMin = in.readInt();
        this.distanceFilter = in.readInt();
        this.email = in.readString();
        this.facebookId = in.readString();
        this.genderFilter = in.readInt();
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
                '}';
    }
}
