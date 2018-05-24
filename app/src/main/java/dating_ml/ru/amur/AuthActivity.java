package dating_ml.ru.amur;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
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

public class AuthActivity extends AppCompatActivity {


    CallbackManager callbackManager;
    LoginButton loginButton;
    RequestQueue queue;
    public String tinder_token;
    public String tinder_id;
    String facebook_id;
    String facebook_token;
    TextView mTextView;
    AmurAPI amur_api;
    String auth_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_auth);

        queue  = Volley.newRequestQueue(this);
        callbackManager = CallbackManager.Factory.create();
        amur_api = new AmurAPI(queue);

        mTextView = (TextView) findViewById(R.id.textView2);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "user_birthday", "user_friends", "user_photos", "user_education_history", "user_relationship_details", "user_work_history", "user_likes");

        boolean loggedIn = AccessToken.getCurrentAccessToken() != null;
        if (loggedIn) {
            Log.d("loggedIn", "Is already logged in");
            facebook_token = AccessToken.getCurrentAccessToken().getToken();
            facebook_id = AccessToken.getCurrentAccessToken().getUserId();
            doTinderAuth();
        } else {
            // Callback registration
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Toast.makeText(getApplicationContext(), "Authentification sucessfull!", Toast.LENGTH_SHORT).show();
                    facebook_token = loginResult.getAccessToken().getToken();
                    facebook_id = loginResult.getAccessToken().getUserId();
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
        mTextView.setText("id: " + facebook_id + ". token: " + facebook_token);

        Log.d("request", "doTinderAuth");
        Log.d("request", facebook_id);
        Log.d("request", facebook_token);

        JsonRequest tinder_request = createCustomJsonRequest("https://api.gotinder.com/auth",
                "{\"facebook_id\": \"" + facebook_id + "\", \"facebook_token\": \"" + facebook_token + "\"}",
                createTinderAuthResponceListener(),
                createTinderAuthErrorListener());

        queue.add(tinder_request);
        mTextView.setText("Tinder auth request set.");

    }

    public Response.Listener<String> createTinderAuthResponceListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mTextView.setText("Came sucessful responce from Tinder");
                try {
                    JSONObject obj = new JSONObject(response);
                    tinder_token = obj.getString("token");
                    tinder_id = obj.getJSONObject("user").getString("_id");

                } catch (JSONException e) {
                    mTextView.setText("Some exception caused by parsing responce from Tinder: " + e.getMessage());
                }
                mTextView.setText("tinder_id: " + tinder_id + ".\n Tinder token: " + tinder_token);

                resolveAmurUrl();
            }
        };
    }

    public void resolveAmurUrl() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "http://dating-ml.ru/get_server.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        amur_api.base_url = response;
                        performAmurAuth();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work! " + error.toString());
            }
        });

        queue.add(stringRequest);
    }

    public Response.ErrorListener createTinderAuthErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                mTextView.setText("Error of tinder auth :(((((");
                Log.d("request_error", "Instead of error description");
                /*
                Log.d("abd", "Error: " + error
                        + ">>" + error.networkResponse.statusCode
                        + ">>" + error.networkResponse.data
                        + ">>" + error.getCause()
                        + ">>" + error.getMessage());
                */


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

    public static JsonRequest createCustomJsonRequest(String request_url,
                                                      String data,
                                                      Response.Listener<String> listener,
                                                      Response.ErrorListener error_listener) {

        return new JsonRequest(Request.Method.POST,
                request_url,
                data,
                listener,
                error_listener) {
            @Override
            public int compareTo(@NonNull Object o) {
                return 0;
            }

            @Override
            protected Response parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, "UTF-8");
                    return Response.success(jsonString,
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("app_version", "6.9.4");
                params.put("platform", "ios");
                params.put("content-type", "application/json");
                params.put("User-agent", "Tinder/7.5.3 (iPhone; iOS 10.3.2; Scale/2.00)");
                return params;
            }
        };
    }

    public Response.Listener<String> getAmurAuthListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                auth_response = response;
                mTextView.setText(response);
                Intent main = new Intent(getApplicationContext(), MainActivity.class);

                Bundle b = new Bundle();
                b.putString("tinder_id", tinder_id);
                b.putString("tinder_token", tinder_token);
                b.putString("base_url", amur_api.base_url);
                b.putString("auth_response", auth_response);
                main.putExtras(b);

                startActivity(main);
                Log.d("MAINACTIVITY", "It starts..............................");
            }
        };
    }

    public void performAmurAuth() {
        mTextView.setText("Oh yeah:) Amurrrr auth...)");
        try {
            String amur_login_str = new JSONObject()
                    .put("action", "SET_AUTH_TOKEN")
                    .put("tinder_id", tinder_id)
                    .put("tinder_auth_token", tinder_token)
                    .toString();
            JsonRequest amur_request = createCustomJsonRequest(
                    amur_api.base_url + "/api",
                    amur_login_str,
                    getAmurAuthListener(),
                    createTinderAuthErrorListener());
            queue.add(amur_request);

        } catch (JSONException ex) {
            mTextView.setText("Amur server request construct fail!");
        }

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

