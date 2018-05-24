package dating_ml.ru.amur;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import dating_ml.ru.amur.dto.MainUserDTO;

public class AuthActivity extends AppCompatActivity {
    public CallbackManager callbackManager;
    public LoginButton loginButton;
    public RequestQueue queue;

    public JsonRequester requester;

    public MainUserDTO mainUser;

    public TextView mTextView;
    public AmurAPI amur_api;
    public String auth_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_auth);

        requester = new JsonRequester(this);

//        startMockMainActivity();

        mainUser = new MainUserDTO();

        queue = Volley.newRequestQueue(this);
        callbackManager = CallbackManager.Factory.create();
        amur_api = new AmurAPI(queue);

        mTextView = findViewById(R.id.textView2);
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "user_birthday", "user_friends", "user_photos", "user_education_history", "user_relationship_details", "user_work_history", "user_likes");

        boolean loggedIn = AccessToken.getCurrentAccessToken() != null;
        if (loggedIn) {
            Log.d("loggedIn", "Is already logged in");
            mainUser.setFacebookToken(AccessToken.getCurrentAccessToken().getToken());
            mainUser.setFacebookId(AccessToken.getCurrentAccessToken().getUserId());
            doTinderAuth();
        } else {
            // Callback registration
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Toast.makeText(getApplicationContext(), "Authentification sucessfull!", Toast.LENGTH_SHORT).show();
                    mainUser.setFacebookToken(loginResult.getAccessToken().getToken());
                    mainUser.setFacebookId(loginResult.getAccessToken().getUserId());
                    doTinderAuth();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(getApplicationContext(), "Authentification cancel!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException exception) {
                    Toast.makeText(getApplicationContext(), "Authentification error!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void doTinderAuth() {
        mTextView.setText("id: " + mainUser.getFacebookId() + ". token: " + mainUser.getFacebookToken());

        Log.d("request", "doTinderAuth");
        Log.d("request", mainUser.getFacebookId());
        Log.d("request", mainUser.getFacebookToken());

        requester.doTinderAuthRequest(mainUser.getFacebookId(), mainUser.getFacebookToken(), createTinderAuthResponceListener(), createTinderAuthErrorListener());

        mTextView.setText("Tinder auth request set.");
    }

    public Response.Listener<String> createTinderAuthResponceListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mTextView.setText("Came sucessful responce from Tinder");
                try {
                    JSONObject obj = new JSONObject(response);
                    mainUser.setTinderToken(obj.getString("token"));
                    mainUser.setTinderId(obj.getJSONObject("user").getString("_id"));

                } catch (JSONException e) {
                    mTextView.setText("Some exception caused by parsing responce from Tinder: " + e.getMessage());
                }
                mTextView.setText("tinder_id: " + mainUser.getTinderId() + ".\n Tinder token: " + mainUser.getFacebookToken());

                requester.resolveAmurUrl(createResolveAmurUrlResponseListener(), createResolveAmurUrlErrorListener());
            }
        };
    }

    private Response.Listener<String> createResolveAmurUrlResponseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                requester.amurAPI.setBase_url(response);
                performAmurAuth();
            }
        };
    }

    private Response.ErrorListener createResolveAmurUrlErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work! " + error.toString());
            }
        };
    }

    public Response.ErrorListener createTinderAuthErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("Error of tinder auth :(((((");
                Log.d("request_error", "Instead of error description");

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    mTextView.setText("Time out or no connection");
                } else if (error instanceof AuthFailureError) {
                    mTextView.setText("Auth failure");
                } else if (error instanceof ServerError) {
                    mTextView.setText("server error");
                } else if (error instanceof NetworkError) {
                    mTextView.setText("network error");
                } else if (error instanceof ParseError) {
                    mTextView.setText("parse error");
                }
            }
        };
    }

    public void performAmurAuth() {
        mTextView.setText("Oh yeah:) Amurrrr auth...)");

        try {
            String amur_login_str = new JSONObject()
                    .put("action", "SET_AUTH_TOKEN")
                    .put("tinder_id", mainUser.getTinderId())
                    .put("tinder_auth_token", mainUser.getTinderToken())
                    .toString();
            JsonRequest amur_request = requester.createCustomJsonRequest(
                    amur_api.base_url + "/api",
                    amur_login_str,
                    getAmurAuthListener(),
                    createTinderAuthErrorListener());
            requester.queue.add(amur_request);

        } catch (JSONException ex) {
            mTextView.setText("Amur server request construct fail!");
        }

    }

    public Response.Listener<String> getAmurAuthListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                auth_response = response;
                try {
                    completeMainUserDTO(auth_response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mTextView.setText(response);

                startMainActivity();
                Log.d("MAINACTIVITY", "It starts..............................");
            }
        };
    }

    void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        Bundle b = new Bundle();
        b.putParcelable(Constants.MAIN_USER, mainUser);
        b.putString("base_url", amur_api.base_url);
        b.putString("auth_response", auth_response);
        intent.putExtras(b);

        startActivity(intent);
    }

    void startMockMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        Bundle b = new Bundle();
        b.putParcelable(Constants.MAIN_USER, MainUserDTO.getMockMainUserDTO());
        intent.putExtras(b);

        startActivity(intent);
    }

    private void completeMainUserDTO(String auth_response) throws JSONException {
        JSONObject auth_json = new JSONObject(auth_response);

        mainUser.setName(auth_json.getJSONObject("data").getString("name"));
        mainUser.setBirthDate(auth_json.getJSONObject("data").getString("birthday").substring(0, 10));
        mainUser.setGender(auth_json.getJSONObject("data").getInt("gender"));
        mainUser.setBio("");
        mainUser.setMaxAge(auth_json.getJSONObject("data").getInt("age_max"));
        mainUser.setMinAge(auth_json.getJSONObject("data").getInt("age_min"));
        mainUser.setMaxDist(auth_json.getJSONObject("data").getInt("distance_filter"));
        int main_photo_idx = auth_json.getJSONObject("data").getInt("main_photo_idx");
        mainUser.setMainPhotoUrl(auth_json.getJSONObject("data").getJSONArray("photos").get(main_photo_idx).toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onClick(View v) {
        loginButton.performClick();
    }
}

