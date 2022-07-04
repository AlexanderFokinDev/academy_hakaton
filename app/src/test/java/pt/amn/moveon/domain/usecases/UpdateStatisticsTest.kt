package pt.amn.moveon.domain.usecases

import org.junit.Assert.*
import org.junit.Test

class UpdateStatisticsTest {

    private val updateStatisticsTest = UpdateStatistics.Base(StatisticsSolver.Base())

    @Test
    fun checkUpdateStatisticsGetMethod() {
        val statData = updateStatisticsTest.getStatisticsOfTraveler(12, 25)
        assertEquals(2, statData.level)
        assertEquals(12, statData.countVisitedCountries)
        assertEquals(25, statData.countVisitedPlaces)
        assertEquals(6.153846153846154, statData.percentWorld, 0.0)
    }

}