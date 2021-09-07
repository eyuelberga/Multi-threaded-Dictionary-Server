package models.logic;


import config.Codes;
import models.db.DictWord;

public class Response {
    public Codes code;
    public String message;
    public DictWord[] data;

    public Response() {
    }

    public Response(Codes code, String message, DictWord[] data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
