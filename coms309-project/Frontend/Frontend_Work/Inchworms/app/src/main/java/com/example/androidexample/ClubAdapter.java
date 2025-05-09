package com.example.androidexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ClubViewHolder> {
    private List<ClubInfo> clubs;
    private onClubClickListener listener;
    private String username;

    public interface onClubClickListener {
        void onClubClick(String club_name, int club_id, String username);
    }

    public ClubAdapter(List<ClubInfo> clubs, String username, onClubClickListener listener) {
        this.clubs = clubs;
        this.listener = listener;
        this.username = username;
    }

    @NonNull
    @Override
    public ClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_club, parent, false);
        return new ClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubViewHolder holder, int position) {
        ClubInfo club = clubs.get(position);
        holder.clubName.setText(club.getClubName());
        holder.joinBtn.setOnClickListener(v -> listener.onClubClick(club.getClubName(), club.getClubId(), username));
    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }

    public static class ClubViewHolder extends RecyclerView.ViewHolder {
        TextView clubName;
        Button joinBtn;

        public ClubViewHolder(@NonNull View itemView) {
            super(itemView);
            clubName = itemView.findViewById(R.id.name_club);
            joinBtn = itemView.findViewById(R.id.join_btn);
        }
    }
}
