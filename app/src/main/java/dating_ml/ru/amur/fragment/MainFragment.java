package dating_ml.ru.amur.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dating_ml.ru.amur.R;
import dating_ml.ru.amur.adapter.UserCardAdapter;
import dating_ml.ru.amur.dto.UserDTO;


public class MainFragment extends AbstractTabFragment {
    private static final int LAYOUT = R.layout.fragment_main;


    private ProgressBar progressBar;
    private CardStackView cardStackView;
    private UserCardAdapter adapter;

    public static MainFragment getInstance(Context context) {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);

        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_main));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        setup();
        reload();

        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private UserDTO createUser() {
        return new UserDTO("Yasaka Shrine", "Kyoto", "https://source.unsplash.com/Xq1ntWruZQI/600x800");
    }

    private List<UserDTO> createUsers() {
        List<UserDTO> users = new ArrayList<>();

        String[] matchName = {"Августа", "Аврора", "Агата", "Агна", "Агнесса", "Агнешка", "Мавра", "Магда", "Магдалена", "Магура", "Мадина", "Мадлена", "Майда", "Майя", "Малика", "Малуша", "Агния", "Агриппина", "Ада", "Адела"};
        String[] matchPhotoUrl = {"http://static5.stcont.com/datas/photos/320x320/60/2e/2349714e5ba5cf1398853afbde88b3c5b22.jpg?0", "https://ru.toluna.com/dpolls_images/2016/12/30/df3a8af9-b58c-4c7c-8354-8f8bce5bf7d1.jpg", "https://i1.sndcdn.com/artworks-000058048090-jfr3hd-t500x500.jpg", "http://re-actor.net/uploads/posts/2010-11/1290934531_4.jpg", "http://mtdata.ru/u24/photoA813/20118705550-0/original.jpeg", "https://scontent-lhr3-1.cdninstagram.com/t51.2885-15/e15/14279116_1581249918848266_1412290040_n.jpg", "http://lh6.googleusercontent.com/-XZjOvUZfzUM/AAAAAAAAAAI/AAAAAAAAAAw/m3wtubs_m_I/photo.jpg", "https://mirra.ru/upload/iblock/855/855442a50ad369a10c572d2d280d73c6.jpg", "https://i04.fotocdn.net/s12/167/user_l/297/2336958630.jpg", "http://www.i-social.ru/imglib/640/01/18/3E/18366198.jpg", "https://cdn1.thehunt.com/app/public/system/zine_images/1043321/original/59ad0f0bf6b310d8eb7b154dd743cedf.jpg", "http://s3.favim.com/orig/39/beautiful-blonde-cute-flowers-girl-Favim.com-324784.jpg", "http://mir-kartinok.my1.ru/_ph/208/2/374058882.jpg?1517270667", "https://new-friend.org/media/cache/8e/ad/8eadccd23b89a906242ddce77cb2f5b7.jpg", "https://cdn1.thehunt.com/app/public/system/zine_images/1942103/original/ff474f5c278e5067069527d2ce751060.jpg", "https://i05.fotocdn.net/s16/195/gallery_s/436/76919746.jpg", "https://i11.fotocdn.net/s24/43/gallery_m/277/2595951658.jpg", "http://img2.1001golos.ru/ratings/132000/131887/pic1.jpg", "https://pbs.twimg.com/profile_images/631078393870729217/0panIZJo.jpg", "https://avatarko.ru/img/avatar/18/devushka_shlyapa_17067.jpg"};

        UserDTO cur;
        for (int i = 0; i < matchName.length; ++i) {
            cur = new UserDTO();
            cur.name = matchName[i];
            cur.url = matchPhotoUrl[i];
            cur.userAvatar = "26 years";

            users.add(cur);
        }
        return users;
    }

    private UserCardAdapter createUserCardAdapter() {
        final UserCardAdapter adapter = new UserCardAdapter(context);
        adapter.addAll(createUsers());
        return adapter;
    }

    private void setup() {
        progressBar = view.findViewById(R.id.activity_main_progress_bar);

        cardStackView = view.findViewById(R.id.activity_main_card_stack_view);
        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {
            @Override
            public void onCardDragging(float percentX, float percentY) {
                Log.d("CardStackView", "onCardDragging");
            }

            @Override
            public void onCardSwiped(SwipeDirection direction) {
                Log.d("CardStackView", "onCardSwiped: " + direction.toString());
                Log.d("CardStackView", "topIndex: " + cardStackView.getTopIndex());
                if (cardStackView.getTopIndex() == adapter.getCount() - 5) {
                    Log.d("CardStackView", "Paginate: " + cardStackView.getTopIndex());
                    paginate();
                }
            }

            @Override
            public void onCardReversed() {
                Log.d("CardStackView", "onCardReversed");
            }

            @Override
            public void onCardMovedToOrigin() {
                Log.d("CardStackView", "onCardMovedToOrigin");
            }

            @Override
            public void onCardClicked(int index) {
                Log.d("CardStackView", "onCardClicked: " + index);
            }
        });
    }

    private void reload() {
        cardStackView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = createUserCardAdapter();
                cardStackView.setAdapter(adapter);
                cardStackView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }, 1000);
    }

    private LinkedList<UserDTO> extractRemainingUsers() {
        LinkedList<UserDTO> users = new LinkedList<>();
        for (int i = cardStackView.getTopIndex(); i < adapter.getCount(); i++) {
            users.add(adapter.getItem(i));
        }
        return users;
    }

    private void addFirst() {
        LinkedList<UserDTO> users = extractRemainingUsers();
        users.addFirst(createUser());
        adapter.clear();
        adapter.addAll(users);
        adapter.notifyDataSetChanged();
    }

    private void addLast() {
        LinkedList<UserDTO> users = extractRemainingUsers();
        users.addLast(createUser());
        adapter.clear();
        adapter.addAll(users);
        adapter.notifyDataSetChanged();
    }

    private void removeFirst() {
        LinkedList<UserDTO> users = extractRemainingUsers();
        if (users.isEmpty()) {
            return;
        }

        users.removeFirst();
        adapter.clear();
        adapter.addAll(users);
        adapter.notifyDataSetChanged();
    }

    private void removeLast() {
        LinkedList<UserDTO> users = extractRemainingUsers();
        if (users.isEmpty()) {
            return;
        }

        users.removeLast();
        adapter.clear();
        adapter.addAll(users);
        adapter.notifyDataSetChanged();
    }

    private void paginate() {
        cardStackView.setPaginationReserved();
        adapter.addAll(createUsers());
        adapter.notifyDataSetChanged();
    }

    public void swipeLeft() {
        List<UserDTO> users = extractRemainingUsers();
        if (users.isEmpty()) {
            return;
        }

        View target = cardStackView.getTopView();
        View targetOverlay = cardStackView.getTopView().getOverlayContainer();

        ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", -10f));
        rotation.setDuration(200);
        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, -2000f));
        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, 500f));
        translateX.setStartDelay(100);
        translateY.setStartDelay(100);
        translateX.setDuration(500);
        translateY.setDuration(500);
        AnimatorSet cardAnimationSet = new AnimatorSet();
        cardAnimationSet.playTogether(rotation, translateX, translateY);

        ObjectAnimator overlayAnimator = ObjectAnimator.ofFloat(targetOverlay, "alpha", 0f, 1f);
        overlayAnimator.setDuration(200);
        AnimatorSet overlayAnimationSet = new AnimatorSet();
        overlayAnimationSet.playTogether(overlayAnimator);

        cardStackView.swipe(SwipeDirection.Left, cardAnimationSet, overlayAnimationSet);
    }

    public void swipeRight() {
        List<UserDTO> users = extractRemainingUsers();
        if (users.isEmpty()) {
            return;
        }

        View target = cardStackView.getTopView();
        View targetOverlay = cardStackView.getTopView().getOverlayContainer();

        ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", 10f));
        rotation.setDuration(200);
        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, 2000f));
        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, 500f));
        translateX.setStartDelay(100);
        translateY.setStartDelay(100);
        translateX.setDuration(500);
        translateY.setDuration(500);
        AnimatorSet cardAnimationSet = new AnimatorSet();
        cardAnimationSet.playTogether(rotation, translateX, translateY);

        ObjectAnimator overlayAnimator = ObjectAnimator.ofFloat(targetOverlay, "alpha", 0f, 1f);
        overlayAnimator.setDuration(200);
        AnimatorSet overlayAnimationSet = new AnimatorSet();
        overlayAnimationSet.playTogether(overlayAnimator);

        cardStackView.swipe(SwipeDirection.Right, cardAnimationSet, overlayAnimationSet);
    }

    private void reverse() {
        cardStackView.reverse();
    }
}
