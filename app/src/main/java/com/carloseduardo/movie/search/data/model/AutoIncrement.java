package com.carloseduardo.movie.search.data.model;

import com.carloseduardo.movie.search.data.model.constants.KnownModel;
import com.carloseduardo.movie.search.data.model.exception.UnknownModelException;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import lombok.Getter;
import lombok.Setter;

public class AutoIncrement extends RealmObject {

    @Required
    @PrimaryKey
    private Integer _id = 1;

    @Getter
    @Setter
    private Integer movie;

    public Integer getId() {
        return _id;
    }

    public void incrementByClassName(String className) {

        switch (className) {

            case KnownModel.MOVIE:
                movie = movie == null ? 1 : ++movie;
                break;

            default:
                throw new UnknownModelException("Class name: " + className);
        }
    }

    public Integer findByClassName(String className) {

        switch (className) {

            case KnownModel.MOVIE:
                return this.movie;

            default:
                throw new UnknownModelException("Class name: " + className);
        }
    }
}
