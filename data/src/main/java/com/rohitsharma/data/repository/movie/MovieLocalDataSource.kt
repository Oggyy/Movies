package com.rohitsharma.data.repository.movie

import androidx.paging.PagingSource
import com.rohitsharma.data.db.movies.MovieDao
import com.rohitsharma.data.db.movies.MovieRemoteKeyDao
import com.rohitsharma.data.entities.MovieDbData
import com.rohitsharma.data.entities.MovieRemoteKeyDbData
import com.rohitsharma.data.entities.toDomain
import com.rohitsharma.data.exception.DataNotAvailableException
import com.rohitsharma.data.mapper.toDbData
import com.rohitsharma.data.util.DiskExecutor
import com.rohitsharma.domain.entities.MovieEntity
import com.rohitsharma.domain.util.Result
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext


class MovieLocalDataSource(
    private val executor: DiskExecutor,
    private val movieDao: MovieDao,
    private val remoteKeyDao: MovieRemoteKeyDao,
) : MovieDataSource.Local {

    override fun movies(): PagingSource<Int, MovieDbData> = movieDao.movies()

    override suspend fun getMovies(): Result<List<MovieEntity>> = withContext(executor.asCoroutineDispatcher()) {
        val movies = movieDao.getMovies()
        return@withContext if (movies.isNotEmpty()) {
            Result.Success(movies.map { it.toDomain() })
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override suspend fun getMovie(movieId: Int): Result<MovieEntity> = withContext(executor.asCoroutineDispatcher()) {
        return@withContext movieDao.getMovie(movieId)?.let {
            Result.Success(it.toDomain())
        } ?: Result.Error(DataNotAvailableException())
    }

    override suspend fun saveMovies(movieEntities: List<MovieEntity>) = withContext(executor.asCoroutineDispatcher()) {
        movieDao.saveMovies(movieEntities.map { it.toDbData() })
    }

    override suspend fun getLastRemoteKey(): MovieRemoteKeyDbData? = withContext(executor.asCoroutineDispatcher()) {
        remoteKeyDao.getLastRemoteKey()
    }

    override suspend fun saveRemoteKey(key: MovieRemoteKeyDbData) = withContext(executor.asCoroutineDispatcher()) {
        remoteKeyDao.saveRemoteKey(key)
    }

    override suspend fun clearMovies() = withContext(executor.asCoroutineDispatcher()) {
        movieDao.clearMoviesExceptFavorites()
    }

    override suspend fun clearRemoteKeys() = withContext(executor.asCoroutineDispatcher()) {
        remoteKeyDao.clearRemoteKeys()
    }
}