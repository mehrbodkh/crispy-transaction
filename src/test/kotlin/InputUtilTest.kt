import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InputUtilTest {

    @BeforeEach
    fun setup() {
        mockkStatic(::readlnOrNull)
    }

    @Test
    fun `should throw exception without input`() {
        every { readlnOrNull() } returns null
        assertThrows(Exception::class.java, ::readCommand, INCORRECT_INPUT)
    }

    @Test
    fun `should throw exception with wrong input`() {
        every { readlnOrNull() } returns "Test test test"
        assertThrows(Exception::class.java, ::readCommand, INCORRECT_INPUT)
    }

    @Test
    fun `should throw exception without sufficient arguments`() {
        every { readlnOrNull() } returns "SET 1"
        assertThrows(Exception::class.java, ::readCommand, INCORRECT_INPUT)

        every { readlnOrNull() } returns "GET"
        assertThrows(Exception::class.java, ::readCommand, INCORRECT_INPUT)

        every { readlnOrNull() } returns "DELETE"
        assertThrows(Exception::class.java, ::readCommand, INCORRECT_INPUT)

        every { readlnOrNull() } returns "COUNT"
        assertThrows(Exception::class.java, ::readCommand, INCORRECT_INPUT)
    }

    @Test
    fun `should trim input and return correct result`() {
        every { readlnOrNull() } returns "    SET x 1   "
        var result = readCommand()
        assert(result == listOf("SET", "x", "1", "", ""))

        every { readlnOrNull() } returns "    ROLLBACK   "
        result = readCommand()
        assert(result == listOf("ROLLBACK", "", ""))
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

}