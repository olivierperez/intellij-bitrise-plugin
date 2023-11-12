package fr.o80.bitriseplugin.ui

import com.intellij.ui.components.panels.VerticalLayout
import fr.o80.bitriseplugin.domain.model.Branch
import fr.o80.bitriseplugin.ui.component.BranchBuildsPanel
import fr.o80.bitriseplugin.ui.component.VerticalComponent
import kotlinx.coroutines.runBlocking
import java.awt.Component
import java.awt.Container
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel

class BuildsPanelFactory(
    private val load: suspend () -> List<Branch>
) {
    private lateinit var parent: Container
    private var buildsComponent: Component? = null

    fun create(builds: List<Branch>): JComponent {
        return JPanel(VerticalLayout(0)).apply {
            add(refreshButton(::refresh))
            buildsComponent = add(newBranchBuildsList(builds))
        }.also { parent = it }
    }

    private fun refresh() {
        runBlocking {
            parent.apply {
                remove(buildsComponent)
                buildsComponent = null
                invalidate()
                repaint()
            }
            val builds = load()
            buildsComponent = parent.add(newBranchBuildsList(builds))
            parent.invalidate()
            parent.repaint()
        }
    }

    private fun newBranchBuildsList(builds: List<Branch>) =
        VerticalComponent(
            items = builds,
            gap = 0
        ) { build ->
            BranchBuildsPanel(build)
                .padding(4)
        }

    private fun refreshButton(onClick: () -> Unit) = JButton("Refresh").apply {
        addActionListener { onClick() }
    }
}
