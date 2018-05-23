package dating_ml.ru.amur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class ChatActivity extends AppCompatActivity {
    private ChatView mChatView;
    private ArrayList<ChatMessage> mMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mChatView = (ChatView) findViewById(R.id.chat_view);
        mChatView.setOnSentMessageListener(new ChatView.OnSentMessageListener(){
            @Override
            public boolean sendMessage(ChatMessage chatMessage){
                return true;
            }
        });
    }
}
