package com.yurykasper.photomap.auth.sign_in

interface LoginViewModelInput {
    fun usernameChanged(text: String)
    fun passwordChanged(text: String)
}

interface LoginViewModelOutput {

}

interface LoginViewModelType: LoginViewModelInput, LoginViewModelOutput {
    val inputs: LoginViewModelInput
    val outputs: LoginViewModelOutput
}