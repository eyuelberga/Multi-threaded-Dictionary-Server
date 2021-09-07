package services;

import models.logic.Request;
import models.logic.Response;

public interface DictionaryService {
    Response search(Request req);

    Response all(Request req);

    Response  add(Request req);

    Response update(Request req);

    Response delete(Request req);
}
