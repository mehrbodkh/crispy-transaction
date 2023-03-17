import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(
    MockKExtension::class
)
class DataStoreTest {
    @SpyK
    private var map: MutableMap<String, String> = mutableMapOf()

    @InjectMockKs
    private lateinit var dataStore: DataStore<String, String>

    @Test
    fun `should call put`() {
        dataStore.put("test", "test")
        verify { map["test"] = "test" }
    }

    @Test
    fun `should call get`() {
        dataStore["test"]
        verify { map["test"] }
    }

    @Test
    fun `should call remove`() {
        dataStore.remove("test")
        verify { map.remove("test") }
    }

    @Test
    fun `should copy the object`() {
        dataStore.put("test", "test")
        val copy = dataStore.copy()
        assert(copy != dataStore)
        assert(copy["test"] == dataStore["test"])
    }

    @Test
    fun `should count instances`() {
        with(dataStore) {
            put("test", "test")
            put("test-2", "test")
        }
        val result = dataStore.count("test")
        assert(result == 2)
    }

}