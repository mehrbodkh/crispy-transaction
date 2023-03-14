fun main() {
    transaction(mapOf())
}

fun transaction(map: Map<String, String>): Map<String, String>? {
    var internalMap = map.toMutableMap()
    while (true) {
        print("> ")
        val (commandInput, key, value) = readCommand()

        when (commandInput.toCommand()) {
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
