package com.example.quizapp

import android.content.Context
import android.content.SharedPreferences

import com.example.quizapp.model.User


class SharedPrefManager private constructor(private val ctx: Context) {

    fun saveUser(user: User) {
        val sharedPreferences: SharedPreferences = ctx
            .getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("id", user.id)
        editor.putString("login", user.login)
        editor.putString("token", user.token)
        editor.apply()
    }

    val user: User
        get() {
            val sharedPreferences: SharedPreferences = ctx
                .getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("login", null),
                sharedPreferences.getString("token", null)
            )
        }

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences: SharedPreferences =
                ctx.getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString("token", null) != null
        }

    fun clear() {
        val sharedPreferences: SharedPreferences =
            ctx.getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private var mInstance: SharedPrefManager? = null

        @Synchronized
        fun getInstance(ctx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(ctx)
            }
            return mInstance as SharedPrefManager
        }
    }

}