package pt.amn.moveon.presentation.viewmodels.utils

import org.junit.Assert.*

import org.junit.Test
import pt.amn.moveon.presentation.viewmodels.utils.StatisticsSolver

class StatisticsSolverTest {

    private val testStatisticsSolver = StatisticsSolver.Base()

    @Test
    fun checkTheLevelOfTheTravelerWith5Countries() {
        checkTheLevelOfTheTraveler(1, 5)
    }

    @Test
    fun checkTheLevelOfTheTravelerWith0Countries() {
        checkTheLevelOfTheTraveler(0, 0)
    }

    @Test
    fun checkTheLevelOfTheTravelerWith1Countries() {
        checkTheLevelOfTheTraveler(1, 1)
    }

    @Test
    fun checkTheLevelOfTheTravelerWithNegativeQuantityOfCountries() {
        checkTheLevelOfTheTraveler(0, -15)
    }

    @Test
    fun checkTheMaxLevelOfTheTravelerWith500Countries() {
        checkTheLevelOfTheTraveler(20, 500)
    }

    @Test
    fun checkTheDozenLevelOfTheTraveler() {
        checkTheLevelOfTheTraveler(4, 30)
    }

    @Test
    fun checkThePercentOfTheWorldWith0Countries() {
        checkThePercentOfTheWorld(0.0, 0)
    }

    @Test
    fun checkThePercentOfTheWorldWith500Countries() {
        checkThePercentOfTheWorld(100.0, 500)
    }

    @Test
    fun checkThePercentOfTheWorldWithNegativeQuantityCountries() {
        checkThePercentOfTheWorld(0.0, -15)
    }

    @Test
    fun checkThePercentOfTheWorldWith25Countries() {
        checkThePercentOfTheWorld(12.820512820512821, 25)
    }

    private fun checkTheLevelOfTheTraveler(expected: Int, countVisitedCountries: Int) {
        val actual = testStatisticsSolver.getLevelOfTraveler(countVisitedCountries)
        assertEquals(expected, actual)
    }

    private fun checkThePercentOfTheWorld(expected: Double, countVisitedCountries: Int) {
        val actual = testStatisticsSolver.getPercentOfTheWorld(countVisitedCountries)
        assertEquals(expected, actual, 0.0)
    }
}