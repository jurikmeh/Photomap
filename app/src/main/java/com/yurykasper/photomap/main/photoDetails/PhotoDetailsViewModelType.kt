package com.yurykasper.photomap.main.photoDetails

import com.yurykasper.photomap.models.photo.PhotoDVO
import io.reactivex.subjects.BehaviorSubject

interface PhotoDetailsViewModelInputType {
    fun getPhotoDetails(id: String)
}

interface PhotoDetailsViewModelOutputType {
    val photoDetails: BehaviorSubject<PhotoDVO>
}

interface PhotoDetailsViewModelType: PhotoDetailsViewModelInputType, PhotoDetailsViewModelOutputType {
    val inputs: PhotoDetailsViewModelInputType
    val outputs: PhotoDetailsViewModelOutputType
}