fun readCommand(): List<String> = readlnOrNull()?.let {
    it.split(' ') + listOf("", "", "")
} ?: listOf("", "", "")