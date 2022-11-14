package com.yurykasper.photomap.models.photo

import com.yurykasper.photomap.models.category.CategoryDTO
import com.yurykasper.photomap.models.user.UserDTO
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class PhotoDVO(
    val id: String,
    val title: String,
    val description: String,
    val category: CategoryDTO,
    val addingDate: Timestamp,
    val author: UserDTO,
    val photoURLs: List<String>,
    val location: GeoPoint
): java.io.Serializable