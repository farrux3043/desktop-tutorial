package maxmudov.farrux.mytaxi.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import maxmudov.farrux.mytaxi.data.remote.dto.ModelData
import maxmudov.farrux.mytaxi.domain.model.MyLocationModel

@Dao
interface InputDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ModelData)

     @Query("SELECT * FROM results")
   suspend  fun getAll():List<ModelData>



}