package com.rohitsharma.data.repository.movie

import com.rohitsharma.data.api.MovieApi
import com.rohitsharma.data.entities.toDomain
import com.rohitsharma.domain.entities.MovieEntity
import com.rohitsharma.domain.util.Result

class MovieRemoteDataSource(
    private val movieApi: MovieApi
) : MovieDataSource.Remote {

    override suspend fun getMovies(page: Int, limit: Int): Result<List<MovieEntity>> = try {
        val result = movieApi.getMovies(page, limit)
        Result.Success(result.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getMovies(movieIds: List<Int>): Result<List<MovieEntity>> = try {
        val result = movieApi.getMovies(movieIds)
        Result.Success(result.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getMovie(movieId: Int): Result<MovieEntity> = try {
        val result = movieApi.getMovie(movieId)
        Result.Success(result.toDomain())
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun search(query: String, page: Int, limit: Int): Result<List<MovieEntity>> = try {
        val result = movieApi.search(query, page, limit)
        Result.Success(result.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }
}
