package com.beertag.android.views.userBeers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beertag.android.R;
import com.beertag.android.models.UserBeers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserBeersAdapter extends RecyclerView.Adapter<UserBeersAdapter.BeerViewHolder> {

    private final List<UserBeers> mUserBeers;
    private OnBeerClickListener mOnBeerClickListener;

    @Inject
    public UserBeersAdapter() {
        mUserBeers = new ArrayList<>();
    }

    @NonNull
    @Override
    public BeerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_beer_item, parent, false);
        return new BeerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeerViewHolder holder, int position) {
        holder.setOnClickListener(mOnBeerClickListener);
        holder.bind(mUserBeers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserBeers.size();
    }

    public UserBeers getItem(int position) {
        return mUserBeers.get(position);
    }

    void clear() {
        mUserBeers.clear();
    }

    void addAllUserBeers(List<UserBeers> userBeers) {
        mUserBeers.addAll(userBeers);
    }


    void setOnBeerClickListener(OnBeerClickListener onBeerClickListener) {
        this.mOnBeerClickListener = onBeerClickListener;
    }

    public static class BeerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name_beer)
        TextView mName;

        @BindView(R.id.tv_style)
        TextView mStyle;

        @BindView(R.id.tv_drink)
        TextView mDrink;

        @BindView(R.id.tv_do_drink)
        TextView mDoDrink;

        @BindView(R.id.tv_rating)
        TextView mRating;

        @BindView(R.id.tv_how_i_rate_it)
        TextView mHowIRateIt;

        @BindView(R.id.iv_beer_image)
        ImageView mBeerImageView;

        private OnBeerClickListener mOnClickListener;
        private UserBeers mUserBeer;

        BeerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(UserBeers userbeer) {
            mName.setText(userbeer.getBeer().getName().toUpperCase());
            mStyle.setText(userbeer.getBeer().getStyle().getName());
            mDoDrink.setText(userbeer.getDrink().getName());
            mHowIRateIt.setText(userbeer.getVote().toString());
            if (Objects.equals(userbeer.getBeer().getImage(), null) ||  userbeer.getBeer().getImage().length()<= 2 ) {
                mBeerImageView.setImageResource(R.drawable.defaultbeerpicture);
            } else {
                InputStream stream = new ByteArrayInputStream(Base64.decode(userbeer.getBeer().getImage().getBytes(), Base64.DEFAULT));
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                mBeerImageView.setImageBitmap(bitmap);
            }

            mUserBeer = userbeer;
        }

        @OnClick
        public void onClick() {
            mOnClickListener.onClick(mUserBeer);
        }

        public void setOnClickListener(OnBeerClickListener onClickListener) {
            mOnClickListener = onClickListener;
        }
    }

    interface OnBeerClickListener {
        void onClick(UserBeers userBeers);
    }

}