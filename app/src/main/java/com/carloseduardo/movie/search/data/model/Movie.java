package com.carloseduardo.movie.search.data.model;

import com.carloseduardo.movie.search.data.model.realm.RealmAutoIncrement;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

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
    private Integer id = RealmAutoIncrement.getInstance().getNextIdFromModel(Movie.class);

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    private Boolean adult;

    private String overview;

    @SerializedName("release_date")
    private Date releaseDate;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("original_language")
    private String originalLanguage;

    private String title;

    private Double popularity;

    @SerializedName("vote_count")
    private Integer voteCount;

    private Boolean video;

    @SerializedName("vote_average")
    private Double voteAverage;
}