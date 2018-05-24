package dating_ml.ru.amur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;
import dating_ml.ru.amur.dto.MainUserDTO;
import dating_ml.ru.amur.dto.UserDTO;

public class ChatActivity extends AppCompatActivity {
    private MainUserDTO mMainUser;
    private UserDTO mBuddy;
    private ChatView mChatView;
    private ArrayList<ChatMessage> mMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        receiveData();

        mMessages = JsonRequester.requestMessages(mMainUser, mBuddy);

        mChatView = findViewById(R.id.chat_view);
        mChatView.addMessages(mMessages);
        mChatView.setOnSentMessageListener(new ChatView.OnSentMessageListener(){
            @Override
            public boolean sendMessage(ChatMessage chatMessage){
                return true;
            }
        });
    }

    private void receiveData() {
        Bundle b = getIntent().getExtras();

        assert b != null;
        mMainUser = b.getParcelable(Constants.MAIN_USER);
        mBuddy = b.getParcelable(Constants.BUDDY);
    }
}
