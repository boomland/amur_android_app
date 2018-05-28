package dating_ml.ru.amur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import dating_ml.ru.amur.dto.MainUser;
import dating_ml.ru.amur.dto.RecUser;
import dating_ml.ru.amur.dto.User;

public class PlayGroundActivity extends AppCompatActivity {
    MainUser mainUser;
    MyTinderAPI tinderAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_ground);
        receiveData();

        JSONObject profile = new JSONObject();
        MainUser mainUser = new MainUser();
        try {
            mainUser = JsonParser.parseMainUserFromProfile(profile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("PlayGround","This is parsed mainUser " + mainUser.toString());

        JSONObject rec = new JSONObject();
        RecUser recUser = new RecUser();
        try {
            recUser = JsonParser.parseRecUserFromRec(profile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("PlayGround","This is parsed recUser " + recUser.toString());

        JSONObject person = new JSONObject();
        User user = new User();
        try {
            user = JsonParser.parseUserFromPerson(person);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("PlayGround","This is parsed user " + user.toString());
    }

    private void receiveData() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            mainUser = b.getParcelable(Constants.MAIN_USER);
        }
    }
}
