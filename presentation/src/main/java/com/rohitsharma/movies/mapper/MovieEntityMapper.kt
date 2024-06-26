package com.rohitsharma.movies.mapper

import com.rohitsharma.movies.entities.MovieListItem
import com.rohitsharma.domain.entities.MovieEntity



fun MovieEntity.toPresentation() = MovieListItem.Movie(
    id = id,
    imageUrl = image,
    category = category
)