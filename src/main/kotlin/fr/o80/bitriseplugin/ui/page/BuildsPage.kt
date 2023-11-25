package fr.o80.bitriseplugin.ui.page

import com.intellij.icons.AllIcons
import fr.o80.bitriseplugin.data.BitriseWebService
import fr.o80.bitriseplugin.data.HttpClientProvider
import fr.o80.bitriseplugin.domain.BranchState
import fr.o80.bitriseplugin.domain.ObserveBranchBuildsUseCase
import fr.o80.bitriseplugin.domain.model.Branch
import fr.o80.bitriseplugin.ui.atom.LoadingComponent
import fr.o80.bitriseplugin.ui.molecule.BuildsVerticalList
import fr.o80.bitriseplugin.ui.utils.padding
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

    private val observeBranchBuilds = ObserveBranchBuildsUseCase(BitriseWebService(HttpClientProvider.client))

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private var buildsComponent: Component? = null
    private var loading = LoadingComponent("Loading Builds").padding(8)

    init {
        add(newHeader(), BorderLayout.NORTH)
        add(loading, BorderLayout.CENTER)
        minimumSize = Dimension(350, 250)

        scope.launch {
            observeBranchBuilds(50).collect { state ->
                when (state) {
                    is BranchState.Loaded -> showContent(state.branches)
                    BranchState.Loading -> showLoading()
                }
            }
        }
    }

    private fun showContent(branches: List<Branch>) {
        buildsComponent = BuildsVerticalList(branches)
        remove(loading)
        add(buildsComponent!!, BorderLayout.CENTER)

        updateUI()
    }

    private fun showLoading() {
        buildsComponent?.let { remove(it) }
        add(loading, BorderLayout.CENTER)

        updateUI()
    }

    private fun refresh() {
        scope.launch { observeBranchBuilds.refresh() }
    }

    private fun newHeader() = JButton("Refresh").apply {
        icon = AllIcons.Actions.Refresh
        addActionListener { refresh() }
    }
}
