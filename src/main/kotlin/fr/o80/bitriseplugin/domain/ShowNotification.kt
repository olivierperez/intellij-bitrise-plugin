package fr.o80.bitriseplugin.domain

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.ProjectManager
import fr.o80.bitriseplugin.ui.Icons

class ShowNotification {
    operator fun invoke(title: String, message: String) {
        val project = ProjectManager.getInstance().defaultProject
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Bitrise Workflow")
            .createNotification(title, message, NotificationType.INFORMATION)
            .setIcon(Icons.BitriseLogo)
            .notify(project)
    }
}
