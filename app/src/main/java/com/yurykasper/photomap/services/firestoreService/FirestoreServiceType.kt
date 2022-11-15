package com.yurykasper.photomap.services.firestoreService

import com.yurykasper.photomap.models.category.CategoryDTO
import com.yurykasper.photomap.models.photo.PhotoDVO
import io.reactivex.Observable
import io.reactivex.Single

interface FirestoreServiceType {
    fun getCategories(): Observable<List<CategoryDTO>>
    fun getPhotos(): Observable<List<PhotoDVO>>

    fun updatePhotoInformationWith(photo: PhotoDVO): Single<Boolean>
}