package models.logic;

import config.LogCodes;

import java.util.Date;

public class Log {
    private LogCodes action;
    private String client;
    private String description;

    public LogCodes getAction() {
        return action;
    }

    public void setAction(LogCodes action) {
        this.action = action;
    }

    private String time;

    public Log(LogCodes action, String client, String description) {
        this.action = action;
        this.client = client;
        this.description = description;
        this.time = new Date().toInstant().toString();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "[ Logger - "+time+" ]\naction: "+action+"\n"+"client: "+client+"\n"+"description: "+description+"\n";
    }
}
