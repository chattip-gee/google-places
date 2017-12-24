package com.fireoneone.android.placesapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.fireoneone.android.placesapp.R;
import com.fireoneone.android.placesapp.controller.FavoritesController;
import com.fireoneone.android.placesapp.custom.LatoBoldTextViewCustom;
import com.fireoneone.android.placesapp.custom.LatoRegularTextViewCustom;
import com.fireoneone.android.placesapp.model.PlaceItem;
import com.fireoneone.android.placesapp.stores.realms.FavoriteItemRealmManager;
import com.fireoneone.android.placesapp.stores.realms.PlaceItemRealmManager;

import java.util.Collections;
import java.util.List;

/**
 * Created by chattipsoontaku on 9/29/2017 AD.
 */

public class PlacesViewAdapter extends RecyclerView.Adapter<PlacesViewAdapter.PlacesViewHolder> {
    Context context;
    private List<PlaceItem> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public PlacesViewAdapter(Context context, List<PlaceItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
    }

    @Override
    public PlacesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_place, parent, false);
        PlacesViewHolder viewHolder = new PlacesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlacesViewHolder holder, int position) {
        final PlaceItem placeItem = mData.get(position);
        initWidget(holder, placeItem);
        setupOnCheckedChangeListener(holder, placeItem);
    }

    private void initWidget(PlacesViewHolder holder, PlaceItem placeItem) {
        holder.textName.setText(placeItem.getName());
        holder.textAddress.setText(placeItem.getAddress());
        holder.textURl.setText(placeItem.getUrl());

        if (FavoritesController.getInstance().getFavByObject(placeItem).isFavorite()) {
            holder.checkFav.setChecked(true);
        }
    }

    private void setupOnCheckedChangeListener(final PlacesViewHolder holder, final PlaceItem placeItem) {
        holder.checkFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    holder.checkFav.setChecked(false);
                } else {
                    holder.checkFav.setChecked(true);
                }

                FavoriteItemRealmManager.getInstance().addFavoriteItem(placeItem.getId(),
                        placeItem.getLat(), placeItem.getLng(), isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public PlaceItem getItem(int id) {
        return mData.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void clearAllData() {
        PlaceItemRealmManager.getInstance().clearAll();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class PlacesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LatoBoldTextViewCustom textName;
        private LatoRegularTextViewCustom textAddress;
        private LatoRegularTextViewCustom textURl;
        private CheckBox checkFav;

        PlacesViewHolder(View itemView) {
            super(itemView);

            textName = (LatoBoldTextViewCustom) itemView.findViewById(R.id.text_name);
            textAddress = (LatoRegularTextViewCustom) itemView.findViewById(R.id.text_address);
            textURl = (LatoRegularTextViewCustom) itemView.findViewById(R.id.text_url);
            checkFav = (CheckBox) itemView.findViewById(R.id.check_favorite);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}