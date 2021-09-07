package utils;

import models.logic.Request;
import models.logic.Response;

import java.net.Socket;

public class ServerUtil {
    public static String getClientInfo(Socket clientSocket){
        return clientSocket.getLocalAddress().getCanonicalHostName()+":"+clientSocket.getPort();
    }
    public static String stringifyResponse(Response res){
        return "code:"+res.code.toString()+"\n"+"message:"+res.message+"\n";
    }
    public static String stringifyRequest(Request req){
        return "method:"+req.method.toString()+"\n"+"payload:"+req.payload.toString()+"\n";
    }
}
