package utils;

import config.Config;
import config.LogCodes;
import models.logic.Log;

import java.util.*;

public final class Logger {
    public List<Log> getLogList() {
        return logList;
    }
    public void log(Log log){
        logList.add(log);
        System.out.println(log.toString());
        if (log.getAction().equals(LogCodes.SERVER_RES)){
            totalRequests ++;
        }
        else if (log.getAction().equals(LogCodes.SERVER_STOPPED)){
            this.totalRequests = 0;
        }
    }
    private List<Log> logList;
    private Integer totalRequests;
    private Integer threadPoolSize;
    private Logger(){
        this.logList = new ArrayList<Log>();
        this.totalRequests = 0;
        threadPoolSize = 0;
    }
    private static final Logger INSTANCE = new Logger();
    public static Logger getInstance(){
        return INSTANCE;
    }
    public String getConnectionLimit(){
        return threadPoolSize.toString();
    }
    public Integer getTotalRequests(){
        return  totalRequests;
    }

    public  void setThreadPoolSize(Integer threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }
}
