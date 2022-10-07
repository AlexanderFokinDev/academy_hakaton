package pt.amn.moveon.data.repositories

import pt.amn.moveon.domain.models.BackupSource
import pt.amn.moveon.domain.repositories.BackupRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupRepositoryImpl @Inject constructor(): BackupRepository {

    override suspend fun saveBackup(source: BackupSource): Boolean {
        return source.save()
    }

    override suspend fun loadDataFromBackup(source: BackupSource): Boolean {
        return source.load()
    }
}