
enum class Command(val value: String, val numberOfArguments: Int) {
    SET("set", 2),
    GET("get", 1),
    DELETE("delete", 1),
    COUNT("count", 1),
    BEGIN("begin", 0),
    ROLLBACK("rollback", 0),
    COMMIT("commit", 0),
    UNKNOWN("unknown", Int.MAX_VALUE),
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
