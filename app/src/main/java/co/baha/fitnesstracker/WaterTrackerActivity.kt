package co.baha.fitnesstracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.baha.fitnesstracker.model.AppDatabase
import co.baha.fitnesstracker.model.WaterTracker
import kotlinx.coroutines.launch

class WaterTrackerActivity : AppCompatActivity() {

    private lateinit var editWaterAmount: EditText
    private lateinit var btnAddWater: Button
    private lateinit var db: AppDatabase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_tracker)

        db = AppDatabase.getDatabase(applicationContext)

        editWaterAmount = findViewById(R.id.edit_water_amount)
        btnAddWater = findViewById(R.id.btn_add_water)

        btnAddWater.setOnClickListener {
            val amount = editWaterAmount.text.toString().toDoubleOrNull()
            if (amount != null && amount > 0) {
                val waterRecord = WaterTracker(amount = amount)
                lifecycleScope.launch {
                    db.waterTrackerDao().insertWater(waterRecord)
                    Toast.makeText(this@WaterTrackerActivity, "Water added!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

}