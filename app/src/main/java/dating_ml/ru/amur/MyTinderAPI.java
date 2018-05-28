package dating_ml.ru.amur;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.intentservice.chatui.models.ChatMessage;
import dating_ml.ru.amur.dto.MainUser;
import dating_ml.ru.amur.dto.User;

public class MyTinderAPI {
    private static final String tinderURL = "https://api.gotinder.com";
    private static final String authEnd = "/auth";
    private static final String recEnd = "/user/recs";
    private static final String sendMessageToMatchEnd = "/user/matches/";
    private static final String profileEnd = "/profile";
    private static final String updatesEnd = "/updates";


    private RequestQueue queue;


    public MyTinderAPI(Context context) {
        Log.d("MyTinderAPI", "this is constructor");
        queue = Volley.newRequestQueue(context);
    }

    // ------------------------------ Supportive ---------------------------------------------------

    public void doProfileRequest(final String authToken, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        JsonRequest request = createSimpleAPIRequest(Request.Method.GET, tinderURL + profileEnd, "", authToken, listener, errorListener);
        queue.add(request);
    }

    public JsonRequest createUpdatesRequest(final String authToken, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        return createSimpleAPIRequest(Request.Method.POST, tinderURL + updatesEnd, "{\"last_activity_date\": \"\"}", authToken, listener, errorListener);
    }

    /*
    private Response.Listener<String> createMatchesListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MyTinderAPI", "Successful request!");
                Log.d("MyTinderAPI", "This is response: " + response);

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONArray jmatches = null;
                try {
                     jmatches = obj.getJSONArray("matches");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("MyTinderAPI", "This is matches " + jmatches.toString());

                matches.clear();
                assert jmatches != null;
                for (int i = 0; i < jmatches.length(); ++i) {
                    User user = new User();
                    JSONObject person = null;
                    try {
                        person = jmatches.getJSONObject(i).getJSONObject("person");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        assert person != null;
                        user.setId(person.getString("_id"));
                        user.setGender(person.getInt("gender"));
                        user.setName(person.getString("name"));

                        JSONArray jphotos = person.getJSONArray("photos");
                        ArrayList<String> photoUrls = new ArrayList<>();
                        for (int j = 0; j < jphotos.length(); ++j) {
                            photoUrls.add(jphotos.getJSONObject(j).getString("url"));
                        }
                        user.setPhotos(photoUrls);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    matches.add(user);
                }


                Log.d("MyTinderAPI", "This is parsed matches:");
                for (int i = 0; i < matches.size(); ++i) {
                    Log.d("MyTinderAPI", matches.get(i).toString());
                }
            }
        };
    }
    */

    public static JsonRequest createSimpleAPIRequest(int method,
                                                     final String requestUrl,
                                                     final String requestBody,
                                                     final String authToken,
                                                     Response.Listener<String> listener,
                                                     Response.ErrorListener errorListener) {

        JsonRequest request = new JsonRequest(method, requestUrl, requestBody, listener, errorListener) {
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
                params.put("content-type", "application/json");
                params.put("User-agent", "Tinder/7.5.3 (iPhone; iOS 10.3.2; Scale/2.00)");
                params.put("X-Auth-Token", authToken);
                return params;
            }
        };

        return request;
    }

    // ------------------------------ Mock ---------------------------------------------------------

    private static ArrayList<User> createMockMatches() {
        String[] matchName = {"Августа", "Аврора", "Агата", "Агна", "Агнесса", "Агнешка", "Мавра", "Магда", "Магдалена", "Магура", "Мадина", "Мадлена", "Майда", "Майя", "Малика", "Малуша", "Агния", "Агриппина", "Ада", "Адела"};
        String[] matchPhotoUrl = {"http://static5.stcont.com/datas/photos/320x320/60/2e/2349714e5ba5cf1398853afbde88b3c5b22.jpg?0", "https://ru.toluna.com/dpolls_images/2016/12/30/df3a8af9-b58c-4c7c-8354-8f8bce5bf7d1.jpg", "https://i1.sndcdn.com/artworks-000058048090-jfr3hd-t500x500.jpg", "http://re-actor.net/uploads/posts/2010-11/1290934531_4.jpg", "http://mtdata.ru/u24/photoA813/20118705550-0/original.jpeg", "https://scontent-lhr3-1.cdninstagram.com/t51.2885-15/e15/14279116_1581249918848266_1412290040_n.jpg", "http://lh6.googleusercontent.com/-XZjOvUZfzUM/AAAAAAAAAAI/AAAAAAAAAAw/m3wtubs_m_I/photo.jpg", "https://mirra.ru/upload/iblock/855/855442a50ad369a10c572d2d280d73c6.jpg", "https://i04.fotocdn.net/s12/167/user_l/297/2336958630.jpg", "http://www.i-social.ru/imglib/640/01/18/3E/18366198.jpg", "https://cdn1.thehunt.com/app/public/system/zine_images/1043321/original/59ad0f0bf6b310d8eb7b154dd743cedf.jpg", "http://s3.favim.com/orig/39/beautiful-blonde-cute-flowers-girl-Favim.com-324784.jpg", "http://mir-kartinok.my1.ru/_ph/208/2/374058882.jpg?1517270667", "https://new-friend.org/media/cache/8e/ad/8eadccd23b89a906242ddce77cb2f5b7.jpg", "https://cdn1.thehunt.com/app/public/system/zine_images/1942103/original/ff474f5c278e5067069527d2ce751060.jpg", "https://i05.fotocdn.net/s16/195/gallery_s/436/76919746.jpg", "https://i11.fotocdn.net/s24/43/gallery_m/277/2595951658.jpg", "http://img2.1001golos.ru/ratings/132000/131887/pic1.jpg", "https://pbs.twimg.com/profile_images/631078393870729217/0panIZJo.jpg", "https://avatarko.ru/img/avatar/18/devushka_shlyapa_17067.jpg"};

        ArrayList<User> data = new ArrayList<>();

        User cur;
        ArrayList<String> curPhotoUrls;
        for (int i = 0; i < matchName.length; ++i) {
            curPhotoUrls = new ArrayList<>();
            curPhotoUrls.add(matchPhotoUrl[i]);

            cur = new User();
            cur.setName(matchName[i]);
            cur.setPhotos(curPhotoUrls);

            data.add(cur);
        }

        return data;
    }

    private static ArrayList<ChatMessage> createMockMessages() {
        ArrayList<ChatMessage> res = new ArrayList<>();
        res.add(new ChatMessage("Привет. Меня зовут Зухра. Давай знакомиться.", System.currentTimeMillis(), ChatMessage.Type.RECEIVED));
        res.add(new ChatMessage("Привет. Имя странное, это настораживает.", System.currentTimeMillis(), ChatMessage.Type.SENT));
        res.add(new ChatMessage("И так сойдет.", System.currentTimeMillis(), ChatMessage.Type.RECEIVED));

        return res;
    }

    public static Response.Listener<String> createTinderListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MyTinderAPI", "Successful request!");
            }
        };
    }

    public static Response.ErrorListener createErrorTinderListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MyTinderAPI", "Error: " + error.toString());
            }
        };
    }
}
