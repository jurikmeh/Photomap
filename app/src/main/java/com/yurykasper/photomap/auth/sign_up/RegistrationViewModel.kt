package com.yurykasper.photomap.auth.sign_up

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
class RegistrationViewModel: ViewModel(), RegistrationViewModelType {

    override val inputs: RegistrationViewModelInput = this
    override val outputs: RegistrationViewModelOutput = this

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

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

    override fun registrationButtonPressed() {
        val emailValue = email.value
        val passwordValue = password.value
        if (emailValue != null && passwordValue != null) {
            registerUserWith(emailValue, passwordValue)
        }
    }

    // Outputs
    override val registrationButtonEnabled: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    // Init
    init {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val emailAndPassword = Observables.combineLatest(email, password)
        val firstAndLastNames = Observables.combineLatest(firstName, lastName)
        Observables.combineLatest(emailAndPassword, firstAndLastNames, phone)
            .map { it.first.first.isNotEmpty() && it.first.second.isNotEmpty() && it.second.first.isNotEmpty() && it.second.second.isNotEmpty() && it.third.isNotEmpty() }
            .subscribe(registrationButtonEnabled)
    }

    private fun registerUserWith(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnFailureListener { println("failed, reason: ${it.localizedMessage}") }
            .addOnSuccessListener {
                val user = hashMapOf(
                    "email" to email,
                    "password" to password,
                    "firstname" to firstName.value,
                    "lastname" to lastName.value,
                    "phone" to "+${phone.value}"
                )
                db.collection("users").add(user)
            }
    }

}