package maxmudov.farrux.mytaxi

import android.app.Application
import android.content.res.Resources
import maxmudov.farrux.mytaxi.di.component.AppComponent
import maxmudov.farrux.mytaxi.di.component.DaggerAppComponent
import maxmudov.farrux.mytaxi.di.module.DatabaseModule
import maxmudov.farrux.mytaxi.di.module.RepositoryModule

class App:Application() {

    companion object{
        lateinit var resources: Resources
        lateinit var appComponent: AppComponent
    }
    override fun onCreate() {
        super.onCreate()
        Companion.resources = resources
        appComponent = DaggerAppComponent
            .builder()
            .databaseModule(DatabaseModule(this))
            .repositoryModule(RepositoryModule())
            .build()
    }
}