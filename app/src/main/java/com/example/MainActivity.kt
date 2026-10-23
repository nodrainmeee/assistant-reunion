package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.ActiveMeetingScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MeetingDetailScreen
import com.example.ui.screens.MeetingsListScreen
import com.example.ui.screens.NewMeetingScreen
import com.example.ui.screens.ProcessingScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.MeetingViewModel

sealed class Screen(val route: String, val titleRes: Int, val icon: ImageVector) {
    object Home : Screen("home", R.string.nav_home, Icons.Default.Home)
    object Meetings : Screen("meetings", R.string.nav_meetings, Icons.AutoMirrored.Filled.List)
    object Search : Screen("search", R.string.nav_search, Icons.Default.Search)
    object Settings : Screen("settings", R.string.nav_settings, Icons.Default.Settings)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: MeetingViewModel = viewModel()
                MainScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MeetingViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarScreens = listOf(Screen.Home, Screen.Meetings, Screen.Search, Screen.Settings)
    val showBottomBar = currentRoute in bottomBarScreens.map { it.route }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomBarScreens.forEach { screen ->
                        val selected = currentRoute == screen.route
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = null) },
                            label = { Text(stringResource(screen.titleRes)) },
                            selected = selected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            modifier = Modifier.testTag("nav_${screen.route}")
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToNewMeeting = { navController.navigate("new_meeting") },
                    onNavigateToMeetingDetail = { meetingId ->
                        viewModel.selectMeeting(meetingId)
                        navController.navigate("meeting_detail/$meetingId")
                    },
                    onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                    onNavigateToActiveMeeting = { navController.navigate("active_meeting") }
                )
            }

            composable(Screen.Meetings.route) {
                MeetingsListScreen(
                    viewModel = viewModel,
                    onNavigateToMeetingDetail = { meetingId ->
                        viewModel.selectMeeting(meetingId)
                        navController.navigate("meeting_detail/$meetingId")
                    }
                )
            }

            composable(Screen.Search.route) {
                SearchScreen(
                    viewModel = viewModel,
                    onNavigateToMeetingDetail = { meetingId ->
                        viewModel.selectMeeting(meetingId)
                        navController.navigate("meeting_detail/$meetingId")
                    }
                )
            }

            composable(Screen.Settings.route) {
                SettingsScreen(viewModel = viewModel)
            }

            composable("new_meeting") {
                NewMeetingScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToActive = { navController.navigate("active_meeting") {
                        popUpTo(Screen.Home.route)
                    }}
                )
            }

            composable("active_meeting") {
                ActiveMeetingScreen(
                    viewModel = viewModel,
                    onNavigateToProcessing = { navController.navigate("processing") {
                        popUpTo(Screen.Home.route)
                    }},
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable("processing") {
                ProcessingScreen(
                    viewModel = viewModel,
                    onNavigateToDetail = { meetingId ->
                        navController.navigate("meeting_detail/$meetingId") {
                            popUpTo(Screen.Home.route)
                        }
                    }
                )
            }

            composable(
                route = "meeting_detail/{meetingId}",
                arguments = listOf(navArgument("meetingId") { type = NavType.StringType })
            ) { backStackEntry ->
                val meetingId = backStackEntry.arguments?.getString("meetingId") ?: ""
                MeetingDetailScreen(
                    meetingId = meetingId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
