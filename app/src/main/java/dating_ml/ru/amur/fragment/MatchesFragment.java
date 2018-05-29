package dating_ml.ru.amur.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dating_ml.ru.amur.ChatActivity;
import dating_ml.ru.amur.Constants;
import dating_ml.ru.amur.JsonParser;
import dating_ml.ru.amur.JsonRequester;
import dating_ml.ru.amur.MainActivity;
import dating_ml.ru.amur.MyTinderAPI;
import dating_ml.ru.amur.R;
import dating_ml.ru.amur.dto.User;


public class MatchesFragment extends AbstractTabFragment {
    private static final int LAYOUT = R.layout.fragment_matches;
    private MyTinderAPI tinderAPI;
    private ArrayList<User> matches;

    public static MatchesFragment getInstance(Context context) {
        Bundle args = new Bundle();
        MatchesFragment fragment = new MatchesFragment();
        fragment.setArguments(args);

        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_matches));

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tinderAPI = new MyTinderAPI(getActivity());
        matches = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        RecyclerView rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(context));

        rv.setAdapter(new MatchListAdapter(matches));
        tinderAPI.doUpdatesRequest(((MainActivity) Objects.requireNonNull(getActivity())).mainUser.getToken(), createDoUpdatesRequestListener(rv.getAdapter()), createDoUpdatesRequestErrorListener());

        return view;
    }

    private Response.ErrorListener createDoUpdatesRequestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MatchesFragment", "Error while doing DoUpdatesRequest: error = " + error.getMessage());
            }
        };
    }

    private Response.Listener<String> createDoUpdatesRequestListener(final RecyclerView.Adapter adapter) {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MatchesFragment", "This is response of DoUpdatesRequest: " + response);

                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONArray jMatches = null;
                try {
                    assert obj != null;
                    jMatches = obj.getJSONArray("matches");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                matches.clear();

                JSONObject person = null;
                assert jMatches != null;
                for (int i = 0; i < jMatches.length(); ++i) {
                    try {
                        person = jMatches.getJSONObject(i).getJSONObject("person");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        assert person != null;
                        matches.add(JsonParser.parseUserFromPerson(person));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter.notifyDataSetChanged();
            }
        };
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class MatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView userName;
        ImageView matchImage;
        TextView lastMessage;
        public User mUser;

        public MatchViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            cardView = itemView.findViewById(R.id.cardView);
            userName = itemView.findViewById(R.id.userName);
            matchImage = itemView.findViewById(R.id.matchImage);
            lastMessage = itemView.findViewById(R.id.lastMessage);
        }

        @Override
        public void onClick(View v) {
            startChatActivity();
            Log.d("MatchesFramgent", "ChatActivity started");
        }

        private void startChatActivity() {
            Intent intent = new Intent(getActivity(), ChatActivity.class);

            Bundle b = new Bundle();
            b.putParcelable(Constants.MAIN_USER, ((MainActivity)getActivity()).mainUser);
            b.putParcelable(Constants.BUDDY, mUser);

            intent.putExtras(b);

            startActivity(intent);
        }
    }

    public class MatchListAdapter extends RecyclerView.Adapter<MatchViewHolder> {
        private List<User> data;

        public MatchListAdapter(List<User> data) {
            this.data = data;
        }

        @Override
        public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item, parent, false);

            return new MatchViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MatchViewHolder holder, int position) {
            User item = data.get(position);

            holder.mUser = item;
            holder.userName.setText(item.getName());
            if (item.getPhotos() != null && !item.getPhotos().isEmpty()) {
                Glide.with(holder.itemView).load(item.getPhotos().get(0)).into(holder.matchImage);
            }
            holder.lastMessage.setText(item.getName() + " говорит \"Зря-зря\"");
        }

        @Override
        public int getItemCount() {
            Log.d("MatchesFragment", "getItemCount() = " + data.size());
            return data.size();
        }


    }
}
