package fr.o80.bitriseplugin.ui

import fr.o80.bitriseplugin.domain.model.Branch
import fr.o80.bitriseplugin.ui.component.BranchBuildsPanel
import fr.o80.bitriseplugin.ui.component.VerticalComponent
import javax.swing.JComponent

class BuildsPanelFactory {
    fun create(builds: List<Branch>): JComponent {
        return VerticalComponent(
            items = builds,
            gap = 0
        ) { build ->
            BranchBuildsPanel(build)
                .padding(4)
        }
    }
}
