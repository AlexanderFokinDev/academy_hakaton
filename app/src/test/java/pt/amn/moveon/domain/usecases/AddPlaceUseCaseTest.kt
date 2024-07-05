package pt.amn.moveon.domain.usecases

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import pt.amn.moveon.domain.models.MoveOnPlace
import pt.amn.moveon.domain.models.UseCaseResult
import pt.amn.moveon.domain.repositories.MoveOnRepository

internal class AddPlaceUseCaseTest {

    private val repository: MoveOnRepository = mock(MoveOnRepository::class.java)

    @Test
    fun `Test 1`() = runBlocking {

        val expected = UseCaseResult<Boolean>(isError = false, data = true, description = "")

        val place = MoveOnPlace(
            id = "1000",
            name = "The best place",
            latitude = 1.0,
            longitude = 1.0,
            country_id = 1
        )

        val testResultUseCase = UseCaseResult<Boolean>(isError = false, data = true, description = "")
        whenever(repository.addPlace(place)).thenReturn(testResultUseCase)

        val actual = AddPlaceUseCase(repository).execute(place)

        assertEquals(expected, actual)
    }

}