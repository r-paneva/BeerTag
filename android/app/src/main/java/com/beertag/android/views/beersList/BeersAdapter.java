package com.beertag.android.views.beersList;

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
import com.beertag.android.models.Beer;
import com.beertag.android.utils.ImageEncoder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeersAdapter extends RecyclerView.Adapter<BeersAdapter.BeerViewHolder> {
    private final List<Beer> mBeers;
    private OnBeerClickListener mOnBeerClickListener;
    private final ImageEncoder mImageEncoder;

    @Inject
    public BeersAdapter(ImageEncoder imageEncoder) {
        mImageEncoder = imageEncoder;
        mBeers = new ArrayList<>();
    }

    @NonNull
    @Override
    public BeerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.beer_item, parent, false);
        return new BeerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeerViewHolder holder, int position) {
        holder.setOnClickListener(mOnBeerClickListener);
        holder.bind(mBeers.get(position));
    }

    @Override
    public int getItemCount() {
        return mBeers.size();
    }

    public Beer getItem(int position) {
        return mBeers.get(position);
    }

    void clear() {
        mBeers.clear();
    }

    void addAll(List<Beer> Beers) {
        mBeers.addAll(Beers);
    }

    void setOnBeerClickListener(OnBeerClickListener onBeerClickListener) {
        this.mOnBeerClickListener = onBeerClickListener;
    }

    public static class BeerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name_beer)
        TextView mName;

        @BindView(R.id.tv_style)
        TextView mStyle;

        @BindView(R.id.tv_alcohol)
        TextView mAlcohol;

        @BindView(R.id.tv_brewery)
        TextView mBrewery;

        @BindView(R.id.tv_country)
        TextView mCountry;

        @BindView(R.id.iv_beer_image)
        ImageView mBeerImageView;

        private OnBeerClickListener mOnClickListener;
        private Beer mBeer;

        BeerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(Beer beer) {
            mName.setText(beer.getName().toUpperCase());
            mStyle.setText(beer.getStyle().getName());
            mCountry.setText(beer.getCountry().getName());
            mAlcohol.setText(beer.getAlcohol()+"%");
            mBrewery.setText(beer.getBrewery());

            if (Objects.equals(beer.getImage(), null) ||  beer.getImage().length()<= 2 ) {
                mBeerImageView.setImageResource(R.drawable.defaultbeerpicture);
            } else {
                InputStream stream = new ByteArrayInputStream(Base64.decode(beer.getImage().getBytes(), Base64.DEFAULT));
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                mBeerImageView.setImageBitmap(bitmap);
            }

            mBeer = beer;
        }

        @OnClick
        public void onClick() {
            mOnClickListener.onClick(mBeer);
        }

        public void setOnClickListener(OnBeerClickListener onClickListener) {
            mOnClickListener = onClickListener;
        }
    }

    interface OnBeerClickListener {
        void onClick(Beer beer);
    }

}