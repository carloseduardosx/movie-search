package com.carloseduardo.movie.search.data.model;

import com.carloseduardo.movie.search.data.model.realm.RealmAutoIncrement;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Movie extends RealmObject {

    @Required
    @PrimaryKey
    private Integer _id = RealmAutoIncrement.getInstance().getNextIdFromModel(Movie.class);
}