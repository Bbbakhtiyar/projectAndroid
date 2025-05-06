package co.baha.fitnesstracker

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.baha.fitnesstracker.model.ExerciseItem

class ExerciseTrackerActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExerciseAdapter
    private lateinit var resetButton: Button

    private val exercises = mutableListOf(
        ExerciseItem("Бег"),
        ExerciseItem("Разминка"),
        ExerciseItem("Жим лежа"),
        ExerciseItem("Жим ногами"),
        ExerciseItem("Вертикальная тяга"),
        ExerciseItem("Отжимания"),
        ExerciseItem("Приседания"),
        ExerciseItem("Планка"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_tracker)

        recyclerView = findViewById(R.id.exercise_recycler)
        resetButton = findViewById(R.id.btn_reset)

        adapter = ExerciseAdapter(exercises)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        resetButton.setOnClickListener {
            // Обнуляем состояние всех чекбоксов
            exercises.forEach { it.isDone = false }
            adapter.notifyDataSetChanged()
        }
    }
}