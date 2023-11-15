package fr.o80.bitriseplugin.ui.page

import com.intellij.ui.components.JBScrollPane
import fr.o80.bitriseplugin.data.BitriseWebService
import fr.o80.bitriseplugin.data.HttpClientProvider
import fr.o80.bitriseplugin.domain.GetBranchBuildsUseCase
import fr.o80.bitriseplugin.domain.model.Branch
import fr.o80.bitriseplugin.ui.component.BranchBuildsPanel
import fr.o80.bitriseplugin.ui.component.VerticalComponent
import fr.o80.bitriseplugin.ui.padding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import javax.swing.JButton
import javax.swing.JPanel

class BuildsPage : JPanel(BorderLayout()) {

    private val getBranchBuilds = GetBranchBuildsUseCase(BitriseWebService(HttpClientProvider.client))

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private lateinit var buildsComponent: Component

    init {
        add(refreshButton(), BorderLayout.NORTH)
        load()
        minimumSize = Dimension(350,250)
    }

    private fun newBranchBuildsList(builds: List<Branch> = emptyList()) =
        JBScrollPane(
            VerticalComponent(
                items = builds
            ) { build ->
                BranchBuildsPanel(build)
                    .padding(horizontal = 4, vertical = 10)
            }
        ).apply {
            verticalScrollBarPolicy = JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
            horizontalScrollBarPolicy = JBScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        }.also {
            buildsComponent = it
        }

    private fun refreshButton() = JButton("Refresh").apply {
        addActionListener { refresh() }
    }

    private fun refresh() {
        remove(buildsComponent)
        updateUI()
        load()
    }

    private fun load() {
        scope.launch {
            val builds = getBranchBuilds(50)
            buildsComponent = newBranchBuildsList(builds)
            add(buildsComponent, BorderLayout.CENTER)
            updateUI()
        }
    }
}
