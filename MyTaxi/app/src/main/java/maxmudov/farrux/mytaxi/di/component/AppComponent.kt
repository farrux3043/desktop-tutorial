package maxmudov.farrux.mytaxi.di.component

import dagger.Component
import maxmudov.farrux.mytaxi.di.module.DatabaseModule
import maxmudov.farrux.mytaxi.di.module.RepositoryModule
import maxmudov.farrux.mytaxi.ui.main.MapboxFragment
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        RepositoryModule::class
    ]
)

interface AppComponent {

    fun inject(mapboxFragment: MapboxFragment)


}