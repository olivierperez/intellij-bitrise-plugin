package fr.o80.bitriseplugin.ui.entry

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.jetbrains.compose.theme.WidgetTheme
import fr.o80.bitriseplugin.ui.page.BuildsPage

class BuildsToolWindowsFactory : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        toolWindow.contentManager.addContent(
            ContentFactory.getInstance().createContent(BuildsPage(), "Builds", false)
        )
        toolWindow.contentManager.addContent(
            ContentFactory.getInstance().createContent(ComposePanel().apply {
                setContent {
                    WidgetTheme(darkTheme = true) {
                        Surface(modifier = Modifier.fillMaxSize()) {
                            Column(Modifier.fillMaxSize()) {
                                Text("Text1")
                                Button(onClick = {
                                    println("Clicked")
                                }) {
                                    Text("Bouton")
                                }
                                Text("Text2")
                            }
                        }
                    }
                }
            }, "Start Workflow", false)
        )
    }
}
