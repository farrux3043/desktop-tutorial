package maxmudov.farrux.mytaxi.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="results")
data class MyLocationModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val longtitute:Double,
    val latitute:Double
)
