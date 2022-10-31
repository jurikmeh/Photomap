package com.yurykasper.photomap.services.authService

import io.reactivex.Observable

interface AuthServiceType {
    fun signIn(email: String, password: String): Observable<Boolean>
    fun signUp(email: String, password: String, firstname: String, lastname: String ,phone: String): Observable<Boolean>
    fun logout()
}