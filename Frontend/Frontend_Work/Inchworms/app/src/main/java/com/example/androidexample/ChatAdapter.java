package com.example.androidexample;

import android.content.Context;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

//public class ClubChatAdapter extends RecyclerView.Adapter<ClubChatAdapter.ViewHolder> {
//    private List<String> messages;
//
//    public ClubChatAdapter(List<String> messages) {
//        this.messages = messages;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.chatText.setText(messages.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return messages.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView chatText;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            chatText = itemView.findViewById(R.id.chat_text);
//        }
//    }
//}

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<ChatMessage> messages;
    private Context context;
    private String currentUsername;

    public ChatAdapter(Context context, List<ChatMessage> messages, String currentUsername) {
        this.context = context;
        this.messages = messages;
        this.currentUsername = currentUsername;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ChatMessage chatMessage = messages.get(position);

        //previous split function
//        holder.chatText.setText(chatMessage.getSenderUsername() + ": " + chatMessage.getMessage());


        //new split functionality to add in colored achievements
        String fullSender = chatMessage.getSenderUsername();
        String msgText = chatMessage.getMessage();

        String user = fullSender;
        String achievement = null;
        int tierSelected = 0;

        /* Parse for achievement */
        if (fullSender.contains("[") && fullSender.contains("]")) {
            int start = fullSender.indexOf('[');
            int end = fullSender.indexOf(']');
            if (start < end) {
                user = fullSender.substring(0, start).trim();
                String inner = fullSender.substring(start+1, end).trim();

                int lastSpace = inner.lastIndexOf(' ');
                if (lastSpace != -1) {

                    String maybeTier = inner.substring(lastSpace + 1).trim();
                    try {
                        tierSelected = Integer.parseInt(maybeTier);
                        achievement = inner.substring(0, lastSpace).trim();
                    } catch (NumberFormatException e) {
                        achievement = inner;
                        tierSelected = 0;
                    }
                } else {
                    achievement = inner;
                    tierSelected = 0;
                }
            }
        }

        /* Using a SpannableStringBuilder to add the proper color to the achievement */
        SpannableStringBuilder spannable = new SpannableStringBuilder();
        spannable.append(user);

        if (achievement != null) {
            // Gets the color for whatever achievement is selected
            Log.d("achievement name check", achievement);
            Log.d("tier check", String.valueOf(tierSelected));
            int color = (tierSelected == 0)
                    ? AchievementAdapter.getColorForNonTiered(context, achievement)
                    : AchievementAdapter.getTierColor(context, tierSelected);

            int start = spannable.length();
            spannable.append("[" + achievement + (tierSelected > 0 ? " " + tierSelected : "") + "]");
            // I used an outside source for this. Sets the color of the achievement
            spannable.setSpan(new ForegroundColorSpan(color), start, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        spannable.append(": ");

        spannable.append(msgText);
        holder.chatText.setText(spannable);


        holder.chatText.setOnClickListener(v -> {
//            Check for system/AI chat bot clicks
            if (!chatMessage.getSenderUsername().contains("[") || !chatMessage.getSenderUsername().contains("]")) {
                return;
            }

            int index = chatMessage.getSenderUsername().indexOf('[');
            String sender = chatMessage.getSenderUsername().substring(0, index);
            if (!sender.equals(currentUsername)) {
                showReportDialog(chatMessage, sender);
            } else {
                Toast.makeText(context, "You can't report your own message.", Toast.LENGTH_SHORT).show();
            }
        });


//        String messageText = chatMessage.getMessage();
//        if (messageText.trim().isEmpty()) {
//            holder.chatText.setClickable(false);
//            holder.chatText.setFocusable(false);
//            holder.chatText.setOnClickListener(null);
//        } else {
//            holder.chatText.setOnClickListener(v -> {
//                if (!chatMessage.getSenderUsername().equals(currentUsername)) {
//                    showReportDialog(chatMessage);
//                } else {
//                    Toast.makeText(context, "You can't report your own message.", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView chatText;

        public ViewHolder(View itemView) {
            super(itemView);
            chatText = itemView.findViewById(R.id.chat_text);
        }
    }

    private void showReportDialog(ChatMessage message, String sender) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Report Message");
//
//        final EditText input = new EditText(context);
//        input.setHint("Enter explanation (optional) (One word. Anymore will not be recorded)");
//        builder.setView(input);
//
//        builder.setPositiveButton("Report", (dialog, which) -> {
//            String explanation = input.getText().toString().trim();
//            submitReport(message, sender, explanation);
//        });
//
//        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
//
//        builder.show();

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_chat_report, null);

        EditText explanationInput = dialogView.findViewById(R.id.explanation_input);
        Button report = dialogView.findViewById(R.id.chat_report_btn);
        Button exitButton = dialogView.findViewById(R.id.chat_report_exit_btn);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        report.setOnClickListener(v -> {
            String explanation = explanationInput.getText().toString().trim();
            submitReport(message, sender, explanation);
            dialog.dismiss();
        });

        exitButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void submitReport(ChatMessage message, String sender, String explanation) {
        if (message.getId() == null) {
            Toast.makeText(context, "This message cannot be reported.", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("Checking", message.getId().toString());
        Log.d("Checking", currentUsername);
//        Log.d("Checking", message.getSenderUsername());
        Log.d("Checking", sender);
        Log.d("Checking", explanation);

        String url = "http://coms-3090-013.class.las.iastate.edu:8080/reports?reportingUsername="
                + currentUsername +
                "&reportedUsername=" + sender +
                "&explanation=" + Uri.encode(explanation);

        //"&messageId=" + message.getId() + // goes before explanation

        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            Toast.makeText(context, "Report submitted.", Toast.LENGTH_SHORT).show();
        },
                error -> {
            Log.d("Submit Report Error", "Error: " + error.toString());
            Toast.makeText(context, "Error submitting report.", Toast.LENGTH_SHORT).show();
                });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}
