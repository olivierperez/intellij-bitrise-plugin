package fr.o80.bitriseplugin.ui.component

import javax.swing.JProgressBar

class LoadingComponent(text: String?) : JProgressBar(HORIZONTAL) {
    init {
        isIndeterminate = true
        text?.let {
            string = text
            isStringPainted = true
        }
    }
}
