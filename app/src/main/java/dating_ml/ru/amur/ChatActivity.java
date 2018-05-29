package dating_ml.ru.amur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;
import dating_ml.ru.amur.dto.MainUser;
import dating_ml.ru.amur.dto.User;

public class ChatActivity extends AppCompatActivity {
    private MainUser mMainUser;
    private User mBuddy;
    private ChatView mChatView;
    private ArrayList<ChatMessage> mMessages;
    private MyTinderAPI tinderAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        receiveData();

        tinderAPI = new MyTinderAPI(getApplicationContext());
        mChatView = findViewById(R.id.chat_view);
        mMessages = new ArrayList<>();
        mChatView.setOnSentMessageListener(new ChatView.OnSentMessageListener(){
            @Override
            public boolean sendMessage(ChatMessage chatMessage){
                return true;
            }
        });

//        mMessages = JsonRequester.requestMessages(mMainUser, mBuddy);

        tinderAPI.doUpdatesRequest(mMainUser.getToken(), createDoUpdatesRequestListener(), createDoUpdatesRequestErrorListener());
    }

    private Response.ErrorListener createDoUpdatesRequestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ChatActivity", "Error while doing DoUpdatesRequest: error " + error.getMessage());
            }
        };
    }

    private Response.Listener<String> createDoUpdatesRequestListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ChatActivity", "This is response from UpdatesRequest: " + response);

                JSONObject obj = null;
                try {
                     obj = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONArray matches = null;
                try {
                    assert obj != null;
                    matches = obj.getJSONArray("matches");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject match = null;
                JSONObject person;
                assert matches != null;
                for (int i = 0; i < matches.length(); ++i) {
                    try {
                        match = matches.getJSONObject(i);
                        person = match.getJSONObject("person");

                        Log.d("ChatActivity", "This is person from for: " + person.toString());
                        if (person.get("_id").equals(mBuddy.getId())) {
                            break;
                        } else if (i + 1 == matches.length()) {
                            match = null;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                assert match != null;
                Log.d("ChatActivity", "This is approprioate match: " + match.toString());

                JSONArray jMessages = null;
                try {
                    jMessages = match.getJSONArray("messages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                assert jMessages != null;
                Log.d("ChatActivity", "This is jMessages: " + jMessages.toString());

                try {
                    mMessages = JsonParser.parseMessages(jMessages, mBuddy.getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("ChatActivity", "This is parsed mMessages: " + mMessages.toString());
                mChatView.addMessages(mMessages);
            }
        };
    }

    private void receiveData() {
        Bundle b = getIntent().getExtras();

        assert b != null;
        mMainUser = b.getParcelable(Constants.MAIN_USER);
        mBuddy = b.getParcelable(Constants.BUDDY);

        assert mBuddy != null;
        assert mMainUser != null;
        Log.d("ChatActivity", "This is received mBuddy: " + mBuddy.toString());
        Log.d("ChatActivity", "This is receved mMainUser: " + mMainUser.toString());
    }
}
