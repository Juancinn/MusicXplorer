package com.example.musicexplorer.network

import com.example.musicexplorer.BuildConfig
import com.example.musicexplorer.model.AlbumResponse
import com.example.musicexplorer.model.ArtistResponse
import com.example.musicexplorer.model.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmApiService {

    @GET("2.0/")
    suspend fun getAlbumInfo(
        @Query("method") method: String = "album.getInfo",
        @Query("artist") artistName: String,
        @Query("album") albumName: String,
        @Query("api_key") apiKey: String = BuildConfig.LASTFM_API_KEY,
        @Query("format") format: String = "json",
        @Query("autocorrect") autocorrect: Int = 1 // Enable autocorrect
    ): AlbumResponse

    @GET("2.0/")
    suspend fun getArtistInfo(
        @Query("method") method: String = "artist.getInfo",
        @Query("artist") artistName: String,
        @Query("api_key") apiKey: String = BuildConfig.LASTFM_API_KEY,
        @Query("format") format: String = "json"
    ): ArtistResponse

    @GET("2.0/")
    suspend fun getTrackInfo(
        @Query("method") method: String = "track.getInfo",
        @Query("artist") artistName: String,
        @Query("track") trackName: String,
        @Query("api_key") apiKey: String = BuildConfig.LASTFM_API_KEY,
        @Query("format") format: String = "json"
    ): TrackResponse
}