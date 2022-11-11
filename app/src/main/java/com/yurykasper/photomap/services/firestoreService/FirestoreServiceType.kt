package com.yurykasper.photomap.services.firestoreService

import com.yurykasper.photomap.models.category.CategoryDTO
import com.yurykasper.photomap.models.photo.PhotoDVO
import io.reactivex.Observable

interface FirestoreServiceType {
    fun getCategories(): Observable<List<CategoryDTO>>
    fun getPhotos(): Observable<List<PhotoDVO>>
}