package com.yurykasper.photomap.models.photo

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class PhotoDTO(
    val uid: String,
    val title: String,
    val description: String,
    val category: String,
    val addingDate: Timestamp,
    val authorId: String,
    val photoURLs: List<String>,
    val location: GeoPoint
)
