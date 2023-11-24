package fr.o80.bitriseplugin.ui.formatter

import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class InstantFormatter(
    private val zoneId: ZoneId = ZoneId.systemDefault()
) {
    private val dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)

    fun format(instant: Instant): String {
        return dateTimeFormatter.format(
            instant.toJavaInstant().atZone(zoneId)
        )
    }
}
