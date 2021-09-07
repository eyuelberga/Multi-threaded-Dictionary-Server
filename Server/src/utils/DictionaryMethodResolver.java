package utils;

import config.Codes;
import models.logic.Request;
import models.logic.Response;
import services.DictionaryService;
import services.DictionaryServiceImpl;

public class DictionaryMethodResolver implements MethodResolver {
    private DictionaryService dictionaryService;
    public DictionaryMethodResolver(){
        this.dictionaryService = new DictionaryServiceImpl();
    }
    @Override
    public Response executeMethod(Request req) {
        switch (req.method) {
            case ADD :
                return this.dictionaryService.add(req);

            case UPDATE :
                return this.dictionaryService.update(req);

            case REMOVE :
                return this.dictionaryService.delete(req);

            case SEARCH :
                return this.dictionaryService.search(req);
            case ALL :
                return this.dictionaryService.all(req);
            default:
                return new Response(Codes.INVALID_METHOD,"invalid method",null);
        }
    }
}
