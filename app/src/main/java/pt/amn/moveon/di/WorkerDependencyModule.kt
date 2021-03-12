package pt.amn.moveon.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.amn.moveon.data.local.AppDatabase
import pt.amn.moveon.workers.WorkerDependency
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class WorkerDependencyModule {

    @Singleton
    @Provides
    fun provideWorkerDependency(database: AppDatabase)
            : WorkerDependency {
        return WorkerDependency(database)
    }

}