package maxmudov.farrux.mytaxi.data.mapper

import maxmudov.farrux.mytaxi.data.remote.dto.ModelData
import maxmudov.farrux.mytaxi.domain.model.MyLocationModel

fun ModelData.toModel():MyLocationModel{
    return MyLocationModel(id, longtitute, latitute)
}


fun MyLocationModel.toModel():ModelData{
    return ModelData(id, longtitute, latitute)
}
