package com.rohitsharma.domain.usecase

import com.rohitsharma.domain.entities.MovieEntity
import com.rohitsharma.domain.repository.MovieRepository
import com.rohitsharma.domain.util.Result


class GetMovieDetails(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Result<MovieEntity> = movieRepository.getMovie(movieId)
}
