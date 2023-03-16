package maxmudov.farrux.mytaxi.di.module

import dagger.Module
import dagger.Provides
import maxmudov.farrux.mytaxi.data.repository.MainRepositoryImpl
import maxmudov.farrux.mytaxi.database.InputDao
import maxmudov.farrux.mytaxi.domain.repository.MainRepository
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(inputDao: InputDao): MainRepository {
        return MainRepositoryImpl(inputDao)
    }

}