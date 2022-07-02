package pt.amn.moveon.domain.usecases

import pt.amn.moveon.common.COUNT_COUNTRIES_IN_THE_WORLD

interface StatisticsSolver {

    fun getPercentOfTheWorld(countVisitedCountries: Int): Double
    fun getLevelOfTraveler(countVisitedCountries: Int): Int

    class Base : StatisticsSolver {
        override fun getPercentOfTheWorld(countVisitedCountries: Int): Double =
            if (countVisitedCountries <= 0) 0.0
            else if (countVisitedCountries >= COUNT_COUNTRIES_IN_THE_WORLD) 100.0
            else countVisitedCountries / (COUNT_COUNTRIES_IN_THE_WORLD / 100.0)

        override fun getLevelOfTraveler(countVisitedCountries: Int): Int =
            if (countVisitedCountries <= 0) 0
            else if (countVisitedCountries > COUNT_COUNTRIES_IN_THE_WORLD) 20
            else countVisitedCountries / 10 + 1
    }

}