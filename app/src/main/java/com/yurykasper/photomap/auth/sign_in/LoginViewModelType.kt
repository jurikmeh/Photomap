package com.yurykasper.photomap.auth.sign_in

import io.reactivex.subjects.BehaviorSubject

interface LoginViewModelInput {
    fun emailChanged(text: String)
    fun passwordChanged(text: String)
    fun loginButtonPressed(): Boolean
}

interface LoginViewModelOutput {
    val loginButtonEnabled: BehaviorSubject<Boolean>
}

interface LoginViewModelType: LoginViewModelInput, LoginViewModelOutput {
    val inputs: LoginViewModelInput
    val outputs: LoginViewModelOutput
}