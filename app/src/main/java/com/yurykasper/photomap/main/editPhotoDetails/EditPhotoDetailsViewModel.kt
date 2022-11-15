package com.yurykasper.photomap.main.editPhotoDetails

import com.yurykasper.photomap.models.photo.PhotoDVO
import com.yurykasper.photomap.services.firestoreService.FirestoreService
import com.yurykasper.photomap.services.firestoreService.FirestoreServiceType

class EditPhotoDetailsViewModel(val photoDVO: PhotoDVO?) {

    private val firestoreService: FirestoreServiceType = FirestoreService()

    fun saveChanges() {

    }

}