package co.baha.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.baha.fitnesstracker.model.SharedPrefHelper

class MainActivity : AppCompatActivity() {

    private lateinit var rvMain: RecyclerView
    private lateinit var sharedPrefHelper: SharedPrefHelper
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация SharedPrefHelper
        sharedPrefHelper = SharedPrefHelper(this)

        // Проверка состояния авторизации
        checkLoginStatus()

        // Инициализация кнопки выхода
        btnLogout = findViewById(R.id.btn_logout)
        btnLogout.setOnClickListener {
            // Сброс состояния авторизации
            sharedPrefHelper.setLoginStatus(false)

            // Очистка данных пользователя (если нужно)
            sharedPrefHelper.clearUserData()

            // Переход на экран логина
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Закрыть текущую активность
        }

        // Добавление элементов для RecyclerView
        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.imc,
                textStringId = R.string.label_imc,
                color = R.color.color4
            )
        )
        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.ndc,
                textStringId = R.string.label_ndc,
                color = R.color.color4
            )
        )
        mainItems.add(
            MainItem(
                id = 3,
                drawableId = R.drawable.ic_water,
                textStringId = R.string.label_water_tracker,
                color = R.color.color4
            )
        )
        mainItems.add(
            MainItem(
                id = 4,
                drawableId = R.drawable.check_icon,
                textStringId = R.string.label_exercise_tracker,
                color = R.color.color4
            )
        )

        val adapter = MainAdapter(mainItems) { id ->
            when (id) {
                1 -> {
                    val intent = Intent(this@MainActivity, ImcActivity::class.java)
                    startActivity(intent)
                }
                2 -> {
                    val intent = Intent(this@MainActivity, NdcActivity::class.java)
                    startActivity(intent)
                }
                3 -> {
                    val intent = Intent(this@MainActivity, WaterTrackerActivity::class.java)
                    startActivity(intent)
                }
                4 -> {
                    val intent = Intent(this@MainActivity, ExerciseTrackerActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this, 2)

    }

    private fun checkLoginStatus() {
        val isLoggedIn = sharedPrefHelper.getLoginStatus()

        // Если пользователь не авторизован, показываем сообщение и направляем на экран логина
        if (!isLoggedIn) {
            Toast.makeText(this, "Вы не авторизованы! Пожалуйста, войдите в систему.", Toast.LENGTH_SHORT).show()

            // Открыть экран логина
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Закрыть текущую активность, чтобы пользователь не мог вернуться
        }
    }

    private inner class MainAdapter(
        private val mainItems: List<MainItem>,
        private val onItemClickListener: (Int) -> Unit,
    ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mainItems.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)
        }

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem) {
                val container: LinearLayout = itemView.findViewById(R.id.item_container)
                val img: ImageView = itemView.findViewById(R.id.item_img_icon)
                val name: TextView = itemView.findViewById(R.id.item_txt_name)

                container.setBackgroundResource(item.color)
                img.setImageResource(item.drawableId)
                name.setText(item.textStringId)

                container.setOnClickListener {
                    onItemClickListener.invoke(item.id)
                }
            }
        }
    }
}