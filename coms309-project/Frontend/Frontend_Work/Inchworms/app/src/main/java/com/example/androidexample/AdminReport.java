package com.example.androidexample;

public class AdminReport {
    private String reportedUser;
    private String reportingUser;
    private String reportedMessage;
    private String explanation;
    private String timeStamp;


    public AdminReport(String reportedUser, String reportingUser, String reportedMessage, String explanation, String timeStamp) {
        this.reportedUser = reportedUser;
        this.reportingUser = reportingUser;
        this.reportedMessage = reportedMessage;
        this.explanation = explanation;
        this.timeStamp = timeStamp;
    }

    public String getReportingUser() { return reportingUser; }
    public String getReportedUser() { return reportedUser; }
    public String getMessage() { return reportedMessage; }
    public String getExplanation() { return explanation; }
    public String getTimestamp() { return timeStamp; }
}
