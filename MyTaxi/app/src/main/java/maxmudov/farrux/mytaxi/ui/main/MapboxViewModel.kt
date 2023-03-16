package maxmudov.farrux.mytaxi.ui.main

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import maxmudov.farrux.mytaxi.domain.model.MyLocationModel
import maxmudov.farrux.mytaxi.domain.repository.MainRepository
import maxmudov.farrux.mytaxi.ui.BaseViewModel
import maxmudov.farrux.mytaxi.utils.Resource
import javax.inject.Inject


class MapboxViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    suspend fun addLocation(locationModel: MyLocationModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDbList(locationModel)
        }
    }

    fun getAllLocation(): Flow<Resource<List<MyLocationModel>>> {
        return repository.getMyLocation()
    }


}