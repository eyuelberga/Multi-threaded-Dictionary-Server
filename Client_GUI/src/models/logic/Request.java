package models.logic;

import config.Methods;
import models.db.DictWord;

public class Request {
    public Methods method;
    public DictWord payload;

    public Request() {
    }

    public Request(Methods method,DictWord payload) {
        this.method = method;
        this.payload = payload;

    }
}
