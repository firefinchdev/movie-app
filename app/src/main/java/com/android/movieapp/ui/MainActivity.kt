package com.android.movieapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.android.movieapp.App
import com.android.movieapp.CropTransformation
import com.android.movieapp.model.MovieDetail
import com.android.movieapp.repo.MovieRepo
import com.android.movieapp.service.MovieResponse
import com.android.movieapp.theme.MovieAppTheme

class MainActivity: ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.provideFactory(
            App.instance, this, MovieRepo()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                MainScreen(viewModel)
            }
        }
    }

    override fun onBackPressed() {
        val state = viewModel.state
        if (state.value?.selectedMovie != null) {
            viewModel.unselectMovie()
        } else {
            super.onBackPressed()
        }
    }
}

@Composable
private fun MainScreen(
    viewModel: MainViewModel
) {
    val state by viewModel.state.observeAsState(MainState())
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie App") }
            )
        }
    ) {
        Surface(Modifier.fillMaxSize()) {
            if (state.selectedMovie == null) {
                Column(Modifier.fillMaxSize()) {
                    if (state.isLoading) {
                        Text("Loading")
                    }
                    MovieSearch(state.query,
                        onValueChange = {
                            viewModel.search(query = it)
                        }
                    )
                    MovieList(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        state.movieDetails,
                        onClick = {
                            viewModel.selectMovie(it)
                        }
                    )
                }
            } else {
                state.selectedMovie?.let {
                    SingleMovieScreen(it)
                }
            }
        }
    }
}

@Composable
fun SingleMovieScreen(
    movie: MovieResponse
) {
    Box() {
        Column() {
            Image(
                rememberImagePainter(
                    movie.poster,
                    builder = {
                        transformations(CropTransformation())
                    }
                ),
                contentDescription = null,
                Modifier.height(80.dp)
            )
            Text(movie.title, Modifier.height(40.dp))
        }
    }
}

@Composable
private fun MovieSearch(
    query: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = query,
        onValueChange = onValueChange,
        Modifier
            .padding(10.dp)
            .height(60.dp)
            .fillMaxSize()
    )
}

@Composable
private fun MovieList(
    modifier: Modifier = Modifier,
    movies: List<MovieDetail>,
    onClick: (id: String) -> Unit
) {
    LazyColumn(modifier) {
        items(movies) { movie ->
            MovieItem(
                movie,
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                onClick = { onClick(movie.id) }
            )
        }
    }
}

@Composable
private fun MovieItem(
    movie: MovieDetail,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(modifier) {
        Column(
            Modifier.clickable { onClick() }
        ) {
            Image(
                rememberImagePainter(
                    movie.poster,
                    builder = {
                        transformations(CropTransformation())
                    }
                ),
                contentDescription = null,
                Modifier.height(80.dp)
            )
            Text(movie.title, Modifier.height(40.dp))
        }
    }
}