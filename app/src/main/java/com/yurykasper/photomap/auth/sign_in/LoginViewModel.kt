package com.yurykasper.photomap.auth.sign_in

import androidx.lifecycle.ViewModel
import com.yurykasper.photomap.services.authService.AuthService
import com.yurykasper.photomap.services.authService.AuthServiceType
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.BehaviorSubject

class LoginViewModel: ViewModel(), LoginViewModelType {

    override val inputs: LoginViewModelInput = this
    override val outputs: LoginViewModelOutput = this

    private val authService: AuthServiceType = AuthService()

    // Inputs
    private val email = BehaviorSubject.create<String>()
    override fun emailChanged(text: String) = email.onNext(text)

    private val password = BehaviorSubject.create<String>()
    override fun passwordChanged(text: String) = password.onNext(text)

    private val loginButtonSubject: PublishSubject<Unit> = PublishSubject.create()
    override fun loginButtonPressed() = loginButtonSubject.onNext(Unit)

    // Outputs
    override val loginButtonEnabled: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    override var showMainFragment: Observable<Boolean> = loginButtonSubject.flatMap {
        return@flatMap authService.signIn(email.value!!, password.value!!)
    }

    // Init
    init {
        Observables.combineLatest(email, password)
            .map { (email, password) -> email.isNotEmpty() && password.isNotEmpty() }
            .subscribe(loginButtonEnabled)
    }

}