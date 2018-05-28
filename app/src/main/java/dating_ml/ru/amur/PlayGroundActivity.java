package dating_ml.ru.amur;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import dating_ml.ru.amur.dto.MainUser;
import dating_ml.ru.amur.dto.MainUserDTO;
import dating_ml.ru.amur.dto.RecUser;
import dating_ml.ru.amur.dto.User;

public class PlayGroundActivity extends AppCompatActivity {
    MainUserDTO mainUser;
    MyTinderAPI tinderAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_ground);
        receiveData();

        tinderAPI = new MyTinderAPI(getApplicationContext());

        User user = new User();
        user.setBio("This is my bio");
        user.setName("Daniel");
        user.setBirthDate("25.02.1998");
        user.setGender(0);

        RecUser recUser = new RecUser();
        recUser.setBio("This is my bio");
        recUser.setDistance(100);

        MainUser mainUser = new MainUser();
        mainUser.setAgeFilterMax(25);
        mainUser.setAgeFilterMin(18);
        mainUser.setFacebookId("facebookId");
        mainUser.setDistanceFilter(250);

        Log.d("PlayGround", "this is User: " + user.toString());
        Log.d("PlayGround", "this is RecUser: " + recUser.toString());
        Log.d("PlayGround", "this is MainUser: " + mainUser.toString());

        Bundle b = new Bundle();
        b.putParcelable("user", user);
        b.putParcelable("recUser", recUser);
        b.putParcelable("mainUser", mainUser);

        user = b.getParcelable("user");
        recUser = b.getParcelable("recUser");
        mainUser = b.getParcelable("mainUser");

        Log.d("PlayGround", "this is User: " + user.toString());
        Log.d("PlayGround", "this is RecUser: " + recUser.toString());
        Log.d("PlayGround", "this is MainUser: " + mainUser.toString());
    }

    private void receiveData() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            mainUser = b.getParcelable(Constants.MAIN_USER);
        }
    }
}
