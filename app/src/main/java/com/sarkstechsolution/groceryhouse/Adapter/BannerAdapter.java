package com.sarkstechsolution.groceryhouse.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.sarkstechsolution.groceryhouse.HomeFragment;
import com.sarkstechsolution.groceryhouse.Models.BannerModel;
import com.sarkstechsolution.groceryhouse.R;


import java.util.List;

public class BannerAdapter extends PagerAdapter {

    Context mContext ;
    List<BannerModel> bannerModelList;

    public BannerAdapter(Context mContext, List<BannerModel> bannerModelList) {
        this.mContext = mContext;
        this.bannerModelList = bannerModelList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutBanner = inflater.inflate(R.layout.layout_banner,null);

        ImageView bannerSlide = layoutBanner.findViewById(R.id.banner_slider);

        bannerSlide.setImageResource(bannerModelList.get(position).getBannerImg());

        container.addView(layoutBanner);

        return layoutBanner;
    }

    @Override
    public int getCount() {
        return bannerModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);

    }
}
