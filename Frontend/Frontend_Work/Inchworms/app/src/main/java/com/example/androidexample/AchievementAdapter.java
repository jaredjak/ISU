package com.example.androidexample;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {
    private List<Achievement> achievementList;
    private String selectedAchievement;
    private Context context;
    private OnEquipClickListener equipClickListener;

    private int[] moneyTiers;
    private int[] nonMoneyTiers;

    public interface OnEquipClickListener {
        void onEquipClicked(String selectedAchievement);
    }

    public AchievementAdapter(Context context, List<Achievement> achievementList, String selectedAchievement, int[] moneyTiers, int[] nonMoneyTiers, OnEquipClickListener listener) {
        this.context = context;
        this.achievementList = achievementList;
        this.selectedAchievement = selectedAchievement;
        this.moneyTiers = moneyTiers;
        this.nonMoneyTiers = nonMoneyTiers;
        this.equipClickListener = listener;
    }

    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_achievements, parent, false);
        return new AchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        Achievement achievement = achievementList.get(position);

        holder.name.setText(achievement.getName());
        holder.description.setText(achievement.getDescription());

        // Edit to display money vs non-money tiers
        if (achievement.isTiered()) {
            int progressPercent = (int) (achievement.getProgress() * 100);
            holder.progressBar.setProgress(progressPercent);

            int tier = achievement.getState();
            String tierLabel = toRomanNumeral(Math.max(1, tier));
            holder.name.setText(achievement.getName() + " " + tierLabel);
            holder.name.setTextColor(getTierColor(holder.itemView.getContext(), achievement.getState()));

            int tierIndex = achievement.getState();
            int required = -1;

            if (achievement.isMoneyTier()) {
                if (moneyTiers != null && moneyTiers.length > 0) {
                    if (tierIndex < moneyTiers.length) {
                        required = moneyTiers[tierIndex];
                    } else {
                        required = moneyTiers[moneyTiers.length - 1];
                    }
                } else {
                    required = 0;
                }
            } else {
                if (nonMoneyTiers != null && nonMoneyTiers.length > 0) {
                    if (tierIndex < nonMoneyTiers.length) {
                        required = nonMoneyTiers[tierIndex];
                    } else {
                        required = nonMoneyTiers[nonMoneyTiers.length - 1];
                    }
                } else {
                    required = 0;
                }
            }

            holder.progressText.setText(achievement.getCount() + " / " + required);
        } else {
            holder.progressBar.setProgress((int) (achievement.getProgress() * 100));
            holder.progressText.setText(achievement.getState() + " / 1");
            holder.name.setTextColor(getColorForNonTiered(holder.itemView.getContext(), achievement.getName()));
        }

        if (achievement.getState() == 0) {
            holder.equipBtn.setText("Locked");
            holder.equipBtn.setEnabled(false);
            holder.equipBtn.setBackgroundColor(Color.parseColor("#D3D3D3"));
        } else if (achievement.isEquipped()) {
            holder.equipBtn.setText("Equipped");
            holder.equipBtn.setEnabled(false);
            holder.equipBtn.setBackgroundColor(Color.parseColor("#B59410"));
        } else {
            holder.equipBtn.setText("Equip");
            holder.equipBtn.setEnabled(true);
            holder.equipBtn.setBackgroundColor(Color.parseColor("#800080"));
            holder.equipBtn.setOnClickListener(v -> equipClickListener.onEquipClicked(achievement.getName()));
        }
    }

    /* Used to get the roman numeral version of each tiered achievement state */
    private String toRomanNumeral(int number) {
        String[] numerals = {"I", "II", "III", "IV", "V"};
        // May have to change this logic, testing needs to be done
        return (number >= 1 && number <= numerals.length) ? numerals[number - 1] : "I";
    }

    /* gets the colors for the given state that a tier achievement is at (public and static because they are used elsewhere) */
    public static int getTierColor(Context context, int tierState) {
        switch (tierState) {
            case 1: return Color.parseColor("#3D3D3D"); // Tier I
            case 2: return Color.parseColor("#008000"); // Tier II
            case 3: return Color.parseColor("#0000FF");  // Tier III
            case 4: return Color.parseColor("#800080"); // Tier IV
            case 5: return Color.parseColor("#FFD700"); // Tier V
            default: return Color.parseColor("#3D3D3D");
        }
    }

    /* gets the colors for the given non-tiered achievement (public because they are used elsewhere) */
    public static int getColorForNonTiered(Context context, String name) {
        switch (name) {
            case "Clubber": return Color.parseColor("#008080"); // teal-ish
            case "Say My Name!": return Color.parseColor("#5C4033"); // dark brown
            case "Mouse Bites": return Color.parseColor("#708090"); // greyish blue
            case "Alaskan Bull Worm": return Color.parseColor("#FFB6C1"); // lighter pink
            case "GOD": return Color.parseColor("#8B0000"); // dark red
            case "Oops! All In": return Color.parseColor("#00008B"); // dark blue
            case "Dedicated": return Color.parseColor("#FFA500"); // orange
            case "Grub": // default text
            default: return Color.parseColor("#3D3D3D");
        }
    }

    @Override
    public int getItemCount() {
        return achievementList.size();
    }

    public void updateTiers(int[] newMoneyTiers, int[] newNonMoneyTiers) {
        this.moneyTiers = newMoneyTiers;
        this.nonMoneyTiers = newNonMoneyTiers;
    }

    public static class AchievementViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, progressText;
        ProgressBar progressBar;
        Button equipBtn;

        public AchievementViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.achievement_name);
            description = itemView.findViewById(R.id.achievement_desc);
            progressText = itemView.findViewById(R.id.achievement_progress);
            progressBar = itemView.findViewById(R.id.achievement_progressbar);
            equipBtn = itemView.findViewById(R.id.achievement_equip);
        }
    }
}
