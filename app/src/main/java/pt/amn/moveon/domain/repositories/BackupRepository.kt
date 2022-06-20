package pt.amn.moveon.domain.repositories

interface BackupRepository {

    suspend fun getBackupDataInJson(): String

}