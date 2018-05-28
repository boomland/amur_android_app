package dating_ml.ru.amur.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {
    private String id;
    private String birthDate;
    private int gender;
    private String name;
    private String bio;
    private ArrayList<String> photos;

    public User() {
        id = "";
        birthDate = "";
        gender = 0;
        name = "";
        bio = "";
        photos = new ArrayList<String>();
    }

    public User(String id, String birthDate, int gender, String name, String bio, ArrayList<String> photos) {
        this.id = id;
        this.birthDate = birthDate;
        this.gender = gender;
        this.name = name;
        this.bio = bio;
        this.photos = photos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    protected User(Parcel in) {
        id = in.readString();
        birthDate = in.readString();
        gender = in.readInt();
        name = in.readString();
        bio = in.readString();
        if (in.readByte() == 0x01) {
            photos = new ArrayList<String>();
            in.readList(photos, String.class.getClassLoader());
        } else {
            photos = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(birthDate);
        dest.writeInt(gender);
        dest.writeString(name);
        dest.writeString(bio);
        if (photos == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(photos);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", photos=" + photos +
                '}';
    }
}