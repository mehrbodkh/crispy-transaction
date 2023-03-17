const val KEY_NOT_SET = "key not set"
const val INCORRECT_INPUT = "incorrect input"
const val NO_TRANSACTION = "no transaction"

fun main() {
    transaction(DataStore())
}

fun transaction(dataStore: KeyValueStore<String, String>, isFirstIteration: Boolean = true): KeyValueStore<String, String>? {
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
                Command.BEGIN -> transaction(internalStore, false)?.let { internalStore = it.copy() }
                Command.ROLLBACK -> if (isFirstIteration) println(NO_TRANSACTION) else return null
                Command.COMMIT -> if (isFirstIteration) println(NO_TRANSACTION) else return internalStore
                Command.EXIT -> return null
                Command.UNKNOWN -> println(INCORRECT_INPUT)
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }
}
