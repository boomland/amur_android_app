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

import com.bumptech.glide.Glide;

import java.util.List;

import dating_ml.ru.amur.ChatActivity;
import dating_ml.ru.amur.Constants;
import dating_ml.ru.amur.JsonRequester;
import dating_ml.ru.amur.MainActivity;
import dating_ml.ru.amur.MyTinderAPI;
import dating_ml.ru.amur.R;
import dating_ml.ru.amur.dto.User;


public class MatchesFragment extends AbstractTabFragment {
    private static final int LAYOUT = R.layout.fragment_matches;
    private MyTinderAPI tinderAPI;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        RecyclerView rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(new MatchListAdapter(JsonRequester.createMockMatches()));

        return view;
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
            return data.size();
        }


    }
}
