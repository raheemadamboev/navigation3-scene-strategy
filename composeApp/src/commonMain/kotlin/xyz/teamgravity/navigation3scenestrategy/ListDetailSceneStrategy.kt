package xyz.teamgravity.navigation3scenestrategy

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope
import androidx.window.core.layout.WindowSizeClass

class ListDetailSceneStrategy<T : Any>(
    val windowSizeClass: WindowSizeClass
) : SceneStrategy<T> {

    override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T>? {
        if (!windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)) return null

        val detailEntry = entries
            .lastOrNull()
            ?.takeIf { it.metadata.containsKey(ListDetailScene.KEY_DETAIL) }
            ?: return null

        val listEntry = entries
            .lastOrNull { it.metadata.containsKey(ListDetailScene.KEY_LIST) }
            ?: return null

        return ListDetailScene(
            list = listEntry,
            detail = detailEntry,
            key = listEntry.contentKey,
            previousEntries = entries.dropLast(1)
        )
    }
}

@Composable
fun <T : Any> rememberListDetailSceneStrategy(): ListDetailSceneStrategy<T> {
    val windowSizeClass = currentWindowAdaptiveInfo(true).windowSizeClass
    return remember(windowSizeClass) {
        ListDetailSceneStrategy(windowSizeClass)
    }
}