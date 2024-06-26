package com.rohitsharma.domain.usecase

import com.rohitsharma.domain.repository.MovieRepository
import com.rohitsharma.domain.util.Result


class CheckFavoriteStatus(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Result<Boolean> = movieRepository.checkFavoriteStatus(movieId)
}