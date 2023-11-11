package fr.o80.bitriseplugin.domain.model

data class Branch(
    val ref: String,
    val builds: List<Build>
) {
    val moreRecentBuild: Build = builds.maxBy { build -> build.startDate }
}
