package com.example.androidexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminReportAdapter extends RecyclerView.Adapter<AdminReportAdapter.ReportViewHolder> {

    private List<AdminReport> reportList;
    private final OnReportClickListener listener;

    public interface OnReportClickListener {
        void onDetailsClick(AdminReport report);
        void onDismissClick(AdminReport report);
    }

    public AdminReportAdapter(List<AdminReport> reports, OnReportClickListener listener) {
        this.reportList = reports;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_report_item, parent, false);
        return new ReportViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        AdminReport report = reportList.get(position);

        holder.reportedText.setText("Reported: " + report.getReportedUser());
        holder.reporterText.setText("By: " + report.getReportingUser());

        holder.reportDetails.setOnClickListener(v -> listener.onDetailsClick(report));
        holder.reportDismiss.setOnClickListener(v -> listener.onDismissClick(report));
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView reporterText, reportedText;
        Button reportDetails, reportDismiss;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            reporterText = itemView.findViewById(R.id.admin_reporter_text);
            reportedText = itemView.findViewById(R.id.admin_reported_text);
            reportDetails = itemView.findViewById(R.id.admin_report_details);
            reportDismiss = itemView.findViewById(R.id.admin_report_dismiss);
        }
    }

    // Used when dismissing a report becomes a thing
    public void removeReport(AdminReport report) {
        int index = reportList.indexOf(report);
        if (index >= 0) {
            reportList.remove(index);
            notifyItemRemoved(index);
        }
    }
}
