package com.vitoksmile.movieslist.data.source.remote

import com.vitoksmile.movieslist.domain.models.MovieId
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {

    @GET("movie/now_playing")
    suspend fun getAll(): MoviesListModel

    @GET("movie/{movieId}")
    suspend fun getDetails(@Path("movieId") id: MovieId): MovieDetailsModel

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresListModel
}
