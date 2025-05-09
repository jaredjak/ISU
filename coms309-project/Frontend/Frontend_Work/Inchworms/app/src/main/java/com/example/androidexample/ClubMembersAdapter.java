package com.example.androidexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClubMembersAdapter extends RecyclerView.Adapter<ClubMembersAdapter.MemberViewHolder> {

    private List<String> clubMembers;

    public ClubMembersAdapter(List<String> clubMembers) {
        this.clubMembers = clubMembers;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        return new MemberViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        String username = clubMembers.get(position);
        holder.usernameText.setText(username);
    }

    @Override
    public int getItemCount() {
        return clubMembers.size();
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        TextView usernameText;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.member_username);
        }
    }
}
