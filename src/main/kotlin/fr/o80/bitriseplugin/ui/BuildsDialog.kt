package fr.o80.bitriseplugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JComponent

class BuildsDialog(
    title: String,
    project: Project,
) : DialogWrapper(project) {

    private val buildsPanelFactory = BuildsPanelFactory()

    init {
        setTitle(title)
        setOKButtonText("OK")
        init()
    }

    override fun createCenterPanel(): JComponent = buildsPanelFactory.create()
}
