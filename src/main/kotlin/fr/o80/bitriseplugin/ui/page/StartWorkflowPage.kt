package fr.o80.bitriseplugin.ui.page

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBTextField
import com.intellij.ui.components.panels.VerticalLayout
import fr.o80.bitriseplugin.data.BitriseWebService
import fr.o80.bitriseplugin.data.HttpClientProvider
import fr.o80.bitriseplugin.domain.GetWorkflowsUseCase
import fr.o80.bitriseplugin.domain.StartWorkflowUseCase
import fr.o80.bitriseplugin.ui.utils.titledBordered
import fr.o80.bitriseplugin.ui.utils.onValueChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.awt.BorderLayout
import java.awt.GridLayout
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.ListSelectionModel

class StartWorkflowPage(
    private val onValidityChanged: (Boolean) -> Unit
) : JPanel(BorderLayout()) {

    private val webService = BitriseWebService(HttpClientProvider.client)
    private val getWorkflows = GetWorkflowsUseCase(webService)
    private val startWorkflow = StartWorkflowUseCase(webService)

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val branchName = JBTextField().onValueChanged {
        updatePage()
    }

    private val tagName = JBTextField().onValueChanged {
        updatePage()
    }

    private val referenceSelection = JPanel(GridLayout(2, 2)).apply {
        border = titledBordered("Commit reference")
        add(JBLabel("Branch"))
        add(branchName)
        add(JBLabel("Tag"))
        add(tagName)
    }

    private val workflowSelection = JBList<String>().apply {
        border = titledBordered("Workflow")
        selectionMode = ListSelectionModel.SINGLE_SELECTION
        layoutOrientation = JBList.HORIZONTAL_WRAP
        visibleRowCount = 6
        addListSelectionListener { updatePage() }
    }

    private val content = JPanel(VerticalLayout(0)).apply {
        add(referenceSelection)
        add(workflowSelection)
    }

    init {
        add(content, BorderLayout.CENTER)
        load()
    }

    private fun load() {
        scope.launch {
            val workflows = getWorkflows()
            workflowSelection.model = JBList.createDefaultListModel(workflows)
        }
    }

    private fun updatePage() {
        tagName.isEnabled = branchName.text.isBlank()
        branchName.isEnabled = tagName.text.isBlank()

        val referenceIsEntered = branchName.text.isNotBlank() || tagName.text.isNotBlank()
        val formIsValid = referenceIsEntered && workflowSelection.selectedIndex >= 0
        onValidityChanged(formIsValid)
    }

    fun doOKAction() {
        println("Do OK action")
        scope.launch {
            startWorkflow(
                workflowSelection.selectedValue,
                tagName.text.takeUnless { it.isBlank() },
                branchName.text.takeUnless { it.isBlank() }
            )
        }
    }

    fun getPreferredFocusedComponent(): JComponent {
        return branchName
    }
}
