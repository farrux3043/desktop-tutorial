package maxmudov.farrux.mytaxi.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import maxmudov.farrux.mytaxi.data.mapper.toModel
import maxmudov.farrux.mytaxi.database.InputDao
import maxmudov.farrux.mytaxi.domain.model.MyLocationModel
import maxmudov.farrux.mytaxi.domain.repository.MainRepository
import maxmudov.farrux.mytaxi.utils.Resource
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val inputDao: InputDao
) : MainRepository {
    override suspend fun addDbList(model: MyLocationModel) {
        inputDao.insert(model.toModel())
    }

    override  fun getMyLocation(): Flow<Resource<List<MyLocationModel>>> = flow {
        emit(Resource.Success(inputDao.getAll().map { it.toModel() }))
    }


}