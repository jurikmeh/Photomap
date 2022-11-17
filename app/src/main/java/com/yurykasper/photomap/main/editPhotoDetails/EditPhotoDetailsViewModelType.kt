package com.yurykasper.photomap.main.editPhotoDetails

import com.yurykasper.photomap.models.category.CategoryDTO
import io.reactivex.subjects.BehaviorSubject

interface EditPhotoDetailsViewModelInput {
    fun titleChanged(text: String)
    fun descriptionChanged(text: String)
    fun categoryChanged(index: Int)

    fun saveChanges()
}

interface EditPhotoDetailsViewModelOutput {
    val saveButtonEnabled: BehaviorSubject<Boolean>
    val changeState: BehaviorSubject<PhotoSaveState>
    val categories: BehaviorSubject<List<CategoryDTO>>
}

interface EditPhotoDetailsViewModelType: EditPhotoDetailsViewModelInput, EditPhotoDetailsViewModelOutput {
    val inputs: EditPhotoDetailsViewModelInput
    val outputs: EditPhotoDetailsViewModelOutput
}