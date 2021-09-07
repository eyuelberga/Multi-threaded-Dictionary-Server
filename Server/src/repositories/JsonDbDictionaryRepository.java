package repositories;

import config.Config;
import exceptions.file.FileNotFoundException;
import exceptions.file.FileReadException;
import exceptions.file.FileWriteException;
import io.jsondb.InvalidJsonDbApiUsageException;
import io.jsondb.JsonDBTemplate;
import models.db.DictWord;
import java.util.List;

public class JsonDbDictionaryRepository implements DictionaryRepository {
    private JsonDBTemplate jsonDBTemplate;
    private static final JsonDbDictionaryRepository INSTANCE = new JsonDbDictionaryRepository();
    public static JsonDbDictionaryRepository getInstance(){
        return INSTANCE;
    }
   private JsonDbDictionaryRepository(){
        String dbFilesLocation= Config.DB_PATH;
        String baseScanPackage="models.db";
        this.jsonDBTemplate = new JsonDBTemplate(dbFilesLocation,baseScanPackage);

        try
        {
            jsonDBTemplate.createCollection(DictWord.class);
        }
        catch (InvalidJsonDbApiUsageException ignored){
        }
        catch (Exception e){
            throw new FileNotFoundException("database initialization failed - "+e.getMessage());
        }
    }
    @Override
    public DictWord search(String word) throws FileReadException {
        try {
            return jsonDBTemplate.findById(word,DictWord.class);
        }
        catch (Exception e){
            throw new FileReadException("Error searching database");
        }

    }

    @Override
    public List<DictWord> all()throws FileReadException {
        try{
            return jsonDBTemplate.findAll(DictWord.class);
        }
        catch (Exception e){
            throw new FileReadException("Error searching database");
        }

    }

    @Override
    public void add(DictWord dictword) throws FileWriteException {
        try {
            this.jsonDBTemplate.insert(dictword);
        }
        catch (Exception e){
                throw new FileWriteException("Error adding to database, word already exists");
        }
    }

    @Override
    public void update(DictWord dictword) throws FileWriteException  {
        try{
            this.jsonDBTemplate.upsert(dictword);
        }
        catch (Exception e){
            throw new FileWriteException("Error updating to database");
        }
    }

    @Override
    public void delete(DictWord dictword) throws FileWriteException {
        try {
            this.jsonDBTemplate.remove(dictword,DictWord.class);
        }
        catch (Exception e){
            throw new FileWriteException("Error deleting from database");
        }

    }
}
