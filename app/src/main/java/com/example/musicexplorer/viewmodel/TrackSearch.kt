package com.example.musicexplorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicexplorer.model.AlbumResponse
import com.example.musicexplorer.model.ArtistResponse
import com.example.musicexplorer.model.TrackResponse
import com.example.musicexplorer.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class TrackSearch : ViewModel() {

    // StateFlows for search results
    private val _artistInfo = MutableStateFlow<ArtistResponse?>(null)
    val artistInfo: StateFlow<ArtistResponse?> get() = _artistInfo

    private val _albumInfo = MutableStateFlow<AlbumResponse?>(null)  // Now stores AlbumResponse
    val albumInfo: StateFlow<AlbumResponse?> get() = _albumInfo

    private val _trackInfo = MutableStateFlow<TrackResponse?>(null)
    val trackInfo: StateFlow<TrackResponse?> get() = _trackInfo

    // Function to search for an artist
    fun searchArtist(query: String) {
        viewModelScope.launch {
            try {
                val response: ArtistResponse = RetrofitInstance.api.getArtistInfo(artistName = query)
                _artistInfo.value = response
                Log.d("ArtistSearch", "Success: $response")
            } catch (e: Exception) {
                Log.e("ArtistSearch", "Error fetching artist info: ${e.message}")
            }
        }
    }

    // Function to search for an album
    fun searchAlbum(artist: String, album: String) {
        viewModelScope.launch {
            try {
                val response: AlbumResponse = RetrofitInstance.api.getAlbumInfo(
                    artistName = artist,
                    albumName = album,
                    autocorrect = 1
                )

                logRawResponse(response)

                // Verify if the returned artist matches the input
                if (!response.album.artist.equals(artist, ignoreCase = true)) {
                    println("Warning: Returned artist ${response.album.artist} doesn't match input artist $artist")
                }

                _albumInfo.value = response
            } catch (e: Exception) {
                println("Error fetching album info: ${e.message}")
            }
        }
    }

    private fun logRawResponse(response: AlbumResponse) {
        println("Raw JSON Response: $response")
    }

    // Function to search for a track
    fun searchTrack(artist: String, track: String) {
        viewModelScope.launch {
            try {
                val response: TrackResponse = RetrofitInstance.api.getTrackInfo(artistName = artist, trackName = track)
                _trackInfo.value = response
                Log.d("TrackSearch", "Success: $response")
            } catch (e: Exception) {
                Log.e("TrackSearch", "Error fetching track info: ${e.message}")
            }
        }
    }
}