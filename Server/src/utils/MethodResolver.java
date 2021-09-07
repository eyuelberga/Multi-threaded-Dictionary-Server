package utils;

import models.logic.Request;
import models.logic.Response;

public interface MethodResolver {
    Response executeMethod(Request req);
}
