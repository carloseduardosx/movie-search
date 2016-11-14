package com.carloseduardo.movie.search.data.model;

import com.carloseduardo.movie.search.data.model.realm.RealmAutoIncrement;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MoviesContent extends RealmObject {

    public static final String ID = "id";

    @Required
    @PrimaryKey
    @SerializedName("page")
    private Integer id = RealmAutoIncrement.getInstance().getNextIdFromModel(MoviesContent.class);

    @SerializedName("results")
    private RealmList<Movie> movies;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("total_pages")
    private Integer totalPages;
}