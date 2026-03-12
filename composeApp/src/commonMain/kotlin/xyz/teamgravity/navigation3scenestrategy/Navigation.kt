package xyz.teamgravity.navigation3scenestrategy

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun Navigation() {
    val stack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.TodoList::class, Route.TodoList.serializer())
                    subclass(Route.Todo::class, Route.Todo.serializer())
                }
            }
        },
        Route.TodoList
    )
    NavDisplay(
        backStack = stack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        sceneStrategy = rememberListDetailSceneStrategy(),
        entryProvider = entryProvider {
            entry<Route.TodoList>(
                metadata = mapOf(ListDetailScene.KEY_LIST to Unit)
            ) {
                TodoListScreen(
                    onNavigateTodo = { todo ->
                        stack.add(Route.Todo(todo))
                    }
                )
            }
            entry<Route.Todo>(
                metadata = mapOf(ListDetailScene.KEY_DETAIL to Unit)
            ) { route ->
                TodoScreen(
                    todoExtra = route.todoExtra
                )
            }
        }
    )
}