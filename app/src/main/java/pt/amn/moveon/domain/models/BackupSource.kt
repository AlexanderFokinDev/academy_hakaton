package pt.amn.moveon.domain.models

interface BackupSource {

    suspend fun save(): Boolean

    suspend fun load(): Boolean

}