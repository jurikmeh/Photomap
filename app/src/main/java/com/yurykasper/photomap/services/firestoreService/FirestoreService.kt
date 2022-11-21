package com.yurykasper.photomap.services.firestoreService

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import com.yurykasper.photomap.main.editPhotoDetails.PhotoSaveState
import com.yurykasper.photomap.models.photo.PhotoDTO
import com.yurykasper.photomap.models.photo.PhotoDVO
import com.yurykasper.photomap.models.category.CategoryDTO
import com.yurykasper.photomap.models.user.UserDTO
import io.reactivex.Observable
import io.reactivex.Single

class FirestoreService: FirestoreServiceType {

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    override fun getCategories(): Observable<List<CategoryDTO>> {
        return Observable.create<List<CategoryDTO>> { observer ->
            val categoriesReference = db.collection(categoriesCollectionPath)

            categoriesReference.get()
                .addOnSuccessListener {
                    val documents = it.documents
                    val categories = documents.map {
                        return@map CategoryDTO(
                            it.id,
                            it.data?.getValue(categoryTitlePropertyKey) as String
                        )
                    }
                observer.onNext(categories)
            }
                .addOnFailureListener { observer.onNext(emptyList()) }
        }
    }

    override fun savePhotoInformation(photo: PhotoDVO): Single<PhotoSaveState> {
        return Single.create<PhotoSaveState> { single ->
            val photoCollectionReference = db.collection(photosCollectionPath)

            val photoInfo = mutableMapOf<String, Any>()
            photoInfo.put(photoAddingDatePropertyKey, photo.addingDate)
            photoInfo.put(photoAuthorIdPropertyKey, photo.author.uid)
            photoInfo.put(photoCategotyPropertyKey, photo.category.uid)
            photoInfo.put(photoDescriptionPropertyKey, photo.description)
            photoInfo.put(photoLocationPropertyKey, photo.location)
            photoInfo.put(photoTitlePropertyKey, photo.title)
            photoInfo.put(photoPhotosPropertyKey, photo.photoURLs)

            if (photo.id.isEmpty()) {
                photoCollectionReference.add(photoInfo)
                    .addOnSuccessListener { single.onSuccess(PhotoSaveState.CREATE) }
            } else {
                val photoDocumentReference = photoCollectionReference.document(photo.id)
                photoDocumentReference.update(photoInfo)
                    .addOnSuccessListener { single.onSuccess(PhotoSaveState.EDIT) }
            }
        }
    }

    override fun getPhotos(): Observable<List<PhotoDVO>> {
        return getPhotoDTOList().flatMap { photoDTOList ->
            return@flatMap getCategories().flatMap { categoriesDTOList ->
                return@flatMap getUserDTOList().map { userDTOList ->
                    return@map photoDTOList.map { photoDTO ->
                        val categoryDTO = categoriesDTOList.filter { it.uid == photoDTO.category }[0]
                        val userDTO = userDTOList.filter { it.uid == photoDTO.authorId }[0]

                        return@map PhotoDVO(
                            photoDTO.uid,
                            photoDTO.title,
                            photoDTO.description,
                            categoryDTO,
                            photoDTO.addingDate,
                            userDTO,
                            photoDTO.photoURLs,
                            photoDTO.location
                        )
                    }
                }
            }
        }
    }

    override fun getPhotoWith(id: String): Observable<PhotoDVO> {
        return getPhotoDTOWith(id)
            .flatMap { photoDTO ->
                val authorId = photoDTO.authorId
                val categoryId = photoDTO.category

                return@flatMap Observable.zip(getUserWith(authorId), getCategoryWith(categoryId), { user, category ->
                    PhotoDVO(
                        id,
                        photoDTO.title,
                        photoDTO.description,
                        category,
                        photoDTO.addingDate,
                        user,
                        photoDTO.photoURLs,
                        photoDTO.location
                    )
                })
            }
    }

    // MARK: - Helpers

    private fun getCategoryWith(id: String): Observable<CategoryDTO> {
        return Observable.create<CategoryDTO> { observer ->
            val categoryReference = db.collection(categoriesCollectionPath).document(id)

            categoryReference.get()
                .addOnSuccessListener {
                    val category = CategoryDTO(
                        id,
                        it.data?.getValue(categoryTitlePropertyKey) as String
                    )
                    observer.onNext(category)
                }
        }
    }

