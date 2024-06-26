package com.rohitsharma.domain.usecase

import androidx.paging.PagingData
import com.rohitsharma.domain.entities.MovieEntity
import com.rohitsharma.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow


class GetFavoriteMovies(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(pageSize: Int): Flow<PagingData<MovieEntity>> = movieRepository.favoriteMovies(pageSize)
}