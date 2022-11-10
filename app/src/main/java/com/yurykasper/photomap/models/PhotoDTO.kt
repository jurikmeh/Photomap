package com.yurykasper.photomap.models

import android.location.Location
import java.util.Date

data class PhotoDTO(
    val name: String,
    val description: String,
    val category: String,
    val addingDate: Date,
    val authorId: String,
    val photoURLs: List<String>,
    val location: Location
): java.io.Serializable
