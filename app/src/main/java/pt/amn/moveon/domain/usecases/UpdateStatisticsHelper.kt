package pt.amn.moveon.domain.usecases

import android.content.Context
import pt.amn.moveon.R

interface UpdateStatistics {

    fun getStatisticsOfTraveler(
        countVisitedCountries: Int,
        countVisitedPlaces: Int
    ): StatisticsOfTraveler

    class Base(private val statisticsSolver: StatisticsSolver) : UpdateStatistics {

        override fun getStatisticsOfTraveler(
            countVisitedCountries: Int,
            countVisitedPlaces: Int
        ): StatisticsOfTraveler =

            StatisticsOfTraveler(
                statisticsSolver.getLevelOfTraveler(countVisitedCountries),
                statisticsSolver.getPercentOfTheWorld(countVisitedCountries),
                countVisitedCountries,
                countVisitedPlaces
            )

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