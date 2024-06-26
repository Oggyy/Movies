package com.rohitsharma.domain.usecase

import com.rohitsharma.domain.repository.MovieRepository


class AddMovieToFavorite(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) = movieRepository.addMovieToFavorite(movieId)
}