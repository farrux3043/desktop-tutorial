package maxmudov.farrux.mytaxi.data.remote.dto

data class MainResponse<T>(
    val data: T,
    val code: Int,
    val message: String,
    val success: Boolean,
    val statusCode: Int,
    val time: String,
)