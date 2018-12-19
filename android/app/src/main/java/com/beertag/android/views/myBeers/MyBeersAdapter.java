package com.beertag.android.views.myBeers;

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
import com.beertag.android.models.MyBeers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyBeersAdapter extends RecyclerView.Adapter<MyBeersAdapter.BeerViewHolder> {

    private final List<MyBeers> mMyBeers;
    private OnBeerClickListener mOnBeerClickListener;

    @Inject
    public MyBeersAdapter() {
        mMyBeers = new ArrayList<>();
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
        holder.bind(mMyBeers.get(position));
    }

    @Override
    public int getItemCount() {
        return mMyBeers.size();
    }

    public MyBeers getItem(int position) {
        return mMyBeers.get(position);
    }

    void clear() {
        mMyBeers.clear();
    }

    void addAllMyBeers(List<MyBeers> myBeers) {
        mMyBeers.addAll(myBeers);
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
        private MyBeers mMyBeer;

        BeerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(MyBeers mybeer) {
            mName.setText(mybeer.getBeer().getName().toUpperCase());
            mStyle.setText(mybeer.getBeer().getStyle().getName());
            mDoDrink.setText(mybeer.getDrink().getName());
            mHowIRateIt.setText(mybeer.getVote().toString());
            if (Objects.equals(mybeer.getBeer().getImage(), null) ||  mybeer.getBeer().getImage().length()<= 2 ) {
                mBeerImageView.setImageResource(R.drawable.defaultbeerpicture);
            } else {
                InputStream stream = new ByteArrayInputStream(Base64.decode(mybeer.getBeer().getImage().getBytes(), Base64.DEFAULT));
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                mBeerImageView.setImageBitmap(bitmap);
            }

            mMyBeer = mybeer;
        }

        @OnClick
        public void onClick() {
            mOnClickListener.onClick(mMyBeer);
        }

        public void setOnClickListener(OnBeerClickListener onClickListener) {
            mOnClickListener = onClickListener;
        }
    }

    interface OnBeerClickListener {
        void onClick(MyBeers myBeers);
    }

}