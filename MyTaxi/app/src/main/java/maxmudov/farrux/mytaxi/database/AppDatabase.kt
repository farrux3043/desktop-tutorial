package maxmudov.farrux.mytaxi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import maxmudov.farrux.mytaxi.data.remote.dto.ModelData

@Database(entities = [ModelData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inputDao(): InputDao
}