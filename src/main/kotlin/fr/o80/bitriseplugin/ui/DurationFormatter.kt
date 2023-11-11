package fr.o80.bitriseplugin.ui

import kotlin.time.Duration

class DurationFormatter {
    fun format(duration: Duration): String = when {
        duration.inWholeHours < 1 -> duration.formatInMinutes()
        duration.inWholeDays < 1 -> duration.formatInHours()
        duration.inWholeDays < 2 -> duration.formatInDayAndHours()
        else -> duration.formatInDays()
    }
}

private fun Duration.formatInMinutes(): String {
    return "$inWholeMinutes min $inWholeSeconds"
}

private fun Duration.formatInHours(): String {
    return "${inWholeHours}h ${inWholeMinutes}min"
}

private fun Duration.formatInDayAndHours(): String {
    return "1 day and $inWholeHours hours"
}

private fun Duration.formatInDays(): String {
    return "$inWholeDays days"
}
