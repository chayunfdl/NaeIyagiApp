package com.chayun.naeiyagiapp.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chayun.naeiyagiapp.databinding.ActivityRegisterBinding
import com.chayun.naeiyagiapp.ui.ViewModelFactory
import com.chayun.naeiyagiapp.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var factory: ViewModelFactory
    private val registerViewModel: RegisterViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        factory = ViewModelFactory.getInstance(this)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupAction()
    }

    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            when {
                name.isEmpty() -> {
                    binding.nameEditTextLayout.error = "Insert your name"
                }
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Insert your email"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Insert your password"
                }
                else -> {
                    registerViewModel.postRegisterData(
                        binding.edRegisterName.text.toString(),
                        binding.edRegisterEmail.text.toString(),
                        binding.edRegisterPassword.text.toString()
                    )
                    registerViewModel.register.observe(this@RegisterActivity) {
                            response -> if (!response.error) {
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    }
                    }
                }
            }
        }

        binding.toLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}