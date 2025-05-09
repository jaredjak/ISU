package com.example.androidexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminUserChatAdapter extends RecyclerView.Adapter<AdminUserChatAdapter.ChatViewHolder> {
    private List<ChatMessage> messages;
//    private List<String> filteredMessages;

    public AdminUserChatAdapter(List<ChatMessage> messages) {
        this.messages = messages;
//        this.filteredMessages = new ArrayList<>(messages);
    }

//    public void filterByKeyword(String keyword) {
//        filteredMessages = messages.stream().filter(chat -> chat.toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
//        notifyDataSetChanged();
//    }

    public void updateMessages(List<ChatMessage> newMessages) {
        this.messages = newMessages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        holder.messageText.setText(message.getMessage());
        holder.senderText.setText("From: " + message.getSenderUsername());
        holder.timestampText.setText("Sent:" + message.getTimestamp());
    }

    public int getItemCount() {
        return messages.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, senderText, timestampText;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.admin_chat_text);
            senderText = itemView.findViewById(R.id.admin_username_text);
            timestampText = itemView.findViewById(R.id.admin_time_text);
        }
    }
}
