package maxmudov.farrux.mytaxi.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="results")
data class ModelData(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val longtitute:Double,
    val latitute:Double
)
