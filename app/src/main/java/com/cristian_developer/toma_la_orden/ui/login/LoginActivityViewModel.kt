package com.cristian_developer.toma_la_orden.ui.login

import android.arch.lifecycle.ViewModel
import com.cristian_developer.toma_la_orden.data.model.User
import com.cristian_developer.toma_la_orden.data.model.UserLogin
import com.cristian_developer.toma_la_orden.data.net.RetrofitSingleton
import com.cristian_developer.toma_la_orden.util.applySchedulers
import com.cristian_developer.toma_la_orden.util.validateResponse

class LoginActivityViewModel: ViewModel() {

    private val net = RetrofitSingleton.getLoginService()

    fun login(userLogin: UserLogin) =
            net.login(userLogin)
                .flatMap { validateResponse(it) }
                .applySchedulers()

    fun signup(user:User) =
            net.signin(user)
                .flatMap { validateResponse(it) }
                .applySchedulers()

}