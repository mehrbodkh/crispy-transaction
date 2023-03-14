
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
