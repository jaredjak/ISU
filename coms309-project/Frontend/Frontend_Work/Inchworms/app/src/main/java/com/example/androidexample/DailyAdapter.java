package com.example.androidexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyViewHolder> {
    private List<Daily> dailyList;

    public DailyAdapter(List<Daily> dailyList) {
        this.dailyList = dailyList;
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily, parent, false);
        return new DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyViewHolder holder, int position) {
        Daily daily = dailyList.get(position);
        holder.name.setText(daily.getName());
        holder.progressText.setText(daily.getCurrentProgress() + " / " + daily.getTargetProgess());

        int percent = (int) (daily.getProgress() * 100);
        holder.progressBar.setProgress(percent);
    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }

    public static class DailyViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, progressText;
        ProgressBar progressBar;

        public DailyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.daily_name);
//            description = itemView.findViewById(R.id.daily_desc);
            progressText = itemView.findViewById(R.id.daily_progress);
            progressBar = itemView.findViewById(R.id.daily_progressbar);
        }
    }
}
