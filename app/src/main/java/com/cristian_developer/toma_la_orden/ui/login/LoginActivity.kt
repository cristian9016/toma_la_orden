package com.cristian_developer.toma_la_orden.ui.login

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.model.User
import com.cristian_developer.toma_la_orden.data.model.UserLogin
import com.cristian_developer.toma_la_orden.data.preferences.UserSession
import com.cristian_developer.toma_la_orden.ui.main.MainActivity
import com.cristian_developer.toma_la_orden.util.*
import com.jakewharton.rxbinding3.view.clicks
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    private val dis = LifeDisposable(this)
    private val viewModel: LoginActivityViewModel
            by lazy { buildViewModel<LoginActivityViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (UserSession.token != "") {
            startActivity<MainActivity>()
            finish()
        }
        dis add RxPermissions(this)
            .request(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            .subscribe()
    }

    @SuppressLint("CheckResult")
    override fun onResume() {
        super.onResume()

        tvSignUp.clicks()
            .subscribe {
                tvSignUp.gone()
                signupGroup.visible()
                btnLogin.gone()
                etPassword.text.clear()
                etName.text.clear()
                etCellphone.text.clear()
            }

        btnCancel.clicks()
            .subscribe {
                tvSignUp.visible()
                signupGroup.gone()
                btnLogin.visible()
                etPassword.text.clear()
                etName.text.clear()
                etCellphone.text.clear()
            }
        btnSignUp.setOnClickListener {
            dis add validateForm(
                R.string.empty_fields,
                etName.toText(),
                etCellphone.toText(),
                etEmail.toText(),
                etPassword.toText()
            )
                .flatMap {
                    loader.visible()
                    signupGroup.isEnabled = false
                    viewModel.signup(User(it[0], it[1], it[2], it[3]))
                }
                .subscribe(
                    {
                        toast("Se Registro Correctamente")
                        tvSignUp.visible()
                        btnLogin.visible()
                        signupGroup.gone()
                        etPassword.text.clear()
                        etName.text.clear()
                        etCellphone.text.clear()
                        loader.gone()
                    },
                    {
                        toast("El correo ya esta registrado.")
                        signupGroup.isEnabled = true
                        loader.gone()
                    },
                    {
                        toast("Error Interno.")
                    }
                )
        }

        btnLogin.setOnClickListener {
            dis add validateForm(R.string.empty_fields, etEmail.toText(), etPassword.toText())
                .flatMap {
                    loader.visible()
                    loginGroupEnabled(false)
                    viewModel.login(UserLogin(it[0], it[1]))
                }
                .subscribe(
                    {
                        startActivity<MainActivity>()
                        UserSession.token = it
                        loader.gone()
                        loginGroupEnabled(true)
                        finish()
                    },
                    {
                        toast(it.message!!)
                        loader.gone()
                        loginGroupEnabled(true)
                    },
                    {
                        loader.gone()
                        toast("Usuario o contraseña invalidos.")
                        loginGroupEnabled(true)
                    }
                )
        }

    }

    fun loginGroupEnabled(show: Boolean) {
        etEmail.isEnabled = show
        etPassword.isEnabled = show
        btnLogin.isEnabled = show
    }
}
