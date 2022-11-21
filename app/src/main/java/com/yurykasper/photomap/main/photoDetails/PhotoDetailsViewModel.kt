package com.yurykasper.photomap.main.photoDetails

import com.yurykasper.photomap.models.photo.PhotoDVO
import com.yurykasper.photomap.services.firestoreService.FirestoreService
import com.yurykasper.photomap.services.firestoreService.FirestoreServiceType
import io.reactivex.subjects.BehaviorSubject

class PhotoDetailsViewModel(): PhotoDetailsViewModelType {
    override val inputs: PhotoDetailsViewModelInputType = this
    override val outputs: PhotoDetailsViewModelOutputType = this

    private val firestoreService: FirestoreServiceType = FirestoreService()

    // Inputs
    override fun getPhotoDetails(id: String) {
        firestoreService.getPhotoWith(id)
            .subscribe(photoDetails)
    }

    // Outputs
    override val photoDetails: BehaviorSubject<PhotoDVO> = BehaviorSubject.create()
}