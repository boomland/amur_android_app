package dating_ml.ru.amur.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class UserDTO implements Parcelable {
    //TODO сделать private
    public String name;
    public String city;
    public String url;

    private int dist;
    private Date birthDate;
    private String tinderId;
    private int gender;
    private String bio;
    private ArrayList<String> photoUrls;

    public UserDTO() {
    }

    public UserDTO(String name, String city, String url) {
        this.name = name;
        this.city = city;
        this.url = url;
    }

    public UserDTO(String name, String city, String url, int dist, Date birthDate, String tinderId,
                   int gender, String bio, ArrayList<String> photoUrls) {
        this.name = name;
        this.city = city;
        this.url = url;


        this.dist = dist;
        this.birthDate = birthDate;
        this.tinderId = tinderId;
        this.gender = gender;
        this.bio = bio;
        this.photoUrls = photoUrls;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getUrl() {
        return url;
    }

    public int getDist() {
        return dist;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getTinderId() {
        return tinderId;
    }

    public int getGender() {
        return gender;
    }

    public String getBio() {
        return bio;
    }

    public ArrayList<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setTinderId(String tinderId) {
        this.tinderId = tinderId;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setPhotoUrls(ArrayList<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    @Override
    public String toString() {
        String res = "";
        res += "touristSpot: {";
        res += "name: " + name + ", ";
        res += "city: " + city + ", ";
        res += "url: " + url + ", ";
        res += "dist: " + dist + ", ";
        res += "birthDate: " + birthDate.toString() + ", ";
        res += "tinderId: " + tinderId + ", ";
        res += "gender: " + gender + ", ";
        res += "bio: " + bio + ", ";
        res += "bio: " + bio + ", ";
        res += "photoUrls: " + photoUrls.toString() + ", ";
        res += "}";


        return res;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.city);
        dest.writeString(this.url);
        dest.writeInt(this.dist);
        dest.writeLong(this.birthDate != null ? this.birthDate.getTime() : -1);
        dest.writeString(this.tinderId);
        dest.writeInt(this.gender);
        dest.writeString(this.bio);
        dest.writeStringList(this.photoUrls);
    }

    protected UserDTO(Parcel in) {
        this.name = in.readString();
        this.city = in.readString();
        this.url = in.readString();
        this.dist = in.readInt();
        long tmpBirthDate = in.readLong();
        this.birthDate = tmpBirthDate == -1 ? null : new Date(tmpBirthDate);
        this.tinderId = in.readString();
        this.gender = in.readInt();
        this.bio = in.readString();
        this.photoUrls = in.createStringArrayList();
    }

    public static final Parcelable.Creator<dating_ml.ru.amur.dto.UserDTO> CREATOR = new Parcelable.Creator<dating_ml.ru.amur.dto.UserDTO>() {
        @Override
        public dating_ml.ru.amur.dto.UserDTO createFromParcel(Parcel source) {
            return new dating_ml.ru.amur.dto.UserDTO(source);
        }

        @Override
        public dating_ml.ru.amur.dto.UserDTO[] newArray(int size) {
            return new dating_ml.ru.amur.dto.UserDTO[size];
        }
    };
}
