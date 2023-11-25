package fr.o80.bitriseplugin.ui.utils

import java.awt.Font
import javax.swing.JComponent
import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

fun JComponent.bold(): JComponent = apply {
    font = font.deriveFont(font.style or Font.BOLD)
}

fun JComponent.tooltip(text: String): JComponent = apply {
    toolTipText = text
}

fun JTextField.onValueChanged(callback: (String) -> Unit): JTextField {
    document.addDocumentListener(object : DocumentListener {
        override fun insertUpdate(event: DocumentEvent) {
            callback(text)
        }

        override fun removeUpdate(event: DocumentEvent) {
            callback(text)
        }

        override fun changedUpdate(event: DocumentEvent) {
            callback(text)
        }
    })

    return this
}
