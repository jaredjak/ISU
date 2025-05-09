package com.example.androidexample;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class SkinAdapter extends RecyclerView.Adapter<SkinAdapter.SkinViewHolder> {
    private Context context;
    private List<Skin> skinList;
    private SkinsFragment skinsFragment;
    private String username;

    public SkinAdapter(Context context, List<Skin> skinList, SkinsFragment skinsFragment, String username) {
        this.context = context;
        this.skinList = skinList;
        this.skinsFragment = skinsFragment;
        this.username = username;
    }

    @NonNull
    @Override
    public SkinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_skin, parent, false);
        return new SkinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkinViewHolder holder, int position) {
        Skin skin = skinList.get(position);
        holder.tvName.setText(skin.getColor());
        holder.tvCount.setText("x" + skin.getCount());
        // Set when worm images given
//        holder.ivSkin.setImageResource(skin.getImageResourceId(context));
        String image = skin.getColor().toLowerCase().replace(" ", "");
        int resId = context.getResources().getIdentifier(image, "drawable", context.getPackageName());

        if (resId != 0) {
            holder.ivSkin.setImageResource(resId);
        } else {
            holder.ivSkin.setImageResource(R.drawable.beige);
        }

        boolean isEquipped = skin.getColor().equals(skinsFragment.getSelectedSkinColor());
        boolean isOwned = skin.getCount() > 0;

        if (!isOwned) {
            holder.equip.setText("Locked");
            holder.equip.setEnabled(false);
            holder.equip.setBackgroundColor(Color.parseColor("#D3D3D3"));
        } else if (isEquipped) {
            holder.equip.setText("Equipped");
            holder.equip.setEnabled(false);
            holder.equip.setBackgroundColor(Color.parseColor("#B59410"));
        } else {
            holder.equip.setText("Equip");
            holder.equip.setEnabled(true);
            holder.equip.setBackgroundColor(Color.parseColor("#800080"));
            holder.equip.setOnClickListener(v -> {
                skinsFragment.onEquipClicked(username, skin);
            });
        }

//        /* WILL NEED TO CHANGE THIS TO EQUIP SKIN. */
//        holder.equip.setOnClickListener(v -> {
//            skinsFragment.equipSkin(username, skin);
//        });
    }

    @Override
    public int getItemCount() {
        return skinList.size();
    }

    public static class SkinViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvCount;
        ImageView ivSkin;
        Button equip;

        public SkinViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_skin_name);
//            tvPrice = itemView.findViewById(R.id.tv_skin_price);
            ivSkin = itemView.findViewById(R.id.iv_skin);
            tvCount = itemView.findViewById(R.id.tv_count);
//            buy = itemView.findViewById(R.id.buttonBuy);
            equip = itemView.findViewById(R.id.equip_skin_button);
        }
    }
}
