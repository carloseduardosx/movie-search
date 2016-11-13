package com.carloseduardo.movie.search.data.model;

import com.carloseduardo.movie.search.data.model.constants.KnownModel;
import com.carloseduardo.movie.search.data.model.exception.UnknownModelException;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AutoIncrement extends RealmObject {

    @Required
    @PrimaryKey
    private Integer id = 1;

    private Integer movie;

    private Integer moviesContent;

    public void incrementByClassName(String className) {

        switch (className) {

            case KnownModel.MOVIE:
                movie = movie == null ? 1 : ++movie;
                break;

            case KnownModel.MOVIES_CONTENT:
                moviesContent = moviesContent == null ? 1 : ++moviesContent;
                break;

            default:
                throw new UnknownModelException("Class name: " + className);
        }
    }

    public Integer findByClassName(String className) {

        switch (className) {

            case KnownModel.MOVIE:
                return this.movie;

            case KnownModel.MOVIES_CONTENT:
                return this.moviesContent;

            default:
                throw new UnknownModelException("Class name: " + className);
        }
    }
}
