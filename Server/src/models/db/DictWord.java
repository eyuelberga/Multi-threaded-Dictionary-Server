package models.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

import java.io.IOException;

@Document(collection = "dictionary", schemaVersion = "1.0")
public class DictWord {
    @Id
    private String word;
    private String meaning;

    public DictWord() {
    }
    public DictWord(String word) {
        this.word = word;
    }
    public DictWord(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;

    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return this.word;
    }
}
