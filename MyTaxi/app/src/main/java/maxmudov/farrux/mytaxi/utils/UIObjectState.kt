package maxmudov.farrux.mytaxi.utils

data class UIObjectState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String = ""
)