package dating_ml.ru.amur.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import dating_ml.ru.amur.R;
import dating_ml.ru.amur.dto.UserDTO;

public class UserCardAdapter extends ArrayAdapter<UserDTO> {

    public UserCardAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        ViewHolder holder;

        if (contentView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            contentView = inflater.inflate(R.layout.item_user_card, parent, false);
            holder = new ViewHolder(contentView);
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }

        UserDTO user = getItem(position);

        holder.name.setText(user.name);
        holder.city.setText(user.userAvatar);
        Glide.with(getContext()).load(user.url).into(holder.image);

        return contentView;
    }

    private static class ViewHolder {
        public TextView name;
        public TextView city;
        public ImageView image;

        public ViewHolder(View view) {
            this.name = view.findViewById(R.id.item_user_name);
            this.city = view.findViewById(R.id.item_user_age);
            this.image = view.findViewById(R.id.item_user_photo);
        }
    }

}

