package com.yurykasper.photomap.auth.sign_up

import androidx.lifecycle.ViewModel
import com.yurykasper.photomap.services.authService.AuthService
import com.yurykasper.photomap.services.authService.AuthServiceType
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
class RegistrationViewModel: ViewModel(), RegistrationViewModelType {

    override val inputs: RegistrationViewModelInput = this
    override val outputs: RegistrationViewModelOutput = this

    private val authService: AuthServiceType = AuthService()

    // Inputs
    private val email = BehaviorSubject.create<String>()
    override  fun emailChanged(text: String) = email.onNext(text)

    private val password = BehaviorSubject.create<String>()
    override fun passwordChanged(text: String) = password.onNext(text)

    private val firstName = BehaviorSubject.create<String>()
    override fun firstNameChanged(text: String) = firstName.onNext(text)

    private val lastName = BehaviorSubject.create<String>()
    override fun lastNameChanged(text: String) = lastName.onNext(text)

    private val phone = BehaviorSubject.create<String>()
    override fun phoneChanged(text: String) = phone.onNext(text)

    private val registrationButtonSubject: PublishSubject<Unit> = PublishSubject.create()
    override fun registrationButtonPressed() = registrationButtonSubject.onNext(Unit)

    // Outputs
    override val registrationButtonEnabled: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    override var showMainFragment: Observable<Boolean> = registrationButtonSubject.flatMap {
        return@flatMap authService.signUp(email.value!!, password.value!!, firstName.value!!, lastName.value!!, phone.value!!)
    }

    // Init
    init {
        val emailAndPassword = Observables.combineLatest(email, password)
        val firstAndLastNames = Observables.combineLatest(firstName, lastName)
        Observables.combineLatest(emailAndPassword, firstAndLastNames, phone)
            .map { it.first.first.isNotEmpty() && it.first.second.isNotEmpty() && it.second.first.isNotEmpty() && it.second.second.isNotEmpty() && it.third.isNotEmpty() }
            .subscribe(registrationButtonEnabled)
    }

}