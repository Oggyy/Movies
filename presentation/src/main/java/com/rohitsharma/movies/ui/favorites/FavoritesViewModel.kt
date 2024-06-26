package com.rohitsharma.movies.ui.favorites

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.rohitsharma.movies.entities.MovieListItem
import com.rohitsharma.movies.mapper.toPresentation
import com.rohitsharma.movies.ui.base.BaseViewModel
import com.rohitsharma.movies.util.singleSharedFlow
import com.rohitsharma.data.util.DispatchersProvider
import com.rohitsharma.domain.usecase.GetFavoriteMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getFavoriteMovies: GetFavoriteMovies,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    data class FavoriteUiState(
        val isLoading: Boolean = true,
        val noDataAvailable: Boolean = false
    )

    sealed class NavigationState {
        data class MovieDetails(val movieId: Int) : NavigationState()
    }

    val movies: Flow<PagingData<MovieListItem>> = getFavoriteMovies(30).map {
        it.map { it.toPresentation() as MovieListItem }
    }.cachedIn(viewModelScope)

    private val _uiState: MutableStateFlow<FavoriteUiState> = MutableStateFlow(FavoriteUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState: MutableSharedFlow<NavigationState> = singleSharedFlow()
    val navigationState = _navigationState.asSharedFlow()

    fun onMovieClicked(movieId: Int) =
        _navigationState.tryEmit(NavigationState.MovieDetails(movieId))

    fun onLoadStateUpdate(loadState: CombinedLoadStates, itemCount: Int) {
        val showLoading = loadState.refresh is LoadState.Loading
        val showNoData = loadState.append.endOfPaginationReached && itemCount < 1

        _uiState.update {
            it.copy(
                isLoading = showLoading,
                noDataAvailable = showNoData
            )
        }
    }
}
