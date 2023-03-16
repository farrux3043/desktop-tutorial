package maxmudov.farrux.mytaxi.domain.repository

import kotlinx.coroutines.flow.Flow
import maxmudov.farrux.mytaxi.domain.model.MyLocationModel
import maxmudov.farrux.mytaxi.utils.Resource


interface MainRepository {


    suspend fun addDbList(model: MyLocationModel)

     fun getMyLocation(): Flow<Resource<List<MyLocationModel>>>


}