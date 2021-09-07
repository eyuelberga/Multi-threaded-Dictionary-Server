package messaging;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.parse.ParsingException;
import models.logic.Request;
import models.logic.Response;

import java.io.StringWriter;
public class JsonMessageExchange implements MessageExchange {
    private ObjectMapper objectMapper;
    public JsonMessageExchange(){
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,true);
    }
    @Override
    public Request parseRequest(String req) throws ParsingException {
        try{
       return objectMapper.readValue(req,Request.class);
        }
        catch (Exception e){
            throw new ParsingException("Parsing Error");
        }
    }

    @Override
    public Response parseResponse(String res) throws ParsingException {
        try {
            return objectMapper.readValue(res,Response.class);
        }
         catch (Exception e){
            throw new ParsingException("Parsing Error");
        }
    }

    @Override
    public String sendRequest(Request req) throws ParsingException {
        try{
        StringWriter str = new StringWriter();
        objectMapper.writeValue(str,req);
        return str.toString();
        }
        catch (Exception e){
            throw new ParsingException("Parsing Error");
        }
    }

    @Override
    public String sendResponse(Response res) throws ParsingException {
        try{
        StringWriter str = new StringWriter();
        objectMapper.writeValue(str,res);
        return str.toString();
        }
        catch (Exception e){
            throw new ParsingException("Parsing Error");
        }
    }
}
