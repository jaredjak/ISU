package com.example.androidexample;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.ViewHolder> {
    private Context context;
    private List<Skin> skinList;
    private MarketplaceFragment marketFragment;
    private String username;
    private double[] buyPrices;
    private double[] sellPrices;

    public MarketAdapter(Context context, List<Skin> skinList, MarketplaceFragment marketFragment, String username, double[] buyPrices, double[] sellPrices) {
        this.context = context;
        this.skinList = skinList;
        this.marketFragment = marketFragment;
        this.username = username;
        this.buyPrices = buyPrices;
        this.sellPrices = sellPrices;

        Log.d("DEBUG", "username in constructor:" + username);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_marketskin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Skin skin = skinList.get(position);
        holder.skinName.setText(skin.getColor());
        holder.skinCount.setText("x" + skin.getCount());

        holder.skinBuyPrice.setText(String.format("$%.2f", buyPrices[position]));
        holder.skinSellPrice.setText(String.format("$%.2f", sellPrices[position]));
        // Set when worm images given
//        holder.ivSkin.setImageResource(skin.getImageResourceId(context));
        String image = skin.getColor().toLowerCase().replace(" ", "");
        int resId = context.getResources().getIdentifier(image, "drawable", context.getPackageName());

        if (resId != 0) {
            holder.ivSkin.setImageResource(resId);
        } else {
            holder.ivSkin.setImageResource(R.drawable.beige);
        }

        Log.d("DEBUG", "username: " + username);

        holder.sell.setOnClickListener(v -> {
            if (skin.getCount() < 100) {
                marketFragment.sellSkin(username, skin);
            }
        });
        holder.buy.setOnClickListener(v -> {
            if (skin.getCount() > 0) {
                marketFragment.buySkin(username, skin);
            }
        });
    }

    @Override
    public int getItemCount() {
        return skinList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView skinName, skinBuyPrice, skinSellPrice, skinCount;
        ImageView ivSkin;
        Button buy, sell;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            skinName = itemView.findViewById(R.id.tv_skin_name);
            skinBuyPrice = itemView.findViewById(R.id.tv_skin_buy_price);
            skinSellPrice = itemView.findViewById(R.id.tv_skin_sell_price);
            ivSkin = itemView.findViewById(R.id.iv_skin);
            skinCount = itemView.findViewById(R.id.tv_count);
            buy = itemView.findViewById(R.id.buttonBuy);
            sell = itemView.findViewById(R.id.sellButton);
        }
    }
}
