package maxmudov.farrux.mytaxi.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import maxmudov.farrux.mytaxi.database.AppDatabase
import maxmudov.farrux.mytaxi.database.InputDao
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "my_taxi")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUssdDao(appDatabase: AppDatabase): InputDao = appDatabase.inputDao()


}