package com.yurykasper.photomap.main.editPhotoDetails

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import com.yurykasper.photomap.models.category.CategoryDTO
import com.yurykasper.photomap.models.photo.PhotoDVO
import com.yurykasper.photomap.models.user.UserDTO
import com.yurykasper.photomap.services.firestoreService.FirestoreService
import com.yurykasper.photomap.services.firestoreService.FirestoreServiceType
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
import java.util.Date
import java.util.Random

class EditPhotoDetailsViewModel(val photoDVO: PhotoDVO?): EditPhotoDetailsViewModelType {

    override val inputs: EditPhotoDetailsViewModelInput = this
    override val outputs: EditPhotoDetailsViewModelOutput = this

    private val firestoreService: FirestoreServiceType = FirestoreService()

    // Inputs
    private val titleSubject = BehaviorSubject.createDefault(photoDVO?.title ?: "")
    override fun titleChanged(text: String) = titleSubject.onNext(text)

    private val descriptionSubject = BehaviorSubject.createDefault(photoDVO?.description ?: "")
    override fun descriptionChanged(text: String) = descriptionSubject.onNext(text)

    private val categorySubject: BehaviorSubject<CategoryDTO> = BehaviorSubject.create()
    override fun categoryChanged(index: Int) {
        val categoriesList = categories.value ?: emptyList()
        categorySubject.onNext(categoriesList.get(index))
    }

    // Add properties for image urls && GeoPoint

    override fun saveChanges() {
            Observables.combineLatest(titleSubject, descriptionSubject, categorySubject)
                .flatMap {
                    val photo: PhotoDVO
                    if (photoDVO != null) {
                        photo = PhotoDVO(
                            photoDVO.id,
                            it.first,
                            it.second,
                            it.third,
                            photoDVO.addingDate,
                            photoDVO.author,
                            photoDVO.photoURLs,
                            photoDVO.location
                        )
                    } else {
                        photo = PhotoDVO(
                            "",
                            it.first,
                            it.second,
                            it.third,
                            Timestamp(Date()),
                            UserDTO( // TODO: - somehow get from `SharedPreferences`
                                "tWyJ5hhvdIMD6thmXPFy3DYuRF13",
                                "kasperyuriu@gmail.com",
                                "Yury",
                                "Kasper",
                                "+375336871259"
                            ),
                            emptyList(),
                            GeoPoint(Random().nextDouble(), Random().nextDouble())
                        )
                    }

                    return@flatMap firestoreService.savePhotoInformation(photo)
                        .toObservable()
                }
                .subscribe(changeState)
    }

    // Outputs
    override val saveButtonEnabled: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    override val changeState: BehaviorSubject<PhotoSaveState> = BehaviorSubject.createDefault(PhotoSaveState.NONE)
    override val categories: BehaviorSubject<List<CategoryDTO>> = BehaviorSubject.createDefault(emptyList())

    init {
        Observables.combineLatest(titleSubject, descriptionSubject, categorySubject)
            .map { t -> t.first.isNotEmpty() && t.second.isNotEmpty() && t.third.title.isNotEmpty() }
            .subscribe(saveButtonEnabled)

        firestoreService.getCategories()
            .subscribe(categories)
    }

}