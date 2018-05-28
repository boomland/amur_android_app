package dating_ml.ru.amur;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import dating_ml.ru.amur.adapter.ImageAdapter;
import dating_ml.ru.amur.dto.RecUser;

public class ProfileActivity extends AppCompatActivity {
    final static public String USER_INFO = "USER_INFO";
    private final String mDistText = "Километров до вас ";

    private TextView mNameAndAge;
    private TextView mWorkplace;
    private TextView mPlaceOfStudy;
    private TextView mDistanceToYou;
    private TextView mBiography;

    private ViewPager mProfilePhotosPager;
    private RecUser mRecUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_actvity);

        mRecUser = getIntent().getParcelableExtra(USER_INFO);
        Log.d("UserProfileActivity2", mRecUser.toString());

        mNameAndAge = findViewById(R.id.name_and_age);
        mNameAndAge.setText(mRecUser.getName() + ", " + mRecUser.getBirthDate());

        mWorkplace = findViewById(R.id.workplace);
        mWorkplace.setText("Google");

        mPlaceOfStudy = findViewById(R.id.place_of_study);
        mPlaceOfStudy.setText("МГУ");

        mDistanceToYou= findViewById(R.id.distance_to_you);
        mDistanceToYou.setText(mDistText + String.valueOf(mRecUser.getDistance()));

        mBiography = findViewById(R.id.biography);
        mBiography.setText(mRecUser.getBio());

        mProfilePhotosPager = findViewById(R.id.profile_photos_pager);
        ImageAdapter imageAdapter = new ImageAdapter(this, mRecUser.getPhotos());
        mProfilePhotosPager.setAdapter(imageAdapter);
    }

}
