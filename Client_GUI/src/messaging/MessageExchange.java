package messaging;

import exceptions.parse.ParsingException;
import models.logic.Request;
import models.logic.Response;

public interface MessageExchange {
    Request parseRequest(String req) throws ParsingException;

    Response parseResponse(String res) throws ParsingException;

    String sendRequest(Request req) throws ParsingException;

    String sendResponse(Response res) throws ParsingException;
}
