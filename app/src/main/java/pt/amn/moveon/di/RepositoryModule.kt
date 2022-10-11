package pt.amn.moveon.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.amn.moveon.data.repositories.BackupRepositoryImpl
import pt.amn.moveon.data.repositories.MoveOnRepositoryImpl
import pt.amn.moveon.domain.repositories.BackupRepository
import pt.amn.moveon.domain.repositories.MoveOnRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepositoryMoveOn(moveOnRepositoryImpl: MoveOnRepositoryImpl) : MoveOnRepository

    @Binds
    @Singleton
    abstract fun bindRepositoryBackup(backupRepositoryImpl: BackupRepositoryImpl) : BackupRepository

}