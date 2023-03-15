fun main() {
    transaction(DataStore())
}

fun transaction(map: KeyValueStore<String, String>): KeyValueStore<String, String>? {
    var internalMap = map.copy()
    while (true) {
        print("> ")
        val (commandInput, key, value) = readCommand()

        when (commandInput.toCommand()) {
            Command.SET -> internalMap.put(key, value)
            Command.GET -> println(internalMap[key] ?: "key not set")
            Command.DELETE -> internalMap.remove(key)
            Command.COUNT -> println(internalMap.count { it == value })
            Command.BEGIN -> transaction(internalMap)?.let { internalMap = it.copy() }
            Command.ROLLBACK -> return null
            Command.COMMIT -> return internalMap
            Command.UNKNOWN -> println("incorrect input")
        }
    }
}
