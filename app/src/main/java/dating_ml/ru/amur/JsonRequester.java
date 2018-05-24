package dating_ml.ru.amur;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import co.intentservice.chatui.models.ChatMessage;
import dating_ml.ru.amur.dto.MainUserDTO;
import dating_ml.ru.amur.dto.UserDTO;

public class JsonRequester {
    private RequestQueue queue;

    public JsonRequester(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }

    public static ArrayList<ChatMessage> requestMessages(MainUserDTO mainUser, UserDTO buddy) {
        ArrayList<ChatMessage> res = new ArrayList<>();

        res = createMockMessages();

        return res;
    }

    private static ArrayList<ChatMessage> createMockMessages() {
        ArrayList<ChatMessage> res = new ArrayList<>();
        res.add(new ChatMessage("Привет. Меня зовут Зухра. Давай знакомиться.", System.currentTimeMillis(), ChatMessage.Type.RECEIVED));
        res.add(new ChatMessage("Привет. Имя странное, это настораживает.", System.currentTimeMillis(), ChatMessage.Type.SENT));
        res.add(new ChatMessage("И так сойдет.", System.currentTimeMillis(), ChatMessage.Type.RECEIVED));

        return res;
    }

    public static List<UserDTO> requestMatches(MainUserDTO mainUser) {
        List<UserDTO> res = new ArrayList<>();

        res = createMockMatches();

        return res;
    }


    private static List<UserDTO> createMockMatches() {
        String[] matchName = {"Августа", "Аврора", "Агата", "Агна", "Агнесса", "Агнешка", "Мавра", "Магда", "Магдалена", "Магура", "Мадина", "Мадлена", "Майда", "Майя", "Малика", "Малуша", "Агния", "Агриппина", "Ада", "Адела"};
        String[] matchPhotoUrl = {"http://static5.stcont.com/datas/photos/320x320/60/2e/2349714e5ba5cf1398853afbde88b3c5b22.jpg?0", "https://ru.toluna.com/dpolls_images/2016/12/30/df3a8af9-b58c-4c7c-8354-8f8bce5bf7d1.jpg", "https://i1.sndcdn.com/artworks-000058048090-jfr3hd-t500x500.jpg", "http://re-actor.net/uploads/posts/2010-11/1290934531_4.jpg", "http://mtdata.ru/u24/photoA813/20118705550-0/original.jpeg", "https://scontent-lhr3-1.cdninstagram.com/t51.2885-15/e15/14279116_1581249918848266_1412290040_n.jpg", "http://lh6.googleusercontent.com/-XZjOvUZfzUM/AAAAAAAAAAI/AAAAAAAAAAw/m3wtubs_m_I/photo.jpg", "https://mirra.ru/upload/iblock/855/855442a50ad369a10c572d2d280d73c6.jpg", "https://i04.fotocdn.net/s12/167/user_l/297/2336958630.jpg", "http://www.i-social.ru/imglib/640/01/18/3E/18366198.jpg", "https://cdn1.thehunt.com/app/public/system/zine_images/1043321/original/59ad0f0bf6b310d8eb7b154dd743cedf.jpg", "http://s3.favim.com/orig/39/beautiful-blonde-cute-flowers-girl-Favim.com-324784.jpg", "http://mir-kartinok.my1.ru/_ph/208/2/374058882.jpg?1517270667", "https://new-friend.org/media/cache/8e/ad/8eadccd23b89a906242ddce77cb2f5b7.jpg", "https://cdn1.thehunt.com/app/public/system/zine_images/1942103/original/ff474f5c278e5067069527d2ce751060.jpg", "https://i05.fotocdn.net/s16/195/gallery_s/436/76919746.jpg", "https://i11.fotocdn.net/s24/43/gallery_m/277/2595951658.jpg", "http://img2.1001golos.ru/ratings/132000/131887/pic1.jpg", "https://pbs.twimg.com/profile_images/631078393870729217/0panIZJo.jpg", "https://avatarko.ru/img/avatar/18/devushka_shlyapa_17067.jpg"};

        List<UserDTO> data = new ArrayList<>();

        UserDTO cur;
        ArrayList<String> curPhotoUrls;
        for (int i = 0; i < matchName.length; ++i) {
            curPhotoUrls = new ArrayList<String>();
            curPhotoUrls.add(matchPhotoUrl[i]);

            cur = new UserDTO();
            cur.setName(matchName[i]);
            cur.setPhotoUrls(curPhotoUrls);

            data.add(cur);
        }


        return data;
    }
}
