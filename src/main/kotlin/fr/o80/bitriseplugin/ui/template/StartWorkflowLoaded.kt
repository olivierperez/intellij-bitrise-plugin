package fr.o80.bitriseplugin.ui.template

import com.intellij.icons.AllIcons
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBTextField
import com.intellij.ui.components.panels.VerticalLayout
import fr.o80.bitriseplugin.data.dto.NetworkResponse
import fr.o80.bitriseplugin.domain.ShowNotification
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
import javax.swing.SwingUtilities.invokeLater

class StartWorkflowLoaded(
    private val workflows: List<String>,
    private val startWorkflow: StartWorkflowUseCase,
    private val showNotification: ShowNotification
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
            invokeLater {
                result.text = ""
                disableUi()
            }

            val tag = tagName.text.takeUnless { it.isBlank() }
            val branch = branchName.text.takeUnless { it.isBlank() }
            val workflow = workflowSelection.selectedValue
            val response = startWorkflow(
                workflow,
                tag,
                branch
            )

            invokeLater {
                when (response) {
                    is NetworkResponse.Success -> showSuccessResult(workflow, tag, branch)
                    is NetworkResponse.Error -> showErrorResult(workflow, response.error.message)
                }

                reset()
                enabledUi()
                branchName.requestFocus()
            }
        }
    }

    private fun showSuccessResult(workflow: String?, tag: String?, branch: String?) {
        val workflowMessage = "<b>Workflow:</b> $workflow"
        val referenceMessage = tag?.let { "<b>Tag:</b> $tag" }
            ?: branch?.let { "<b>Branch:</b> $branch" }
            ?: "No reference found"

        showNotification("Workflow Started", "<html>$workflowMessage<br>$referenceMessage</html>")

        result.text = "<html>$workflowMessage<br>$referenceMessage</html>"
    }

    private fun showErrorResult(workflow: String, message: String) {
        showNotification(
            "Workflow Not Started",
            "<html>Failed to start $workflow<br><b>Error:</b> $message</html>",
            ShowNotification.Type.ERROR
        )
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
        workflowSelection.clearSelection()
    }

    private fun updatePage() {
        tagName.isEnabled = branchName.text.isBlank()
        branchName.isEnabled = tagName.text.isBlank()

        val referenceIsEntered = branchName.text.isNotBlank() || tagName.text.isNotBlank()
        val formIsValid = referenceIsEntered && workflowSelection.selectedIndex >= 0

        startButton.isEnabled = formIsValid
    }
}
