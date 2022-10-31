package com.yurykasper.photomap.services.authService

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable

class AuthService: AuthServiceType {

    private val auth = FirebaseAuth.getInstance()

    override fun signIn(email: String, password: String): Observable<Boolean> {
            return Observable.create<Boolean> { observer ->
                if (auth.currentUser == null) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener { observer.onNext(true) }
                        .addOnFailureListener { observer.onNext(false) }
                } else {
                    observer.onNext(true)
                }
            }
    }

    override fun signUp(
        email: String,
        password: String,
        firstname: String,
        lastname: String,
        phone: String
    ): Observable<Boolean> {
        return Observable.create<Boolean> { observer ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener { observer.onNext(false) }
                .addOnSuccessListener {
                    val user = hashMapOf(
                        "email" to email,
                        "password" to password,
                        "firstname" to firstname,
                        "lastname" to lastname,
                        "phone" to "+${phone}"
                    )
                    FirebaseFirestore.getInstance().collection("users").add(user)
                    observer.onNext(true)
                }
        }
    }

    override fun logout() {
        auth.signOut()
    }
}