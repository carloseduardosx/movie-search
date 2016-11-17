# movie-search

**Movie Search** is a simple app which use [TheMovieDB](https://developers.themoviedb.org/4/getting-started) API to show a list of movies ordered by *popularity*. Moreover, you can make a *search* by a movie of your preference and see more details of it. By clicking at the movie item you should be redirect to a *detail screen* of the movie.

## Architecture

Used Model View Presenter(MVP) to facilitate separation of responsibilities and make the code more testable

## Technologies

- [Realm](https://realm.io/docs/java/latest/) for storage
- [Retrofit2](https://square.github.io/retrofit/) for network
- [RxJava2](https://github.com/ReactiveX/RxJava/tree/2.x) and [RxAndroid2](https://github.com/ReactiveX/RxAndroid/tree/2.x) for asynchronicity
- [Gson](https://github.com/google/gson) for serialization / deserialization
- [Picasso](https://github.com/square/picasso) for image loading
- [Lombok](https://projectlombok.org/) for boilerplate generation
- [Dagger2](https://google.github.io/dagger/) for dependency injection
- [JUnit](http://junit.org/junit4/) and [Mockito](http://site.mockito.org/) for tests


## Preview

<img src="./assets/preview.gif" alt="Preview" width="300px" height="500px">

## Images

You can see some more images about the application [here](https://github.com/carloseduardosx/movie-search/blob/master/IMAGES.md)

## License

movie-search is released under the Apache License. See [LICENSE](https://github.com/carloseduardosx/movie-search/blob/master/LICENSE.md) for details.
