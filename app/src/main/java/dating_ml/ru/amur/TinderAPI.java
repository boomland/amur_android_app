package dating_ml.ru.amur;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class TinderAPI {

    public static JsonRequest createSimpleGetApiCall(String request_url,
                                                     final String auth_token) {
        return new JsonRequest(Request.Method.GET,
                request_url,
                "",
                createTinderListener(),
                createErrorTinderListener()) {
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
                params.put("X-Auth-Token", auth_token);
                return params;
            }
        };
    }

    public static Response.Listener<String> createTinderListener() {
        return new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("TinderAPI", "Successful request!");
            }
        };
    }

    public static Response.ErrorListener createErrorTinderListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TinderAPI", "Error: " + error.toString());
            }
        };
    }
}