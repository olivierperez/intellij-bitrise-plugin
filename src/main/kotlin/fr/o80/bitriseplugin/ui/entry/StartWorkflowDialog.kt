package fr.o80.bitriseplugin.ui.entry

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import fr.o80.bitriseplugin.ui.page.StartWorkflowPage
import javax.swing.JComponent

class StartWorkflowDialog(
    project: Project
) : DialogWrapper(project) {

    init {
        title = "Start Workflow"
        okAction.isEnabled = false
        setOKButtonText("Start")
        init()
    }

    private var page: StartWorkflowPage? = null

    override fun createCenterPanel(): JComponent =
        StartWorkflowPage(
            onValidityChanged = { okAction.isEnabled = it }
        ).also { page = it }

    override fun getPreferredFocusedComponent(): JComponent? {
        return page?.getPreferredFocusedComponent()
    }

    override fun getDimensionServiceKey(): String {
        return "BitrisePlugin.StartWorkflowDialog"
    }

    override fun doOKAction() {
        page?.doOKAction()
        super.doOKAction()
    }

    override fun doValidate(): ValidationInfo? {
        return null
    }
}
