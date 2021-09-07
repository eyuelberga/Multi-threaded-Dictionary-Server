package repositories;

import exceptions.file.FileReadException;
import exceptions.file.FileWriteException;
import models.db.DictWord;

import java.util.List;

public interface DictionaryRepository {
    DictWord search(String word) throws FileReadException;
    List<DictWord> all() throws FileReadException;
    void add(DictWord dictword) throws FileWriteException;
    void update(DictWord dictword) throws FileWriteException;
    void delete(DictWord dictword) throws FileWriteException;
}
