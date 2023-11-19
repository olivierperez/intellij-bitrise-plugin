package fr.o80.bitriseplugin.ui.atom

import com.intellij.ui.components.panels.VerticalLayout
import com.intellij.ui.scale.JBUIScale
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JSeparator

class VerticalComponent<T>(
    items: List<T>,
    gap: Int = 0,
    builder: (T) -> JComponent
) : JPanel(VerticalLayout(JBUIScale.scale(gap))) {
    init {
        items
            .map(builder)
            .forEachIndexed { index, component ->
                if (index != 0) {
                    add(JSeparator())
                }
                add(component)
            }
    }
}