    private fun getUserWith(id: String): Observable<UserDTO> {
        return Observable.create<UserDTO> { observer ->
            val userReference = db.collection(usersCollectionPath).document(id)

            userReference.get()
                .addOnSuccessListener {
                    val user = UserDTO(
                        id,
                        it.data?.getValue(userEmailPropertyKey) as String,
                        it.data?.getValue(userFirstnamePropertyKey) as String,
                        it.data?.getValue(userLastnamePropertyKey) as String,
                        it.data?.getValue(userPhonePropertyKey) as String
                    )
                    observer.onNext(user)
                }
        }
    }

    private fun getPhotoDTOWith(id: String): Observable<PhotoDTO> {
        return Observable.create<PhotoDTO> { observer ->
            val photoReference = db.collection(photosCollectionPath).document(id)

            photoReference.get()
                .addOnSuccessListener {
                    val photoDTO = PhotoDTO(
                        id,
                        it.data?.getValue(photoTitlePropertyKey) as String,
                        it.data?.getValue(photoDescriptionPropertyKey) as String,
                        it.data?.getValue(photoCategotyPropertyKey) as String,
                        it.data?.getValue(photoAddingDatePropertyKey) as Timestamp,
                        it.data?.getValue(photoAuthorIdPropertyKey) as String,
                        it.data?.getValue(photoPhotosPropertyKey) as List<String>,
                        it.data?.getValue(photoLocationPropertyKey) as GeoPoint
                    )
                    observer.onNext(photoDTO)
                }
        }
    }

     private fun getPhotoDTOList(): Observable<List<PhotoDTO>> {
        return Observable.create<List<PhotoDTO>> { observer ->
            val photosReference = db.collection(photosCollectionPath)

            photosReference.get()
                .addOnSuccessListener {
                    val documents = it.documents
                    val photoDTOList = documents.map {
                        return@map PhotoDTO(
                            it.id,
                            it.data?.getValue(photoTitlePropertyKey) as String,
                            it.data?.getValue(photoDescriptionPropertyKey) as String,
                            it.data?.getValue(photoCategotyPropertyKey) as String,
                            it.data?.getValue(photoAddingDatePropertyKey) as Timestamp,
                            it.data?.getValue(photoAuthorIdPropertyKey) as String,
                            it.data?.getValue(photoPhotosPropertyKey) as List<String>,
                            it.data?.getValue(photoLocationPropertyKey) as GeoPoint
                        )
                    }
                    observer.onNext(photoDTOList)
                }
                .addOnFailureListener { observer.onNext(emptyList()) }
        }
    }

    private fun getUserDTOList(): Observable<List<UserDTO>> {
        return  Observable.create<List<UserDTO>> { observer ->
            val usersReference = db.collection(usersCollectionPath)

            usersReference.get()
                .addOnSuccessListener {
                    val documents = it.documents

                    val userDTOList = documents.map {
                        return@map UserDTO(
                            it.id,
                            it.data?.getValue(userEmailPropertyKey) as String,
                            it.data?.getValue(userFirstnamePropertyKey) as String,
                            it.data?.getValue(userLastnamePropertyKey) as String,
                            it.data?.getValue(userPhonePropertyKey) as String
                        )
                    }
                    observer.onNext(userDTOList)
                }
                .addOnFailureListener { observer.onNext(emptyList()) }
        }
    }

    // Keys

    companion object {
        // Category Properties
        val categoryTitlePropertyKey = "title"

        // Photo Properties
        val photoTitlePropertyKey = "title"
        val photoDescriptionPropertyKey = "description"
        val photoCategotyPropertyKey = "categoryId"
        val photoAddingDatePropertyKey = "addingDate"
        val photoAuthorIdPropertyKey = "authorId"
        val photoPhotosPropertyKey = "photos"
        val photoLocationPropertyKey = "location"

        // User Properties
        val userEmailPropertyKey = "email"
        val userFirstnamePropertyKey = "firstname"
        val userLastnamePropertyKey = "lastname"
        val userPhonePropertyKey = "phone"

        // Path names
        val categoriesCollectionPath = "categories"
        val photosCollectionPath = "photos"
        val usersCollectionPath = "users"
    }

}