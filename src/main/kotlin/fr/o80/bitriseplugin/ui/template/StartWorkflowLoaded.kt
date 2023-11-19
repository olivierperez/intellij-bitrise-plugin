package fr.o80.bitriseplugin.ui.template

import com.intellij.icons.AllIcons
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBTextField
import com.intellij.ui.components.panels.VerticalLayout
import fr.o80.bitriseplugin.domain.StartWorkflowUseCase
import fr.o80.bitriseplugin.ui.atom.JComment
import fr.o80.bitriseplugin.ui.utils.onValueChanged
import fr.o80.bitriseplugin.ui.utils.titledBordered
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.ListSelectionModel

class StartWorkflowLoaded(
    private val workflows: List<String>,
    private val startWorkflow: StartWorkflowUseCase
) : JPanel(VerticalLayout(0)) {

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
        model = JBList.createDefaultListModel(workflows)
        addListSelectionListener { updatePage() }
    }

    private val startButton = JButton("Start workflow").apply {
        icon = AllIcons.Actions.Play_forward
        addActionListener { doOKAction() }
    }

    private val result = JComment("")

    init {
        add(referenceSelection)
        add(workflowSelection)
        add(startButton)
        add(result)
        updatePage()
    }

    override fun requestFocus() {
        branchName.requestFocus()
    }

    private fun doOKAction() {
        scope.launch {
            disableUi()
            val tag = tagName.text.takeUnless { it.isBlank() }
            val branch = branchName.text.takeUnless { it.isBlank() }
            val workflow = workflowSelection.selectedValue
            startWorkflow(
                workflow,
                tag,
                branch
            )
            /*val referenceMessage = tag?.let { "Tag: <b>$tag</b>" }
                ?: branch?.let { "Branch: <b>$branch</b>" }
                ?: "No reference found"
            result.text = "Workflow started: $workflow -- $referenceMessage"*/
            reset()
            enabledUi()
        }
    }

    private fun disableUi() {
        startButton.isEnabled = false
        tagName.isEnabled = false
        branchName.isEnabled = false
        workflowSelection.isEnabled = false
    }

    private fun enabledUi() {
        startButton.isEnabled = true
        tagName.isEnabled = true
        branchName.isEnabled = true
        workflowSelection.isEnabled = true
    }

    private fun reset() {
        tagName.text = ""
        branchName.text = ""
    }

    private fun updatePage() {
        tagName.isEnabled = branchName.text.isBlank()
        branchName.isEnabled = tagName.text.isBlank()

        val referenceIsEntered = branchName.text.isNotBlank() || tagName.text.isNotBlank()
        val formIsValid = referenceIsEntered && workflowSelection.selectedIndex >= 0

        startButton.isEnabled = formIsValid
    }
}
