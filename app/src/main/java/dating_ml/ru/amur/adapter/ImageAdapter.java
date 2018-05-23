package dating_ml.ru.amur.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> mUserUrls;

    ImageAdapter(Context context) {
        mContext = context;
        mUserUrls = null;
    }

    public ImageAdapter(Context context, ArrayList<String> userUrls) {
        mContext = context;
        mUserUrls = userUrls;

        Log.d("ImageAdapter", "userUrls: " + userUrls.toString());
    }

    @Override
    public int getCount() {
        return mUserUrls.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide.with(mContext).load(mUserUrls.get(position)).into(imageView);

        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
