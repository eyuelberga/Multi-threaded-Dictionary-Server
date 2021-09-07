package services;

import config.Codes;
import models.db.DictWord;
import models.logic.Request;
import models.logic.Response;
import repositories.DictionaryRepository;
import repositories.JsonDbDictionaryRepository;

import java.lang.reflect.Array;
import java.util.List;

public class DictionaryServiceImpl implements DictionaryService {
    private DictionaryRepository dictionaryRepository;
    public DictionaryServiceImpl(){
        this.dictionaryRepository = JsonDbDictionaryRepository.getInstance();
    }
    public Response search(Request req){
        try {
            DictWord dictWord = this.dictionaryRepository.search(req.payload.toString());
            return new Response(Codes.OPERATION_SUCCESSFUL,"search method executed", new DictWord[]{dictWord});
        }
        catch (Exception e){
            return new Response(Codes.OPERATION_FAIL,e.getMessage(),null);
        }

    }

    public Response all(Request req){
        try {
            List<DictWord> repoResponse = this.dictionaryRepository.all();
            return new Response(Codes.OPERATION_SUCCESSFUL,"all method executed", (DictWord[]) repoResponse.toArray());
        }
        catch (Exception e){
            return new Response(Codes.OPERATION_FAIL,e.getMessage(),null);
        }

    }

    public synchronized Response add(Request req) {
        try {
            DictWord payload = req.payload;
            this.dictionaryRepository.add(req.payload);
            return new Response(Codes.OPERATION_SUCCESSFUL,"word added to dictionary",null);
        }
        catch (Exception e){
            return new Response(Codes.OPERATION_FAIL,e.getMessage(),null);
        }

    }
    public synchronized Response update(Request req){
        try {
            this.dictionaryRepository.update((DictWord)req.payload);
            return new Response(Codes.OPERATION_SUCCESSFUL,"word successfully updated",null);
        }
        catch (Exception e){
            return new Response(Codes.OPERATION_FAIL,e.getMessage(),null);
        }

    }
    public synchronized Response delete(Request req){
        try {
            this.dictionaryRepository.delete((DictWord)req.payload);
            return new Response(Codes.OPERATION_SUCCESSFUL,"word deleted from dictionary",null);
        }
        catch (Exception e){
            return new Response(Codes.OPERATION_FAIL,e.getMessage(),null);
        }
    }

}
