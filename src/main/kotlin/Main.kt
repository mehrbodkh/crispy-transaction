const val KEY_NOT_SET = "key not set"
const val INCORRECT_INPUT = "incorrect input"

fun main() {
    transaction(DataStore())
}

fun transaction(dataStore: KeyValueStore<String, String>, isTransaction: Boolean = false): KeyValueStore<String, String>? {
    var internalStore = dataStore.copy()
    while (true) {
        print("> ")
        try {
            val (commandInput, firstArgument, secondArgument) = readCommand()

            when (commandInput.toCommand()) {
                Command.SET -> internalStore.put(firstArgument, secondArgument)
                Command.GET -> println(internalStore[firstArgument] ?: KEY_NOT_SET)
                Command.DELETE -> internalStore.remove(firstArgument)
                Command.COUNT -> println(internalStore.count(firstArgument))
                Command.BEGIN -> transaction(internalStore, true)?.let { internalStore = it.copy() }
                Command.ROLLBACK -> {
                    if (isTransaction)
                        return null
                    else {
                        println("no transaction")
                    }
                }
                Command.COMMIT -> {
                    if (isTransaction)
                        return internalStore
                    else {
                        println("no transaction")
                    }
                }
                Command.UNKNOWN -> println(INCORRECT_INPUT)
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }
}
