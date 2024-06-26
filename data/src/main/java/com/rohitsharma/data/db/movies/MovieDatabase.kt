package com.rohitsharma.data.db.movies

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rohitsharma.data.db.favoritemovies.FavoriteMovieDao
import com.rohitsharma.data.entities.FavoriteMovieDbData
import com.rohitsharma.data.entities.MovieDbData
import com.rohitsharma.data.entities.MovieRemoteKeyDbData


@Database(
    entities = [MovieDbData::class, FavoriteMovieDbData::class, MovieRemoteKeyDbData::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieRemoteKeysDao(): MovieRemoteKeyDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}