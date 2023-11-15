package fr.o80.bitriseplugin.ui.utils

import com.intellij.util.ui.JBUI
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.border.CompoundBorder
import javax.swing.border.TitledBorder


fun JComponent.padding(padding: Int): JComponent = apply {
    border = JBUI.Borders.empty(padding)
}

fun JComponent.padding(vertical: Int, horizontal: Int): JComponent = apply {
    border = JBUI.Borders.empty(vertical, horizontal, vertical, horizontal)
}

fun titledBordered(title: String, padding: Int = 4): TitledBorder = BorderFactory.createTitledBorder(
    CompoundBorder(
        JBUI.Borders.customLine(JBUI.CurrentTheme.ToolWindow.borderColor()),
        JBUI.Borders.empty(padding)
    ),
    title
)
