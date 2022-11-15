package com.yurykasper.photomap.models.photo

import com.yurykasper.photomap.models.category.CategoryDTO
import com.yurykasper.photomap.models.user.UserDTO
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class PhotoDVO(
    val id: String,
    var title: String,
    var description: String,
    var category: CategoryDTO,
    val addingDate: Timestamp,
    val author: UserDTO,
    var photoURLs: List<String>,
    var location: GeoPoint
): java.io.Serializable