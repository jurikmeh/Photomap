package com.yurykasper.photomap.auth.sign_up

import io.reactivex.subjects.BehaviorSubject

interface RegistrationViewModelInput {
    fun emailChanged(text: String)
    fun passwordChanged(text: String)
    fun firstNameChanged(text: String)
    fun lastNameChanged(text: String)
    fun phoneChanged(text: String)

    fun registrationButtonPressed()
}

interface RegistrationViewModelOutput {
    val registrationButtonEnabled: BehaviorSubject<Boolean>
}

interface RegistrationViewModelType: RegistrationViewModelInput, RegistrationViewModelOutput {
    val inputs: RegistrationViewModelInput
    val outputs: RegistrationViewModelOutput
}