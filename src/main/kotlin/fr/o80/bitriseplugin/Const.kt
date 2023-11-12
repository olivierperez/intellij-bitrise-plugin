package fr.o80.bitriseplugin

object Const {
    val token = System.getenv("BITRISE_PLUGIN_TOKEN")
    val appSlug = System.getenv("BITRISE_PLUGIN_APPSLUG")
}
