package fr.o80.bitriseplugin.ui.page

import com.intellij.icons.AllIcons
import com.intellij.openapi.project.ProjectManager
import fr.o80.bitriseplugin.data.BitriseWebService
import fr.o80.bitriseplugin.data.HttpClientProvider
import fr.o80.bitriseplugin.domain.GetBranchBuildsUseCase
import fr.o80.bitriseplugin.ui.atom.LoadingComponent
import fr.o80.bitriseplugin.ui.molecule.BuildsVerticalList
import fr.o80.bitriseplugin.ui.entry.StartWorkflowDialog
import fr.o80.bitriseplugin.ui.utils.padding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.jdesktop.swingx.HorizontalLayout
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import javax.swing.JButton
import javax.swing.JPanel

class BuildsPage : JPanel(BorderLayout()) {

    private val getBranchBuilds = GetBranchBuildsUseCase(BitriseWebService(HttpClientProvider.client))

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private lateinit var buildsComponent: Component
    private var loading = LoadingComponent("Loading Builds").padding(8)

    init {
        add(newHeader(), BorderLayout.NORTH)
        add(loading, BorderLayout.CENTER)
        load()
        minimumSize = Dimension(350, 250)
    }

    private fun newHeader() = JPanel(HorizontalLayout()).apply {
        add(JButton("Refresh").apply {
            icon = AllIcons.Actions.Refresh
            addActionListener { refresh() }
        })
        add(JButton("Start workflow").apply {
            icon = AllIcons.Actions.Play_forward
            addActionListener { StartWorkflowDialog(ProjectManager.getInstance().defaultProject).show() }
        })
    }

    private fun refresh() {
        remove(buildsComponent)
        add(loading, BorderLayout.CENTER)
        updateUI()
        load()
    }

    private fun load() {
        scope.launch {
            val builds = getBranchBuilds(50)

            buildsComponent = BuildsVerticalList(builds)
            remove(loading)
            add(buildsComponent, BorderLayout.CENTER)

            updateUI()
        }
    }
}
