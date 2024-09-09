package com.example.musicexplorer.model

import com.google.gson.annotations.SerializedName

// Album Response
data class AlbumResponse(
    val album: Album
)

data class Album(
    val name: String,
    val artist: String,
    val releaseDate: String?,
    val tracks: TrackList?,
    val image: List<Image>,
    val playcount: String?,          // Playcount added here
    val tags: Tags?,                 // Tags field added
    val wiki: Wiki?                  // Wiki field for content and description
)

// Track List and Track
data class TrackList(
    val track: List<Track>
)

data class Track(
    val name: String,
    val duration: Int,
    val url: String
)

// Image
data class Image(
    val size: String,
    @SerializedName("#text") val text: String // This points to the image URL
)

// Tags
data class Tags(
    val tag: List<Tag>
)

data class Tag(
    val name: String,
    val url: String
)

// Wiki
data class Wiki(
    val published: String?,          // Published date for the album
    val summary: String?,            // Short summary of the album
    val content: String?             // Full content/description of the album
)

// Track Response
data class TrackResponse(
    val track: TrackDetail
)

data class TrackDetail(
    val name: String,
    val duration: Int,
    val artist: TrackArtist,
    val url: String
)

data class TrackArtist(
    val name: String,
    val url: String
)

// Artist Response
data class ArtistResponse(
    val artist: Artist
)

data class Artist(
    val name: String,
    val url: String,                 // URL to the artist's page
    val listeners: String,           // Number of listeners
    val playcount: String            // Play count of the artist
)