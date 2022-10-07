package pt.amn.moveon.domain.repositories

import pt.amn.moveon.domain.models.BackupSource

interface BackupRepository {

    suspend fun saveBackup(source: BackupSource): Boolean

    suspend fun loadDataFromBackup(source: BackupSource) : Boolean

}