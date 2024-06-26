package com.rohitsharma.data.mapper

import com.rohitsharma.data.entities.MovieDbData
import com.rohitsharma.domain.entities.MovieEntity



fun MovieEntity.toDbData() = MovieDbData(
    id = id,
    image = image,
    description = description,
    title = title,
    category = category
)
