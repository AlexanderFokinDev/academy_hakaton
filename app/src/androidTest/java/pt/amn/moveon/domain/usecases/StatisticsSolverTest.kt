package pt.amn.moveon.domain.usecases

import org.junit.Assert.*

import org.junit.Test

class StatisticsSolverTest {

    private val testStatisticsSolver = StatisticsSolver.Base()

    @Test
    fun checkTheLevelOfTheTravelerWith0Countries() {
        checkTheLevelOfTheTraveler(0, 0)
    }

    @Test
    fun checkTheLevelOfTheTravelerWith1Countries() {
        checkTheLevelOfTheTraveler(1, 1)
    }

    @Test
    fun checkTheLevelOfTheTravelerWith5Countries() {
        checkTheLevelOfTheTraveler(1, 5)
    }

    private fun checkTheLevelOfTheTraveler(expected: Int, countVisitedCountries: Int) {
        val actual = testStatisticsSolver.getLevelOfTraveler(countVisitedCountries)
        assertEquals(expected, actual)
    }
}