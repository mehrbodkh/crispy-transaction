fun main() {
    transaction(mapOf())
}

enum class Command(val value: String) {
    SET("set"),
    GET("get"),
    DELETE("delete"),
    COUNT("count"),
    BEGIN("begin"),
    ROLLBACK("rollback"),
    COMMIT("commit"),
    UNKNOWN("unknown"),
}

fun String.toCommand(): Command = when (this.lowercase()) {
    Command.SET.value -> Command.SET
    Command.GET.value -> Command.GET
    Command.DELETE.value -> Command.DELETE
    Command.COUNT.value -> Command.COUNT
    Command.BEGIN.value -> Command.BEGIN
    Command.ROLLBACK.value -> Command.ROLLBACK
    Command.COMMIT.value -> Command.COMMIT
    else -> Command.UNKNOWN
}

fun transaction(map: Map<String, String>): Map<String, String>? {
    var internalMap = map.toMutableMap()
    while (true) {
        print("> ")
        readlnOrNull()?.let { input ->
            val (command, key, value) = input.split(' ') + listOf("", "")

            when (command.toCommand()) {
                Command.SET -> internalMap[key] = value
                Command.GET -> println(internalMap[key] ?: "key not set")
                Command.DELETE -> internalMap.remove(key)
                Command.COUNT -> println(internalMap.count { it.value == value })
                Command.BEGIN -> transaction(internalMap)?.let { internalMap = it.toMutableMap() }
                Command.ROLLBACK -> return null
                Command.COMMIT -> return internalMap
                Command.UNKNOWN -> println("incorrect input")
            }
        }
    }
}
