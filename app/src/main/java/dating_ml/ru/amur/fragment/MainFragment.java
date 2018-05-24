package dating_ml.ru.amur.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import dating_ml.ru.amur.AuthActivity;
import dating_ml.ru.amur.MainActivity;
import dating_ml.ru.amur.ProfileActivity;
import dating_ml.ru.amur.R;
import dating_ml.ru.amur.TinderAPI;
import dating_ml.ru.amur.adapter.UserCardAdapter;
import dating_ml.ru.amur.dto.UserDTO;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MainFragment extends AbstractTabFragment {
    private static final int LAYOUT = R.layout.fragment_main;

    private ProgressBar progressBar;
    private CardStackView cardStackView;
    private UserCardAdapter adapter;

    RequestQueue queue;
    String tinder_id;
    String tinder_token;
    String base_url;
    List<UserDTO> spots;
    boolean portion_acquired;

    ArrayList<String> liked_ids;
    ArrayList<String> disliked_ids;
    int last_cnt;
    int first = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        liked_ids = new ArrayList<String>();
        disliked_ids = new ArrayList<String>();
        queue = Volley.newRequestQueue(getContext());
        spots = new ArrayList<>();

        tinder_id = ((MainActivity)getActivity()).tinder_id;
        tinder_token = ((MainActivity)getActivity()).tinder_token;
        base_url = ((MainActivity)getActivity()).base_url;

        portion_acquired = false;
    }

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

        makeEncounters();
        setup();

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
        adapter.addAll(spots);
        return adapter;
    }

    public void makeEncounters() {
        try {
            JSONArray votes = new JSONArray();

            int sum_size = liked_ids.size() + disliked_ids.size();
            String json_req;
            if (sum_size > 0) {
                for (int i = 0; i != liked_ids.size(); ++i) {
                    votes.put(new JSONObject().put("id", liked_ids.get(i)).put("vote", 1));
                }
                for (int i = 0; i != disliked_ids.size(); ++i) {
                    votes.put(new JSONObject().put("id", disliked_ids.get(i)).put("vote", 0));
                }

                liked_ids.clear();
                disliked_ids.clear();

                json_req = new JSONObject()
                        .put("tinder_id", tinder_id)
                        .put("tinder_auth_token", tinder_token)
                        .put("action", "GET_ENCOUNTERS")
                        .put("votes_data", votes)
                        .toString();
            } else {
                json_req = new JSONObject()
                        .put("tinder_id", tinder_id)
                        .put("tinder_auth_token", tinder_token)
                        .put("action", "GET_ENCOUNTERS")
                        .toString();
            }
            JsonRequest amur_get_enc = AuthActivity.createCustomJsonRequest(
                    base_url + "/api",
                    json_req,
                    encountersListener(),
                    encountersError()
            );
            amur_get_enc.setRetryPolicy(new DefaultRetryPolicy(30000, 0, 0));
            queue.add(amur_get_enc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Response.Listener<String> encountersListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    String status = json.getString("status");
                    if (status.compareTo("ok") == 0) {
                        Log.d("Encounters", "Sucessfully got pack of encounters");
                        JSONArray data = json.getJSONArray("data");

                        last_cnt = data.length();
                        if (first == 0) {
                            spots = extractRemainingUsers();
                        }
                        for (int i = 0; i != data.length(); ++i) {
                            JSONObject object = data.getJSONObject(i);

                            String name = object.getString("name");
                            String id = object.getString("id");
                            String photo_url = object.getJSONArray("photos").getJSONObject(0).getString("url");

                            int dist = object.getInt("distance");

                            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
                            Date birthDate = ft.parse(object.getString("birthday"));

                            int gender = object.getInt("gender");
                            String bio = object.getString("bio");
                            ArrayList<String> photoUrls = new ArrayList<String>();

                            JSONArray jsonArray = object.getJSONArray("photos");
                            for (int j = 0; j != jsonArray.length(); ++j) {
                                if (jsonArray.getJSONObject(j).getString("type").compareTo("tinder") == 0) {
                                    photoUrls.add(jsonArray.getJSONObject(j).getString("url"));
                                }
                            }

                            spots.add(new UserDTO(name, id, photo_url, dist, birthDate, id, gender, bio, photoUrls));
                        }
                        portion_acquired = false;
                    } else {
                        Log.d("Encounters", "Not ok response. Error: " + json.getString("error_description"));
                        Toast.makeText(getApplicationContext(),
                                "Something went wrong while GE: " +
                                        json.getString("error_description"),
                                Toast.LENGTH_SHORT);
                    }
                } catch (JSONException je) {
                    je.printStackTrace();
                } catch (ParseException e) {
                    Log.d("DateError", "Дата не была распаршена");
                }
                if (first == 1) {
                    reload();
                    first = 0;
                } else {
                    adapter.clear();
                    adapter.addAll(spots);
                    adapter.notifyDataSetChanged();
                }
            }
        };
    }

    public Response.ErrorListener encountersError() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Encounters", "Error happened: " + error.toString());
            }
        };
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

                if (direction.toString().compareTo("Right") == 0) {
                    // liked
                    String id = spots.get(cardStackView.getTopIndex() - 1).getTinderId();
                    liked_ids.add(id);
                    JsonRequest like_request = TinderAPI.createSimpleGetApiCall(
                            "https://api.gotinder.com/like/" + id,
                            tinder_token);
                    queue.add(like_request);
                }
                if (direction.toString().compareTo("Left") == 0) {
                    // disliked
                    String id = spots.get(cardStackView.getTopIndex() - 1).getTinderId();
                    disliked_ids.add(id);

                    JsonRequest dislike_request = TinderAPI.createSimpleGetApiCall(
                            "https://api.gotinder.com/pass/" + id,
                            tinder_token);
                    queue.add(dislike_request);
                }

                if (cardStackView.getTopIndex() == adapter.getCount() - 5) {
                    Log.d("CardStackView", "NEW PORTION ACQUIRED!");
                    portion_acquired = true;
                    makeEncounters();
                }
                Log.d("CardStackView", "adapter.getCount() == " + adapter.getCount());
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

                UserDTO user = spots.get(index);

                Log.d("CardStackView", "onCardClicked: " + index);
                Log.d("ToursitSpot_user: ", user.toString());

                startUserProfileActivity(user);
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

    private void startUserProfileActivity(UserDTO user) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra(ProfileActivity.USER_INFO, user);
        getActivity().startActivity(intent);
    }
}
