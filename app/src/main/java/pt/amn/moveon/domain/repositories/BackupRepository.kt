package pt.amn.moveon.domain.repositories

interface BackupRepository {

    fun getBackupDataInJson(): String

}