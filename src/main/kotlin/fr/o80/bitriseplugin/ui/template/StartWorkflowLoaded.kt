package fr.o80.bitriseplugin.ui.template

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBTextField
import com.intellij.ui.components.panels.VerticalLayout
import fr.o80.bitriseplugin.domain.StartWorkflowUseCase
import fr.o80.bitriseplugin.ui.utils.onValueChanged
import fr.o80.bitriseplugin.ui.utils.titledBordered
import java.awt.GridLayout
import javax.swing.JPanel
import javax.swing.ListSelectionModel

class StartWorkflowLoaded(
    private val workflows: List<String>,
    private val startWorkflow: StartWorkflowUseCase,
    private val onValidityChanged: (Boolean) -> Unit
): JPanel(VerticalLayout(0)) {

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
        model = JBList.createDefaultListModel(workflows)
        addListSelectionListener { updatePage() }
    }

    init {
        add(referenceSelection)
        add(workflowSelection)
    }

    override fun requestFocus() {
        branchName.requestFocus()
    }

    suspend fun doOKAction() {
        startWorkflow(
            workflowSelection.selectedValue,
            tagName.text.takeUnless { it.isBlank() },
            branchName.text.takeUnless { it.isBlank() }
        )
    }

    private fun updatePage() {
        tagName.isEnabled = branchName.text.isBlank()
        branchName.isEnabled = tagName.text.isBlank()

        val referenceIsEntered = branchName.text.isNotBlank() || tagName.text.isNotBlank()
        val formIsValid = referenceIsEntered && workflowSelection.selectedIndex >= 0
        onValidityChanged(formIsValid)
    }
}
