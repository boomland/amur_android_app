package dating_ml.ru.amur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dating_ml.ru.amur.dto.MainUser;
import dating_ml.ru.amur.dto.RecUser;
import dating_ml.ru.amur.dto.User;

public class JsonParser {
    public static User parseUserFromPerson(JSONObject person) throws JSONException {
        User user = new User();

        user.setId(person.getString("_id"));
        user.setBirthDate(person.getString("birth_date"));
        user.setGender(person.getInt("gender"));
        user.setName(person.getString("name"));

        ArrayList<String> photos = new ArrayList<>();
        JSONArray jPhotos = person.getJSONArray("photos");
        for (int i = 0; i < jPhotos.length(); ++i) {
            photos.add(jPhotos.getJSONObject(i).getString("url"));
        }
        user.setPhotos(photos);

        return user;
    }

    public static RecUser parseRecUserFromRec(JSONObject rec) throws JSONException{
        RecUser recUser = new RecUser();

        recUser.setId(rec.getString("_id"));
        recUser.setBio(rec.getString("bio"));
        recUser.setBirthDate(rec.getString("birth_date"));
        recUser.setDistance(rec.getInt("distance_mi"));
        recUser.setGender(rec.getInt("gender"));
        recUser.setName(rec.getString("name"));

        ArrayList<String> photos = new ArrayList<>();
        JSONArray jPhotos = rec.getJSONArray("photos");
        for (int i = 0; i < jPhotos.length(); ++i) {
            photos.add(jPhotos.getJSONObject(i).getString("url"));
        }
        recUser.setPhotos(photos);

        return recUser;
    }

    public static MainUser parseMainUserFromProfile(JSONObject profile) throws JSONException {
        MainUser mainUser = new MainUser();

        mainUser.setId(profile.getString("_id"));
        mainUser.setAgeFilterMax(profile.getInt("age_filter_max"));
        mainUser.setAgeFilterMin(profile.getInt("age_filter_min"));
        mainUser.setBio(profile.getString("bio"));
        mainUser.setBirthDate(profile.getString("birth_date"));
        mainUser.setDistanceFilter(profile.getInt("distance_filter"));
        mainUser.setEmail(profile.getString("email"));
        mainUser.setFacebookId(profile.getString("facebook_id"));
        mainUser.setGender(profile.getInt("gender"));
        mainUser.setGenderFilter(profile.getInt("gender_filter"));
        mainUser.setName(profile.getString("name"));

        ArrayList<String> photos = new ArrayList<>();
        JSONArray jPhotos = profile.getJSONArray("photos");
        for (int i = 0; i < jPhotos.length(); ++i) {
            photos.add(jPhotos.getJSONObject(i).getString("url"));
        }
        mainUser.setPhotos(photos);

        return mainUser;
    }

    public static RecUser parseRecUserFromKolyaRequest(JSONObject rec) throws JSONException {
        RecUser recUser = new RecUser();
        recUser.setName(rec.getString("name"));
        recUser.setId(rec.getString("id"));

        recUser.setName(rec.getString("name"));
        recUser.setId(rec.getString("id"));
        recUser.setDistance(rec.getInt("distance"));
        recUser.setBirthDate(rec.getString("birthday"));
        recUser.setGender(rec.getInt("gender"));
        recUser.setBio(rec.getString("bio"));

        JSONArray jsonArray = rec.getJSONArray("photos");
        ArrayList<String> photos = new ArrayList<>();
        for (int j = 0; j != jsonArray.length(); ++j) {
            if (jsonArray.getJSONObject(j).getString("type").compareTo("tinder") == 0) {
                photos.add(jsonArray.getJSONObject(j).getString("url"));
            }
        }
        recUser.setPhotos(photos);

        return recUser;
    }
}
