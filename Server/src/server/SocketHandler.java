package server;

import config.Codes;
import config.LogCodes;
import exceptions.parse.ParsingException;
import models.logic.Log;
import utils.DictionaryMethodResolver;
import utils.Logger;
import utils.MethodResolver;
import messaging.JsonMessageExchange;
import messaging.MessageExchange;
import models.logic.Response;
import utils.ServerUtil;

import java.io.*;
import java.net.Socket;

class SocketHandler implements Runnable {
    private final Socket clientSocket;
    private MethodResolver methodResolver;
    private MessageExchange messageExchange;
    private Boolean isClosed;

    public SocketHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.methodResolver = new DictionaryMethodResolver();
        this.messageExchange = new JsonMessageExchange();
        this.isClosed = false;
    }

    @Override
    public void run() {
        try {
            PrintStream ps = new PrintStream(clientSocket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
            while (!isClosed) {
                try {
                    String request, response;
                    request = br.readLine();
                    Response res = methodResolver.executeMethod(messageExchange.parseRequest(request));
                    // log request
                    Logger.getInstance().log(new Log(LogCodes.CLIENT_REQ, ServerUtil.getClientInfo(clientSocket), ServerUtil.stringifyRequest(messageExchange.parseRequest(request))));
                    response = messageExchange.sendResponse(res);
                    //log response
                    Logger.getInstance().log(new Log(LogCodes.SERVER_RES, ServerUtil.getClientInfo(clientSocket),ServerUtil.stringifyResponse(res)));
                    // send to client
                    ps.println(response);
                    break;
                } catch (ParsingException e) {
                    Response errorResponse = new Response(Codes.PARSING_ERROR, "could not parse request", null);
                    // log error response
                    Logger.getInstance().log(new Log(LogCodes.SERVER_RES, ServerUtil.getClientInfo(clientSocket),ServerUtil.stringifyResponse(errorResponse)));
                    // send to client
                    ps.println(messageExchange.sendResponse(errorResponse));
                    break;
                }
            }
            ps.close();
            br.close();
            kb.close();
            clientSocket.close();
            Logger.getInstance().log(new Log(LogCodes.CONN_CLOSED, ServerUtil.getClientInfo(clientSocket),""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}