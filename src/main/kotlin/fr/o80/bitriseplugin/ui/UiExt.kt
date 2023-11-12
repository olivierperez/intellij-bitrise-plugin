package fr.o80.bitriseplugin.ui

import com.intellij.util.ui.JBUI
import java.awt.Font
import javax.swing.JComponent

fun JComponent.padding(padding: Int) = apply {
    border = JBUI.Borders.empty(padding)
}

fun JComponent.bold() = apply {
    font = font.deriveFont(font.style or Font.BOLD)
}
