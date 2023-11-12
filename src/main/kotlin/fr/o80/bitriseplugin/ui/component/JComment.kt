package fr.o80.bitriseplugin.ui.component

import com.intellij.util.ui.JBUI
import javax.swing.JLabel

class JComment(text: String) : JLabel(text) {
    init {
        foreground = JBUI.CurrentTheme.ContextHelp.FOREGROUND
    }
}
