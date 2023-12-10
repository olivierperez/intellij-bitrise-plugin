package fr.o80.bitriseplugin.ui.atom

import fr.o80.bitriseplugin.ui.molecule.CenterPanel
import javax.swing.JProgressBar

class LoadingComponent(text: String?) : CenterPanel(
    JProgressBar(JProgressBar.HORIZONTAL).apply {
        isIndeterminate = true
        text?.let {
            string = text
            isStringPainted = true
        }
    }
)
