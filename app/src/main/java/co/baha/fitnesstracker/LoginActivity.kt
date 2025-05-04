package co.baha.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.baha.fitnesstracker.model.SharedPrefHelper
import android.view.View

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPrefHelper: SharedPrefHelper
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegisterLink: Button
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPrefHelper = SharedPrefHelper(this)

        edtUsername = findViewById(R.id.edt_username)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btn_login)
        btnRegisterLink = findViewById(R.id.btn_register_link)
        btnLogout = findViewById(R.id.btn_logout)

        // Проверка состояния логина
        if (sharedPrefHelper.getLoginStatus()) {
            // Если пользователь авторизован, показываем кнопку выхода
            btnLogout.visibility = View.VISIBLE
            edtUsername.isEnabled = false // Блокируем поля ввода
            edtPassword.isEnabled = false
            btnLogin.isEnabled = false // Блокируем кнопку входа
        } else {
            // Если пользователь не авторизован, скрываем кнопку выхода
            btnLogout.visibility = View.GONE
        }

        // Обработчик клика по кнопке "Войти"
        btnLogin.setOnClickListener {
            val inputUsername = edtUsername.text.toString().trim()
            val inputPassword = edtPassword.text.toString().trim()

            val savedUsername = sharedPrefHelper.getUsername()
            val savedPassword = sharedPrefHelper.getPassword()

            if (inputUsername == savedUsername && inputPassword == savedPassword) {
                sharedPrefHelper.setLoginStatus(true)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        // Переход на экран регистрации
        btnRegisterLink.setOnClickListener {
            Log.d("LoginActivity", "Register button clicked")  // Лог для проверки
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


        // Обработчик кнопки "Выйти"
        btnLogout.setOnClickListener {
            sharedPrefHelper.setLoginStatus(false) // Устанавливаем статус "не авторизован"
            sharedPrefHelper.clearUserData() // Очищаем данные пользователя

            // Перенаправление на экран логина
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Закрываем текущую активность
        }
    }
}