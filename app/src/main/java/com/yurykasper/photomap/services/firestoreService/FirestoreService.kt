package com.yurykasper.photomap.services.firestoreService

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.storage.FirebaseStorage
import com.yurykasper.photomap.models.photo.PhotoDTO
import com.yurykasper.photomap.models.photo.PhotoDVO
import com.yurykasper.photomap.models.category.CategoryDTO
import com.yurykasper.photomap.models.user.UserDTO
import io.reactivex.Observable
class FirestoreService: FirestoreServiceType {

    private val currentUserUid: String?
        get () = FirebaseAuth.getInstance().uid

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
                            it.data?.getValue(categoryNamePropertyKey) as String
                        )
                    }
                    println(categories)
                observer.onNext(categories)
            }
                .addOnFailureListener { observer.onNext(emptyList()) }
        }
    }

    override fun getPhotos(): Observable<List<PhotoDVO>> {
        return getPhotoDTOList().flatMap { photoDTOList ->
            return@flatMap getCategories().flatMap { categoriesDTOList ->
                return@flatMap getUserDTOList().map { userDTOList ->
                    return@map photoDTOList.map { photoDTO ->
                        val categoryDTO = categoriesDTOList.filter { it.uid == photoDTO.category }[0]
                        val userDTO = userDTOList.filter { it.id == photoDTO.authorId }[0]

                        return@map PhotoDVO(
                            photoDTO.uid,
                            photoDTO.name,
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

    // MARK: - Helpers

     fun getPhotoDTOList(): Observable<List<PhotoDTO>> {
        return Observable.create<List<PhotoDTO>> { observer ->
            val photosReference = db.collection(photosCollectionPath)

            photosReference.get()
                .addOnSuccessListener {
                    val documents = it.documents
                    val photoDTOList = documents.map {
                        return@map PhotoDTO(
                            it.id,
                            it.data?.getValue(photoNamePropertyKey) as String,
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

    fun getUserDTOList(): Observable<List<UserDTO>> {
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
        // Category Property Keys
        val categoryNamePropertyKey = "name"

        // Photo Property Keys
        val photoNamePropertyKey = "name"
        val photoDescriptionPropertyKey = "description"
        val photoCategotyPropertyKey = "category"
        val photoAddingDatePropertyKey = "addingDate"
        val photoAuthorIdPropertyKey = "authorId"
        val photoPhotosPropertyKey = "photos"
        val photoLocationPropertyKey = "location"

        // User Property Keys
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