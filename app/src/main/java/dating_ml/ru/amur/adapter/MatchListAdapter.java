package dating_ml.ru.amur.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import dating_ml.ru.amur.R;
import dating_ml.ru.amur.dto.UserDTO;


public class MatchListAdapter extends RecyclerView.Adapter<MatchListAdapter.MatchViewHolder> {
    private List<UserDTO> data;

    public MatchListAdapter(List<UserDTO> data) {
        this.data = data;
    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item, parent, false);

        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MatchViewHolder holder, int position) {
        UserDTO item = data.get(position);

        holder.userName.setText(item.getName());
        if (item.getPhotoUrls() != null && !item.getPhotoUrls().isEmpty()) {
            Glide.with(holder.itemView).load(item.getPhotoUrls().get(0)).into(holder.matchImage);
        }
        holder.lastMessage.setText(item.getName() + " говорит \"Зря-зря\"");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView userName;
        ImageView matchImage;
        TextView lastMessage;

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
            Toast.makeText(v.getContext(), userName.getText() +" was clicked.", Toast.LENGTH_SHORT).show();
        }
    }
}
