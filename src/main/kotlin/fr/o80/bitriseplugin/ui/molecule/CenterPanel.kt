package fr.o80.bitriseplugin.ui.molecule

import java.awt.GridBagLayout
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JPanel

open class CenterPanel(
    content: JComponent
): JPanel(GridBagLayout()) {
    init {
        this.add(content)
        this.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
    }
}
