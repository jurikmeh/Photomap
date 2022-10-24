package com.yurykasper.photomap.auth.sign_in

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject

class LoginViewModel: ViewModel(), LoginViewModelType {

    override val inputs: LoginViewModelInput = this
    override val outputs: LoginViewModelOutput = this

    private  lateinit var auth: FirebaseAuth

    // Inputs
    private val email = BehaviorSubject.create<String>()
    override fun emailChanged(text: String) = email.onNext(text)

    private val password = BehaviorSubject.create<String>()
    override fun passwordChanged(text: String) = password.onNext(text)

    override fun loginButtonPressed(): Boolean {
        var isAuthorized = false
        if (auth.currentUser == null) {
            auth.signInWithEmailAndPassword(email.value!!, password.value!!)
                .addOnSuccessListener {
                    val user = auth.currentUser
                    isAuthorized = true
                }
            return isAuthorized
        } else {
            return  true
        }
    }

    // Outputs
    override val loginButtonEnabled: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    // Init
    init {
        auth = FirebaseAuth.getInstance()

        Observables.combineLatest(email, password)
            .map { (email, password) -> email.isNotEmpty() && password.isNotEmpty() }
            .subscribe(loginButtonEnabled)
    }

}