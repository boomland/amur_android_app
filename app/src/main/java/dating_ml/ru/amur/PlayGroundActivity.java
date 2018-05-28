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

import dating_ml.ru.amur.dto.MainUserDTO;

public class PlayGroundActivity extends AppCompatActivity {
    MainUserDTO mainUser;
    MyTinderAPI tinderAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_ground);
        receiveData();

        tinderAPI = new MyTinderAPI(getApplicationContext());


    }

    private void receiveData() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            mainUser = b.getParcelable(Constants.MAIN_USER);
        }
    }
}
