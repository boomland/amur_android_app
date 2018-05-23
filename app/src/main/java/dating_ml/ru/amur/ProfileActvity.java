package dating_ml.ru.amur;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import dating_ml.ru.amur.adapter.ImageAdapter;
import dating_ml.ru.amur.dto.UserDTO;

public class ProfileActvity extends AppCompatActivity {
    final static public String USER_INFO = "USER_INFO";
    private final String mDistText = "Километров до вас ";

    private TextView mNameAndAge;
    private TextView mWorkplace;
    private TextView mPlaceOfStudy;
    private TextView mDistanceToYou;
    private TextView mBiography;

    private ViewPager mProfilePhotosPager;
    private UserDTO mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_actvity);

        mUser = getIntent().getParcelableExtra(USER_INFO);
        Log.d("UserProfileActivity2", mUser.toString());

        mNameAndAge = findViewById(R.id.name_and_age);
        mNameAndAge.setText(mUser.getName() + ", " + mUser.getAge());

        mWorkplace = findViewById(R.id.workplace);
        mWorkplace.setText(mUser.getWorkplace());

        mPlaceOfStudy = findViewById(R.id.place_of_study);
        mPlaceOfStudy.setText(mUser.getPlaceOfStudy());

        mDistanceToYou= findViewById(R.id.distance_to_you);
        mDistanceToYou.setText(mDistText + String.valueOf(mUser.getDist()));

        mBiography = findViewById(R.id.biography);
        mBiography.setText(mUser.getBio());

        mProfilePhotosPager = findViewById(R.id.profile_photos_pager);
        ImageAdapter imageAdapter = new ImageAdapter(this, mUser.getPhotoUrls());
        mProfilePhotosPager.setAdapter(imageAdapter);
    }

}
