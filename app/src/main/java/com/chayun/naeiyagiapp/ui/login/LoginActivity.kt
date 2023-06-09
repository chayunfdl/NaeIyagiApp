package com.chayun.naeiyagiapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chayun.naeiyagiapp.data.model.UserModel
import com.chayun.naeiyagiapp.databinding.ActivityLoginBinding
import com.chayun.naeiyagiapp.ui.ViewModelFactory
import com.chayun.naeiyagiapp.ui.iyagi.IyagiActivity
import com.chayun.naeiyagiapp.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var factory: ViewModelFactory
    private val loginViewModel: LoginViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        factory = ViewModelFactory.getInstance(this)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupAction()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            when {
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Insert your email"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Insert your password"
                }
                else -> {
                    loginViewModel.postLoginData(
                        binding.edLoginEmail.text.toString(),
                        binding.edLoginPassword.text.toString()
                    )
                    loginViewModel.login.observe(this@LoginActivity) {
                            response -> loginViewModel.postUser(
                        UserModel(
                            response.loginResult.name,
                            "Bearer " + (response.loginResult.token),
                            true
                        )
                    )
                    }
                    loginViewModel.userLogin()
                    loginViewModel.login.observe(this@LoginActivity) {
                            response -> if (!response.error) {
                        startActivity(Intent(this@LoginActivity, IyagiActivity::class.java))
                    }
                    }
                }
            }
        }

        binding.toRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}