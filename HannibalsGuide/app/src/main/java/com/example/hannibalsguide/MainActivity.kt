package com.example.hannibalsguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.hannibalsguide.presentation.chat.ChatScreen
import com.example.hannibalsguide.presentation.chat.ChatViewModel
import com.example.hannibalsguide.presentation.home.HomeScreen
import com.example.hannibalsguide.presentation.home.HomeViewModel
import com.example.hannibalsguide.presentation.landmarkdetail.DetailScreen
import com.example.hannibalsguide.presentation.landmarkdetail.DetailViewModel
import com.example.hannibalsguide.presentation.map.MapScreen
import com.example.hannibalsguide.presentation.map.MapViewModel
import com.example.hannibalsguide.presentation.settings.LanguageViewModel
import com.example.hannibalsguide.presentation.settings.SettingsScreen
import com.example.hannibalsguide.ui.theme.HannibalsGuideTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val languageViewModel: LanguageViewModel = hiltViewModel()
            val language by languageViewModel.language.collectAsState()
            androidx.compose.runtime.CompositionLocalProvider(
                LocalLayoutDirection provides if (language.isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
            ) {
                HannibalsGuideTheme {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            val vm: HomeViewModel = hiltViewModel()
                            HomeScreen(
                                viewModel = vm,
                                onLandmarkClick = { id -> navController.navigate("detail/$id") },
                                onSettingsClick = { navController.navigate("settings") }
                            )
                        }

                        composable(
                            "detail/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id").orEmpty()
                            val vm: DetailViewModel = hiltViewModel()
                            DetailScreen(
                                viewModel = vm,
                                landmarkId = id,
                                onBack = { navController.popBackStack() },
                                onAskTarek = { lmId -> navController.navigate("chat/$lmId") },
                                onOpenMap = { lmId -> navController.navigate("map/$lmId") }
                            )
                        }

                        composable("chat/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id").orEmpty()
                            val vm: ChatViewModel = hiltViewModel()
                            ChatScreen(
                                viewModel = vm,
                                landmarkId = id,
                                onBack = { navController.popBackStack() }
                            )
                        }

                        composable("map/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id").orEmpty()
                            val vm: MapViewModel = hiltViewModel()
                            MapScreen(
                                viewModel = vm,
                                landmarkId = id,
                                onBack = { navController.popBackStack() }
                            )
                        }

                        composable("settings") {
                            val vm: LanguageViewModel = hiltViewModel()
                            SettingsScreen(
                                viewModel = vm,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}