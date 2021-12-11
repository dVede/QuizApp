package com.example.quizapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.*
import com.example.quizapp.databinding.ActivityLoginBinding
import com.example.quizapp.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPrefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //isTokenExpired() TODO(check token expiration)
        init()
    }

    private fun init() {
        sharedPrefManager = SharedPrefManager.getInstance(this)
        binding.loginButton.setOnClickListener {
            login()
        }
        binding.registerButton.setOnClickListener {
            Intent(this@LoginActivity, RegisterActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun login() {
        Timber.i("Start login")
        val login = binding.edLogin.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()
        if (login.isEmpty() || password.isEmpty() || login.length < 6 || password.length < 6) {
            validateData(login, password)
            return
        }
        val api = RetrofitClient.getAuthService().login(login, password)
        api.enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val loginResponse = response.body()!!
                if (!loginResponse.error) {
                    SharedPrefManager.getInstance(applicationContext).saveUser(loginResponse.user)
                    Intent(this@LoginActivity, MainActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                }
                else {
                    Toast.makeText(
                        this@LoginActivity,
                        loginResponse.msg,
                        Toast.LENGTH_SHORT).show()
                    Timber.e("Something went wrong")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.printStackTrace()
                TODO("NetworkErrors")
            }
        })
    }

    private fun validateData(login: String, password: String) {
        if (login.length < 6) binding.edLogin.error = resources.getString(R.string.characters6)
        if (password.length < 6) binding.edPassword.error = resources.getString(R.string.characters6)
        if (login.isBlank()) binding.edLogin.error = resources.getString(R.string.empty)
        if (password.isBlank()) binding.edPassword.error = resources.getString(R.string.empty)
    }

    private fun isTokenExpired() {
        if (sharedPrefManager.isLoggedIn) {
            val t: String = sharedPrefManager.user.token!!
            val arr = Utils.decodeJWT(t)
                .replace(Utils.RIGHT_BRACE, Utils.EMPTY_CHARACTER)
                .split(Utils.COLON_CHARACTER)
            val timeNow = System.currentTimeMillis() / 1000
            if (timeNow < arr[3].toLong()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else sharedPrefManager.clear()
        }
    }
}