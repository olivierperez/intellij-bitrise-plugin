package fr.o80.bitriseplugin.ui.component

import java.awt.BorderLayout
import java.awt.Component
import javax.swing.JPanel

class BorderComponent(
    west: Component? = null,
    center: Component? = null,
    hGap: Int = 0,
    vGap: Int = 0,
) : JPanel(BorderLayout(hGap, vGap)) {
    init {
        west?.let { add(west, BorderLayout.WEST) }
        center?.let { add(center, BorderLayout.CENTER) }
    }
}
