package com.yurykasper.photomap.main.timeline

import com.yurykasper.photomap.models.photo.PhotoDVO
import com.yurykasper.photomap.services.firestoreService.FirestoreService
import com.yurykasper.photomap.services.firestoreService.FirestoreServiceType
import io.reactivex.subjects.BehaviorSubject

class TimelineViewModel {
    private val firestoreService: FirestoreServiceType = FirestoreService()

    val photosDVOList: BehaviorSubject<List<PhotoDVO>> = BehaviorSubject.createDefault(
        emptyList()
    )

    init {
        firestoreService.getPhotos()
            .subscribe(photosDVOList)
    }
}