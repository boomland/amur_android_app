package dating_ml.ru.amur;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;

import java.util.concurrent.ExecutionException;

public class AmurAPI {

    public static String base_url;
    private static boolean is_url_resolved = false;
    private static RequestQueue queue;

    public AmurAPI(RequestQueue queue_main) {
        queue = queue_main;
    }

    public static boolean isURLResolved() {
        return is_url_resolved;
    }

    public static boolean isURLError() {
        return base_url.compareTo("none") == 0;
    }

    public static void resolveURL() throws ExecutionException, InterruptedException {
        String result;
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://dating-ml.ru/get_server.php",
                future,
                future);
        queue.add(stringRequest);
        base_url = future.get();
    }
}