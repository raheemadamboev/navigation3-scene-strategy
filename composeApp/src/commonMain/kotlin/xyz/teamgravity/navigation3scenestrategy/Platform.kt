package xyz.teamgravity.navigation3scenestrategy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform