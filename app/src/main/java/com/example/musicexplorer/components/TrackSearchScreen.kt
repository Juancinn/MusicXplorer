package com.example.musicexplorer.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.musicexplorer.model.ArtistResponse
import com.example.musicexplorer.model.TrackResponse
import com.example.musicexplorer.model.AlbumResponse


@Composable
fun TrackSearchScreen(
    onSearchArtist: (String) -> Unit,
    onSearchAlbum: (String, String) -> Unit,
    onSearchTrack: (String, String) -> Unit,
    artistInfo: ArtistResponse?,
    albumInfo: AlbumResponse?,
    trackInfo: TrackResponse?,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var albumArtist by remember { mutableStateOf("") }  // Artist input for album search
    var albumName by remember { mutableStateOf("") }    // Album name input
    var trackArtist by remember { mutableStateOf("") }  // Artist input for track search
    var trackName by remember { mutableStateOf("") }    // Track name input
    var selectedSearchType by remember { mutableStateOf("Artist") }  // Track the selected search type

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Pill-like buttons to select the search type
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SearchPillButton(
                text = "Artist",
                isSelected = selectedSearchType == "Artist",
                onClick = { selectedSearchType = "Artist" }
            )
            SearchPillButton(
                text = "Album",
                isSelected = selectedSearchType == "Album",
                onClick = { selectedSearchType = "Album" }
            )
            SearchPillButton(
                text = "Track",
                isSelected = selectedSearchType == "Track",
                onClick = { selectedSearchType = "Track" }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input fields for artist and album/track search
        if (selectedSearchType == "Album") {
            TextField(
                value = albumArtist,
                onValueChange = { albumArtist = it },
                label = { Text("Enter Artist Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = albumName,
                onValueChange = { albumName = it },
                label = { Text("Enter Album Name") },
                modifier = Modifier.fillMaxWidth()
            )
        } else if (selectedSearchType == "Track") {
            TextField(
                value = trackArtist,
                onValueChange = { trackArtist = it },
                label = { Text("Enter Artist Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = trackName,
                onValueChange = { trackName = it },
                label = { Text("Enter Track Name") },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search for an Artist") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Search button based on selected search type
        Button(
            onClick = {
                when (selectedSearchType) {
                    "Artist" -> onSearchArtist(searchQuery)
                    "Album" -> onSearchAlbum(albumArtist, albumName)  // Pass both artist and album
                    "Track" -> onSearchTrack(trackArtist, trackName)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search $selectedSearchType")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display search results based on the selected type
        when (selectedSearchType) {
            "Artist" -> artistInfo?.let { artist ->
                Text(text = "Artist: ${artist.artist.name}, URL: ${artist.artist.url}")
            }
            "Album" -> albumInfo?.let { album ->
                Text(text = "Album: ${album.album.name}")
                Text(text = "Artist: ${album.album.artist}")
                Text(text = "Release Date: ${album.album.wiki?.published ?: "Unknown"}")

                // Display album image
                album.album.image.firstOrNull { it.size == "extralarge" || it.size == "large" }?.let { image ->
                    Image(
                        painter = rememberAsyncImagePainter(image.text),
                        contentDescription = "Album Art",
                        modifier = Modifier
                            .size(200.dp)
                            .padding(8.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Display track list
                album.album.tracks?.let { trackList ->
                    LazyColumn {
                        items(trackList.track) { track ->
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(text = "Track: ${track.name}")
                                Text(text = "Duration: ${formatDuration(track.duration)}")
                            }
                        }
                    }
                }
            }
            "Track" -> trackInfo?.let { track ->
                Text(text = "Track: ${track.track.name}")
                Text(text = "Artist: ${track.track.artist.name}")
                Text(text = "Duration: ${track.track.duration}")
            }
        }
    }
}
@Composable
fun SearchPillButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Blue else Color.LightGray,
            contentColor = Color.White
        ),
        modifier = Modifier
            .height(40.dp)
            .padding(horizontal = 8.dp)
    ) {
        Text(text)
    }
}
fun formatDuration(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%d:%02d", minutes, remainingSeconds)
}