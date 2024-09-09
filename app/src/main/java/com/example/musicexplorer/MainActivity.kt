package com.example.musicexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.musicexplorer.components.TrackSearchScreen
import com.example.musicexplorer.ui.theme.MusicExplorerTheme
import com.example.musicexplorer.viewmodel.TrackSearch

class MainActivity : ComponentActivity() {

    // Initialize the ViewModel
    private val viewModel: TrackSearch by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MusicExplorerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    // Collect the state from the ViewModel
                    val artistInfo = viewModel.artistInfo.collectAsState().value
                    val albumInfo = viewModel.albumInfo.collectAsState().value
                    val trackInfo = viewModel.trackInfo.collectAsState().value

                    // Pass the search functions and collected states to the UI
                    TrackSearchScreen(
                        onSearchArtist = { query ->
                            viewModel.searchArtist(query)
                        },
                        onSearchAlbum = { artist, album ->  // Pass two parameters for album search
                            viewModel.searchAlbum(artist, album)
                        },
                        onSearchTrack = { artist, track ->  // Pass two parameters for track search
                            viewModel.searchTrack(artist, track)
                        },
                        artistInfo = artistInfo,
                        albumInfo = albumInfo,
                        trackInfo = trackInfo,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}