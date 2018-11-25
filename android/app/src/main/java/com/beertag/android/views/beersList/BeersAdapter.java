package com.beertag.android.views.beersList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beertag.android.R;
import com.beertag.android.models.Beer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeersAdapter extends RecyclerView.Adapter<BeersAdapter.BeerViewHolder> {
    private List<Beer> mBeers;
    private OnBeerClickListener mOnBeerClickListener;

    @Inject
    public BeersAdapter() {
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

    public void clear() {
        mBeers.clear();
    }

    public void addAll(List<Beer> Beers) {
        mBeers.addAll(Beers);
    }

    public void setOnBeerClickListener(OnBeerClickListener onBeerClickListener) {
        this.mOnBeerClickListener = onBeerClickListener;
    }

    public static class BeerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name_beer)
        TextView mName;

        @BindView(R.id.tv_style)
        TextView mStyle;

        @BindView(R.id.tv_country)
        TextView mCountry;

        @BindView(R.id.iv_beer)
        ImageView mBeerImageView;

        private OnBeerClickListener mOnClickListener;
        private Beer mBeer;

        BeerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(Beer beer) {
            mName.setText(beer.getName());
            mStyle.setText(beer.getStyle());
            mCountry.setText(beer.getOrigin());
            if(!beer.getImage().isEmpty()) {
                Picasso.get()
                        .load(beer.getImage())
                        .into(mBeerImageView);
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