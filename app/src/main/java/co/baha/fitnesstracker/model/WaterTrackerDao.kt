package co.baha.fitnesstracker.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WaterTrackerDao {

    @Insert
    fun insertWater(waterTracker: WaterTracker)

    @Query("SELECT * FROM WaterTracker ORDER BY date DESC")
    fun getAllWaterRecords(): List<WaterTracker>



    @Delete
    fun deleteWaterRecord(waterTracker: WaterTracker)

}