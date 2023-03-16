package maxmudov.farrux.mytaxi

import android.app.Application
import android.content.res.Resources
import maxmudov.farrux.mytaxi.database.AppDatabase
import maxmudov.farrux.mytaxi.di.component.AppComponent
import maxmudov.farrux.mytaxi.di.component.DaggerAppComponent
import maxmudov.farrux.mytaxi.di.module.DatabaseModule
import maxmudov.farrux.mytaxi.di.module.NetworkModule
import maxmudov.farrux.mytaxi.di.module.RepositoryModule
import maxmudov.farrux.mytaxi.di.module.ApiServiceModule

class App:Application() {

    companion object{
        lateinit var resources: Resources
        lateinit var appComponent: AppComponent
    }
    override fun onCreate() {
        super.onCreate()
//        AppDatabase.initDataBase(this)
//        Plugin.Mapbox.getInstance(this,getString(R.string.mapbox_access_token))
        Companion.resources = resources
        appComponent = DaggerAppComponent
            .builder()
            .databaseModule(DatabaseModule(this))
            .repositoryModule(RepositoryModule())
            .build()
    }
}