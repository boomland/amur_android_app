package dating_ml.ru.amur.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RecUser extends User implements Parcelable {
    private int distance;

    public RecUser() {
        super();
        this.distance = 0;
    }

    public RecUser(String id, String birthDate, int gender, String name, String bio, ArrayList<String> photos, int distance) {
        super(id, birthDate, gender, name, bio, photos);
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public RecUser(Parcel in) {
        super(in);
        this.distance = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(distance);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RecUser> CREATOR = new Parcelable.Creator<RecUser>() {
        @Override
        public RecUser createFromParcel(Parcel in) {
            return new RecUser(in);
        }

        @Override
        public RecUser[] newArray(int size) {
            return new RecUser[size];
        }
    };

    @Override
    public String toString() {
        return "RecUser{" + super.toString() + ", " +
                "distance=" + distance +
                '}';
    }
}
