package maxmudov.farrux.mytaxi.utils

data class UIListState<T>(
    val isLoading: Boolean = false,
    val data: List<T>? = null,
    val error: String = ""
)