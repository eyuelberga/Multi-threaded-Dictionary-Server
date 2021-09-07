package utils;

import config.ClientConfig;
import messaging.JsonMessageExchange;
import messaging.MessageExchange;
import models.logic.Request;
import models.logic.Response;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

public class SocketHandler implements Callable<Response> {
    private Request request;
    private MessageExchange messageExchange;
    public SocketHandler(Request request){
        this.request = request;
    }
    @Override
    public Response call() throws Exception {
        messageExchange = new JsonMessageExchange();
        InetAddress ip = InetAddress.getByName(ClientConfig.getInstance().getHostName());
        Socket socket = new Socket(ip, ClientConfig.getInstance().getPort());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String requestStr, responseStr;
        requestStr = messageExchange.sendRequest(request);
        dos.writeBytes(requestStr + "\n");
         //receive from the server
        responseStr = br.readLine();
        // close connection.
        dos.close();
        br.close();
        socket.close();
        return messageExchange.parseResponse(responseStr);
    }
}
