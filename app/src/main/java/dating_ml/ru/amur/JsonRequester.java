package dating_ml.ru.amur;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.intentservice.chatui.models.ChatMessage;
import dating_ml.ru.amur.API.AmurAPI;
import dating_ml.ru.amur.dto.MainUser;
import dating_ml.ru.amur.dto.User;

public class JsonRequester {
    public RequestQueue queue;
    public AmurAPI amurAPI;

    public JsonRequester(Context context) {
        this.queue = Volley.newRequestQueue(context);
        amurAPI = new AmurAPI(queue);
    }

    public static ArrayList<ChatMessage> requestMessages(MainUser mainUser, User buddy) {
        ArrayList<ChatMessage> res;

        res = createMockMessages();

        return res;
    }

    public static List<User> requestMatches(MainUser mainUser) {
        List<User> res;

        res = createMockMatches();

        return res;
    }

    public void doTinderAuthRequest(String facebookId, String facebookToken, Response.Listener<String> response_listener, Response.ErrorListener error_listener) {
        JsonRequest tinder_request = createCustomJsonRequest("https://api.gotinder.com/auth",
                "{\"facebook_id\": \"" + facebookId + "\", \"facebook_token\": \"" + facebookToken + "\"}",
                response_listener,
                error_listener);

        queue.add(tinder_request);
    }

    public void resolveAmurUrl(Response.Listener<String> response_listener, Response.ErrorListener error_listener) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "http://dating-ml.ru/get_server.php",
                response_listener,
                error_listener);

        queue.add(stringRequest);
    }

    public static JsonRequest createCustomJsonRequest(String request_url, String data, Response.Listener<String> response_listener, Response.ErrorListener error_listener) {
        return new JsonRequest(Request.Method.POST, request_url, data, response_listener, error_listener) {
            @Override
            public int compareTo(@NonNull Object o) {
                return 0;
            }

            @Override
            protected Response parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, "UTF-8");
                    return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
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

    private static ArrayList<ChatMessage> createMockMessages() {
        ArrayList<ChatMessage> res = new ArrayList<>();
        res.add(new ChatMessage("Привет. Меня зовут Зухра. Давай знакомиться.", System.currentTimeMillis(), ChatMessage.Type.RECEIVED));
        res.add(new ChatMessage("Привет. Имя странное, это настораживает.", System.currentTimeMillis(), ChatMessage.Type.SENT));
        res.add(new ChatMessage("И так сойдет.", System.currentTimeMillis(), ChatMessage.Type.RECEIVED));

        return res;
    }

    public static List<User> createMockMatches() {
        String[] matchName = {"Августа", "Аврора", "Агата", "Агна", "Агнесса", "Агнешка", "Мавра", "Магда", "Магдалена", "Магура", "Мадина", "Мадлена", "Майда", "Майя", "Малика", "Малуша", "Агния", "Агриппина", "Ада", "Адела"};
        String[] matchPhotoUrl = {"http://static5.stcont.com/datas/photos/320x320/60/2e/2349714e5ba5cf1398853afbde88b3c5b22.jpg?0", "https://ru.toluna.com/dpolls_images/2016/12/30/df3a8af9-b58c-4c7c-8354-8f8bce5bf7d1.jpg", "https://i1.sndcdn.com/artworks-000058048090-jfr3hd-t500x500.jpg", "http://re-actor.net/uploads/posts/2010-11/1290934531_4.jpg", "http://mtdata.ru/u24/photoA813/20118705550-0/original.jpeg", "https://scontent-lhr3-1.cdninstagram.com/t51.2885-15/e15/14279116_1581249918848266_1412290040_n.jpg", "http://lh6.googleusercontent.com/-XZjOvUZfzUM/AAAAAAAAAAI/AAAAAAAAAAw/m3wtubs_m_I/photo.jpg", "https://mirra.ru/upload/iblock/855/855442a50ad369a10c572d2d280d73c6.jpg", "https://i04.fotocdn.net/s12/167/user_l/297/2336958630.jpg", "http://www.i-social.ru/imglib/640/01/18/3E/18366198.jpg", "https://cdn1.thehunt.com/app/public/system/zine_images/1043321/original/59ad0f0bf6b310d8eb7b154dd743cedf.jpg", "http://s3.favim.com/orig/39/beautiful-blonde-cute-flowers-girl-Favim.com-324784.jpg", "http://mir-kartinok.my1.ru/_ph/208/2/374058882.jpg?1517270667", "https://new-friend.org/media/cache/8e/ad/8eadccd23b89a906242ddce77cb2f5b7.jpg", "https://cdn1.thehunt.com/app/public/system/zine_images/1942103/original/ff474f5c278e5067069527d2ce751060.jpg", "https://i05.fotocdn.net/s16/195/gallery_s/436/76919746.jpg", "https://i11.fotocdn.net/s24/43/gallery_m/277/2595951658.jpg", "http://img2.1001golos.ru/ratings/132000/131887/pic1.jpg", "https://pbs.twimg.com/profile_images/631078393870729217/0panIZJo.jpg", "https://avatarko.ru/img/avatar/18/devushka_shlyapa_17067.jpg"};

        List<User> data = new ArrayList<>();

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
}
