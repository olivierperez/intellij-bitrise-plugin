package fr.o80.bitriseplugin.ui.molecule

import com.intellij.ui.components.JBScrollPane
import fr.o80.bitriseplugin.domain.model.Branch
import fr.o80.bitriseplugin.ui.atom.VerticalComponent
import fr.o80.bitriseplugin.ui.utils.padding

class BuildsVerticalList(
    builds: List<Branch>
) : JBScrollPane(VerticalComponent(items = builds) { build ->
    BuildPanel(build)
        .padding(horizontal = 4, vertical = 10)
}) {
    init {
        verticalScrollBarPolicy = VERTICAL_SCROLLBAR_AS_NEEDED
        horizontalScrollBarPolicy = HORIZONTAL_SCROLLBAR_NEVER
    }
}
