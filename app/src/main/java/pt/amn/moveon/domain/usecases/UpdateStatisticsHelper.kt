package pt.amn.moveon.domain.usecases

import android.content.Context
import pt.amn.moveon.R

interface UpdateStatistics {

    fun getStatisticsOfTraveler(level: Int,
                                percentWorld: Double,
                                countVisitedCountries: Int,
                                countVisitedPlaces: Int) : StatisticsOfTraveler

    class Base() : UpdateStatistics {
        override fun getStatisticsOfTraveler(level: Int,
                                             percentWorld: Double,
                                             countVisitedCountries: Int,
                                             countVisitedPlaces: Int) : StatisticsOfTraveler {

            return StatisticsOfTraveler(
                level,
                percentWorld,
                countVisitedCountries,
                countVisitedPlaces
            )
        }
    }
}

data class StatisticsOfTraveler(
    val level: Int,
    val percentWorld: Double,
    val countVisitedCountries: Int,
    val countVisitedPlaces: Int
) {
    fun convertToStatisticsOfTravelerText(context: Context) : StatisticsOfTravelerText {
        return StatisticsOfTravelerText(
            String.format(context.getString(R.string.statistics_level), level),
            String.format(context.getString(R.string.statistics_result2), percentWorld),
            String.format(context.getString(R.string.statistics_result1), countVisitedCountries),
            String.format(context.getString(R.string.statistics_result3), countVisitedPlaces),
            level
        )
    }
}

data class StatisticsOfTravelerText(
    val levelText: String,
    val percentText: String,
    val countriesText: String,
    val placesText: String,
    val progress: Int
)