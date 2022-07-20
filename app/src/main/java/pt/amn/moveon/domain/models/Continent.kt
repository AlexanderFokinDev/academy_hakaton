package pt.amn.moveon.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Continent(
    val id: Int,
    val nameEn: String,
    val nameRu: String
) : Parcelable {

    fun getLocalName(): String =
        when (Locale.getDefault().language) {
            "ru" -> nameRu
            else -> nameEn
        }

}