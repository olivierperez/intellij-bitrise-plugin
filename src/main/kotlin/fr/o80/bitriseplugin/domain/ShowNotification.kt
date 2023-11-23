package fr.o80.bitriseplugin.domain

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.ProjectManager
import fr.o80.bitriseplugin.ui.Icons
import javax.swing.Icon

class ShowNotification {
    operator fun invoke(title: String, message: String, type: Type = Type.INFO) {
        val project = ProjectManager.getInstance().defaultProject
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Bitrise Workflow")
            .createNotification(title, message, type.ideType)
            .setIcon(type.icon)
            .notify(project)
    }

    enum class Type(
        val ideType: NotificationType,
        val icon: Icon
    ) {
        INFO(NotificationType.INFORMATION, Icons.CircleCheck),
        ERROR(NotificationType.ERROR, Icons.CircleMinus)
    }
}
