package server;

import config.LogCodes;
import exceptions.server.AcceptConnectionException;
import exceptions.server.CloseServerException;
import exceptions.server.PortTakenException;
import models.logic.Log;
import utils.Logger;
import utils.ServerUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private Integer serverPort;
    private ServerSocket serverSocket;
    private ExecutorService threadPool;
    private  Thread runningThread;
    private Boolean isStopped;
    private Integer threadPoolSize;


    public Server(Integer serverPort, Integer threadPoolSize) throws PortTakenException{
            this.serverPort = serverPort;
            this.threadPoolSize = threadPoolSize;
            this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
            this.runningThread = null;
            this.isStopped = false;
            this.serverSocket = null;
        openServerSocket();
    }
    @Override
    public void run() {
        while (!isStopped()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
                // print out log
                Logger.getInstance().log(new Log(LogCodes.CONN_ESTABLISHED, ServerUtil.getClientInfo(clientSocket),clientSocket.toString()));
            }
            catch (IOException e){
                if(isStopped()) {
                    break;
                }
                throw new AcceptConnectionException("Error accepting client connection");
            }
            this.threadPool.execute(new SocketHandler(clientSocket));
        }
        this.threadPool.shutdown();
        Logger.getInstance().log(new Log(LogCodes.SERVER_STOPPED,"","port:"+serverPort.toString()));
    }
private void openServerSocket() {
    try {
        this.serverSocket = new ServerSocket(this.serverPort);
        Logger.getInstance().log(new Log(LogCodes.SERVER_STARTED,"","port:"+serverPort.toString()));
        Logger.getInstance().setThreadPoolSize(threadPoolSize);
    } catch (IOException e) {
        throw new PortTakenException("Cannot open port "+this.serverPort.toString());
    }
}
    private synchronized boolean isStopped() {
        return this.isStopped;
    }
    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
            this.threadPool.shutdown();
        } catch (IOException e) {
            throw new CloseServerException(e.getMessage());
        }
    }
    public String getMachine() throws UnknownHostException {
        return InetAddress.getLocalHost().getCanonicalHostName();
    }
    public String getIP() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
    public String getPort(){
        return this.serverPort.toString();
    }
}
