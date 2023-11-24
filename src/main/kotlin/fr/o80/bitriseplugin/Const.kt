package fr.o80.bitriseplugin

object Const {
    val token: String = System.getenv("BITRISE_PLUGIN_TOKEN")
    val appSlug: String = System.getenv("BITRISE_PLUGIN_APPSLUG")
}
