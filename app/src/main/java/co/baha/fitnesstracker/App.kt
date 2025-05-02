package co.baha.fitnesstracker

import android.app.Application
import co.baha.fitnesstracker.model.AppDatabase

class App : Application() {

    lateinit var  db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }

}