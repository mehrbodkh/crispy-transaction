fun readCommand() = readlnOrNull()?.let {
    val input = it.trim().split(' ')
    if (isCorrectInput(input)) {
        input + listOf("", "")
    } else {
        throw Exception(INCORRECT_INPUT)
    }
} ?: throw Exception(INCORRECT_INPUT)

fun isCorrectInput(input: List<String>) = input.isNotEmpty() && input[0].toCommand().numberOfArguments <= input.size - 1
