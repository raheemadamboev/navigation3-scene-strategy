package xyz.teamgravity.navigation3scenestrategy

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.Scene

class ListDetailScene<T : Any>(
    val list: NavEntry<T>,
    val detail: NavEntry<T>,
    override val key: Any,
    override val previousEntries: List<NavEntry<T>>
) : Scene<T> {

    companion object {
        const val KEY_LIST = "ListDetailScene-List"
        const val KEY_DETAIL = "ListDetailScene-Detail"
    }

    override val content: @Composable (() -> Unit) = {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.weight(0.4F)
            ) {
                list.Content()
            }
            Box(
                modifier = Modifier.weight(0.6F)
            ) {
                detail.Content()
            }
        }
    }

    override val entries: List<NavEntry<T>> = listOf(list, detail)
}