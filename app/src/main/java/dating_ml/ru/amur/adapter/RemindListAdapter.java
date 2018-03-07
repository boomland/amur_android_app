package dating_ml.ru.amur.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.List;

import dating_ml.ru.amur.R;
import dating_ml.ru.amur.dto.RemindDTO;


public class RemindListAdapter extends RecyclerView.Adapter<RemindListAdapter.RemindViewHolder> {
    private List<RemindDTO> data;

    public RemindListAdapter(List<RemindDTO> data) {
        this.data = data;
    }

    @Override
    public RemindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remind_item, parent, false);

        return new RemindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RemindViewHolder holder, int position) {
        RemindDTO item = data.get(position);
        holder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class RemindViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView title;

        public RemindViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            cardView = itemView.findViewById(R.id.cardView);
            title = itemView.findViewById(R.id.title);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), title.getText() +" was clicked.", Toast.LENGTH_SHORT).show();
        }
    }
}
