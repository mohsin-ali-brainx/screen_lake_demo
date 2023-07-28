package com.example.screen_lake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.rememberNavController
import com.example.screen_lake.navigation.ScreenLakeNavGraph
import com.example.screen_lake.ui.theme.ScreenLakeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenLakeTheme {
                val navController = rememberNavController()
                ScreenLakeNavGraph(navController = navController)
//                NavHost(navController = navController, startDestination = Routes.TODO_LIST) {
//                    composable(Routes.TODO_LIST) {
//                        TodoListScreen(
//                            onNavigate = {
//                                navController.navigate(it.route)
//                            })
//                    }
//
//                    composable(
//                        route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
//                        arguments = listOf(
//                            navArgument(name = "todoId") {
//                                type = NavType.IntType
//                                defaultValue = -1
//                            }
//                        )
//                    ) {
//                        AddEditTodoScreen(onPopBackStack = { navController.popBackStack() })
//                    }
//                }
            }
        }
    }
}