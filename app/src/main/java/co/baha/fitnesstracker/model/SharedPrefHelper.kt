package co.baha.fitnesstracker.model

import android.content.Context
import android.content.SharedPreferences

class SharedPrefHelper(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("FitnessAppPrefs", Context.MODE_PRIVATE)

    // Получение статуса авторизации
    fun getLoginStatus(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    // Установка статуса авторизации
    fun setLoginStatus(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }

    // Сохранение логина и пароля
    fun saveUserCredentials(username: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }

    // Получение логина
    fun getUsername(): String? {
        return sharedPreferences.getString("username", null)
    }

    // Получение пароля
    fun getPassword(): String? {
        return sharedPreferences.getString("password", null)
    }

    // Очистка данных пользователя
    fun clearUserData() {
        val editor = sharedPreferences.edit()
        editor.remove("username")
        editor.remove("password")
        editor.remove("isLoggedIn")
        editor.apply()
    }
}