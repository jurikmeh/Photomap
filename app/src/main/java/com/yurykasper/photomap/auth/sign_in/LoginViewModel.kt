package com.yurykasper.photomap.auth.sign_in

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

class LoginViewModel: ViewModel(), LoginViewModelType {

    override val inputs: LoginViewModelInput = this
    override val outputs: LoginViewModelOutput = this

    // Inputs
    private val username: PublishSubject<String> = PublishSubject.create<String>()
    override fun usernameChanged(text: String) = username.onNext(text)

    private val password: PublishSubject<String> = PublishSubject.create<String>()
    override fun passwordChanged(text: String) = password.onNext(text)

    // Outputs



    // Init
    init {

    }

}