package dating_ml.ru.amur.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import dating_ml.ru.amur.MainActivity;
import dating_ml.ru.amur.R;
import dating_ml.ru.amur.dto.MainUser;


public class ProfileFragment extends AbstractTabFragment {
    private static final int LAYOUT = R.layout.fragment_profile_new;

    public static ProfileFragment getInstance(Context context) {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);

        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_profile));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        MainUser mainUser = ((MainActivity)getActivity()).mainUser;

        ImageView avatar = view.findViewById(R.id.profile_user_photo);
        TextView name = view.findViewById(R.id.profile_name);
        TextView gender = view.findViewById(R.id.profile_gender);
        TextView birthDay = view.findViewById(R.id.profile_birthday);
        TextView bio = view.findViewById(R.id.profile_bio);
        TextView ageFilter = view.findViewById(R.id.profile_age_filter);
        TextView maxDist = view.findViewById(R.id.profile_distance_filter);

        try {
            Glide.with(Objects.requireNonNull(getActivity())).load(mainUser.getPhotos().get(0)).into(avatar);
        } catch (Exception e) {
            e.printStackTrace();
        }

        name.setText(mainUser.getName());
        if (mainUser.getGender() == 0) {
            gender.setText("Женский пол");
        } else {
            gender.setText("Мужской пол");
        }

        birthDay.setText(mainUser.getBirthDate());

        bio.setText(mainUser.getBio());
        ageFilter.setText("от " + String.valueOf(mainUser.getAgeFilterMin()) + " до " + String.valueOf(mainUser.getAgeFilterMax()) + " лет");
        maxDist.setText(String.valueOf(mainUser.getDistanceFilter()));

        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
