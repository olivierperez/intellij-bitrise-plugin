package fr.o80.bitriseplugin.ui

import com.intellij.util.ui.JBUI
import java.awt.Font
import javax.swing.JComponent

fun JComponent.padding(padding: Int): JComponent = apply {
    border = JBUI.Borders.empty(padding)
}

fun JComponent.padding(vertical: Int, horizontal: Int): JComponent = apply {
    border = JBUI.Borders.empty(vertical, horizontal, vertical, horizontal)
}

fun JComponent.bold(): JComponent = apply {
    font = font.deriveFont(font.style or Font.BOLD)
}

fun JComponent.tooltip(text: String): JComponent = apply {
    toolTipText = text
}
