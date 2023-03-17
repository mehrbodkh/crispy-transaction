import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn
import org.junit.jupiter.api.Test

class MainTest {

    @Test
    fun `should show error without transaction for rollback`() {
        withTextFromSystemIn("ROLLBACK", "EXIT").execute {
            val output = tapSystemOut {
                main()
            }
            assert(
                output == """
                > no transaction
                > 
            """.trimIndent()
            )
        }
    }

    @Test
    fun `should show error without transaction for commit`() {
        withTextFromSystemIn("COMMIT", "EXIT").execute {
            val output = tapSystemOut {
                main()
            }
            assert(
                output == """
                > no transaction
                > 
            """.trimIndent()
            )
        }
    }

    @Test
    fun `should set a value correctly`() {
        withTextFromSystemIn("SET x 1", "GET x", "EXIT").execute {
            val output = tapSystemOut {
                main()
            }
            assert(
                output == """
                > > 1
                > 
            """.trimIndent()
            )
        }
    }

    @Test
    fun `should count a value correctly`() {
        withTextFromSystemIn("SET x 1", "SET y 1", "COUNT 1", "EXIT").execute {
            val output = tapSystemOut {
                main()
            }
            assert(
                output == """
                > > > 2
                > 
            """.trimIndent()
            )
        }
    }

    @Test
    fun `should delete a value`() {
        withTextFromSystemIn("SET x 1", "DELETE x", "GET x", "EXIT").execute {
            val output = tapSystemOut {
                main()
            }
            assert(
                output == """
                > > > key not set
                > 
            """.trimIndent()
            )
        }
    }

    @Test
    fun `should do a single transaction`() {
        withTextFromSystemIn("SET x 1", "BEGIN", "SET x 2", "COMMIT", "GET x", "EXIT").execute {
            val output = tapSystemOut {
                main()
            }
            assert(
                output == """
                > > > > > 2
                > 
            """.trimIndent()
            )
        }
    }

    @Test
    fun `should rollback a single transaction`() {
        withTextFromSystemIn("SET x 1", "BEGIN", "SET x 2", "ROLLBACK", "GET x", "EXIT").execute {
            val output = tapSystemOut {
                main()
            }
            assert(
                output == """
                > > > > > 1
                > 
            """.trimIndent()
            )
        }
    }

    @Test
    fun `should do a nested transaction`() {
        withTextFromSystemIn(
            "SET x 1",
            "BEGIN",
            "SET x 2",
            "BEGIN",
            "SET x 3",
            "GET x",
            "ROLLBACK",
            "COMMIT",
            "GET x",
            "EXIT"
        ).execute {
            val output = tapSystemOut {
                main()
            }
            println(output)
            assert(
                output == """
                > > > > > > 3
                > > > 2
                > 
            """.trimIndent()
            )
        }
    }
}
