package com.carloseduardo.movie.search.data.source.local;

import com.carloseduardo.movie.search.data.model.MoviesContent;
import com.carloseduardo.movie.search.helper.RealmHelper;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class MoviesLocalDataSource {

    private RealmHelper realmHelper = new RealmHelper();

    public Observable<MoviesContent> listMovies() {

        return Observable.create(new ObservableOnSubscribe<MoviesContent>() {

            @Override
            public void subscribe(ObservableEmitter<MoviesContent> subscriber) throws Exception {

                Realm realm = realmHelper.getInstance();

                try {

                    MoviesContent moviesContent = realm.where(MoviesContent.class)
                            .findFirst();

                    subscriber.onNext(moviesContent == null ? new MoviesContent() : realm.copyFromRealm(moviesContent));
                } finally {

                    realm.close();
                }
            }
        });
    }

    public void save(final MoviesContent moviesContent) {

        Realm realm = realmHelper.getInstance();

        try {

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    realm.copyToRealmOrUpdate(moviesContent);
                }
            });
        } finally {

            realm.close();
        }
    }
}