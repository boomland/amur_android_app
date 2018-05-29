package dating_ml.ru.amur;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dating_ml.ru.amur.dto.MainUser;

public class AuthActivity extends AppCompatActivity {
    public CallbackManager callbackManager;
    public LoginButton loginButton;
    public JsonRequester requester;
    public MainUser mainUser;
    public String auth_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_auth);

        requester = new JsonRequester(this);

//        startMockMainActivity();

        mainUser = new MainUser();

        callbackManager = CallbackManager.Factory.create();

        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "user_updates", "user_birthday", "user_friends", "user_photos", "user_education_history", "user_relationship_details", "user_work_history", "user_likes");

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
        Log.d("mTextView", "id: " + mainUser.getFacebookId() + ". token: " + mainUser.getFacebookToken());

        Log.d("request", "doTinderAuth");
        Log.d("request", mainUser.getFacebookId());
        Log.d("request", mainUser.getFacebookToken());

        requester.doTinderAuthRequest(mainUser.getFacebookId(), mainUser.getFacebookToken(), createTinderAuthResponceListener(), createTinderAuthErrorListener());

        Log.d("mTextView", "Tinder auth request set.");
    }

    public Response.Listener<String> createTinderAuthResponceListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("mTextView", "Came sucessful responce from Tinder");
                try {
                    JSONObject obj = new JSONObject(response);

                    String authToken = obj.getString("token");

                    MyTinderAPI tinderAPI = new MyTinderAPI(getApplicationContext());
                    tinderAPI.doProfileRequest(authToken, createDoProfileRequestListener(authToken), createDoProfileRequestErrorListener());

//                    mainUser.setToken(obj.getString("token"));
//                    mainUser.setId(obj.getJSONObject("user").getString("_id"));

                } catch (JSONException e) {
                    Log.d("mTextView", "Some exception caused by parsing responce from Tinder: " + e.getMessage());
                }
            }
        };
    }

    private Response.ErrorListener createDoProfileRequestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AuthActivity", "Error while doing ProfileRequest: " + error.getMessage());
            }
        };
    }

    private Response.Listener<String> createDoProfileRequestListener(final String authToken) {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("AuthActivity", "Successful ProfileRequest: response = " + response);
                    mainUser = JsonParser.parseMainUserFromProfile(new JSONObject(response));
                    mainUser.setToken(authToken);

                    Log.d("mTextView", "This is mainUser.toString(): " + mainUser.toString());
                    Log.d("mTextView", "tinder_id: " + mainUser.getId() + ".\n Tinder token: " + mainUser.getToken());
//                    requester.resolveAmurUrl(createResolveAmurUrlResponseListener(), createResolveAmurUrlErrorListener());
                    startMainActivity();
                } catch (JSONException e) {
                    Log.d("AuthActivity", "Error while converting responce to JSONObject to pass it into parseMainUserFromProfile");
                    e.printStackTrace();
                }
            }
        };
    }

    private Response.Listener<String> createResolveAmurUrlResponseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AmurAPI.setBase_url(response);
                performAmurAuth();
            }
        };
    }

    private Response.ErrorListener createResolveAmurUrlErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mTextView", "That didn't work! " + error.toString());
            }
        };
    }

    public Response.ErrorListener createTinderAuthErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mTextView", "Error of tinder auth :(((((");
                Log.d("request_error", "Instead of error description");

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.d("mTextView", "Time out or no connection");
                } else if (error instanceof AuthFailureError) {
                    Log.d("mTextView", "Auth failure");
                } else if (error instanceof ServerError) {
                    Log.d("mTextView", "server error");
                } else if (error instanceof NetworkError) {
                    Log.d("mTextView", "network error");
                } else if (error instanceof ParseError) {
                    Log.d("mTextView", "parse error");
                }
            }
        };
    }

    public void performAmurAuth() {
        Log.d("mTextView", "Oh yeah:) Amurrrr auth...)");

        try {
            String amur_login_str = new JSONObject()
                    .put("action", "SET_AUTH_TOKEN")
                    .put("tinder_id", mainUser.getId())
                    .put("tinder_auth_token", mainUser.getToken())
                    .toString();
            JsonRequest amur_request = requester.createCustomJsonRequest(
                    AmurAPI.base_url + "/api",
                    amur_login_str,
                    getAmurAuthListener(),
                    createTinderAuthErrorListener());
            requester.queue.add(amur_request);

        } catch (JSONException ex) {
            Log.d("mTextView", "Amur server request construct fail!");
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
                Log.d("mTextView", response);
                Log.d("mTextView", mainUser.toString());

//                startMainActivity();
                Log.d("MAINACTIVITY", "It starts..............................");
            }
        };
    }

    void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        Bundle b = new Bundle();
        b.putParcelable(Constants.MAIN_USER, mainUser);
        b.putString("base_url", AmurAPI.base_url);
        b.putString("auth_response", auth_response);
        intent.putExtras(b);

        startActivity(intent);
    }

    void startPlayGroundActivity() {
        Intent intent = new Intent(getApplicationContext(), PlayGroundActivity.class);

        Bundle b = new Bundle();
        b.putParcelable(Constants.MAIN_USER, mainUser);
        intent.putExtras(b);

        startActivity(intent);
    }

    void startMockMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        Bundle b = new Bundle();
        b.putParcelable(Constants.MAIN_USER, MainUser.getMockMainUser());
        intent.putExtras(b);

        startActivity(intent);
    }

    private void completeMainUserDTO(String auth_response) throws JSONException {
        JSONObject auth_json = new JSONObject(auth_response);

        mainUser.setName(auth_json.getJSONObject("data").getString("name"));
        mainUser.setBirthDate(auth_json.getJSONObject("data").getString("birthday").substring(0, 10));
        mainUser.setGender(auth_json.getJSONObject("data").getInt("gender"));
        mainUser.setBio("");
        mainUser.setAgeFilterMax(auth_json.getJSONObject("data").getInt("age_max"));
        mainUser.setAgeFilterMin(auth_json.getJSONObject("data").getInt("age_min"));
        mainUser.setDistanceFilter(auth_json.getJSONObject("data").getInt("distance_filter"));

        ArrayList<String> photos = new ArrayList<>();
        JSONArray jphotos = auth_json.getJSONObject("data").getJSONArray("photos");
        for (int i = 0; i < jphotos.length(); ++i) {
            photos.add(jphotos.getJSONObject(i).getString("url"));
        }

        mainUser.setPhotos(photos);
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

