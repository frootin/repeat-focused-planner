package ru.sfu.planner.ui.base

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.sfu.planner.R
import ru.sfu.planner.ui.theme.AppTheme as PlannerTheme

class BaseFragment : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlannerTheme {
                Ex11()
            }
        }
    }
}

sealed class NavRoute(val route: String, @DrawableRes val icon: Int, val topBarText: String) {
    data object Home : NavRoute("home", R.drawable.ic_home, "Сегодня")
    data object PresetsList : NavRoute("presetslist", R.drawable.ic_paperclip, "Шаблоны")
    data object AddTask : NavRoute("addtask", R.drawable.ic_plus, "Добавить задачу")
    data object CalendarPage : NavRoute("calendar", R.drawable.ic_calendar, "Календарь")
    data object Settings : NavRoute("settings", R.drawable.ic_settings, "Настройки")
}

@Composable
fun BasicElements(modifier: Modifier = Modifier) {
    val title = remember {
        mutableStateOf(NavRoute.Home.topBarText)
    }
    val navController = rememberNavController()

    val topLevelDestinations = listOf(
        TopLevelDestination(
            route = NavRoute.Home.route,
            icon = NavRoute.Home.icon,
            iconText = NavRoute.Home.topBarText
        ), TopLevelDestination(
            route = NavRoute.PresetsList.route,
            icon = NavRoute.PresetsList.icon,
            iconText = NavRoute.PresetsList.topBarText
        ), TopLevelDestination(
            route = NavRoute.AddTask.route,
            icon = NavRoute.AddTask.icon,
            iconText = NavRoute.AddTask.topBarText
        ), TopLevelDestination(
            route = NavRoute.CalendarPage.route,
            icon = NavRoute.CalendarPage.icon,
            iconText = NavRoute.CalendarPage.topBarText
        ), TopLevelDestination(
            route = NavRoute.Settings.route,
            icon = NavRoute.Settings.icon,
            iconText = NavRoute.Settings.topBarText
        )

    )

    Scaffold(
        modifier = modifier,
        bottomBar = { HomeBottomBar(
            topLevelDestinations,
            navController.currentBackStackEntryAsState().value?.destination,
            onNavigateToDestination = {
                title.value = it.iconText
                navController.navigate(it.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            }) },
        topBar = {
            TopBar(title.value)
        }
    )

    {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            HomeNavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                navController = navController,
                startDestination = NavRoute.Home.route
            )

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(title)
        }
    )
}

@Composable
private fun HomeBottomBar(
    destinations: List<TopLevelDestination>,
    currentDestination: NavDestination?,
    onNavigateToDestination: (dest: TopLevelDestination) -> Unit
) {

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
            )
            .height(70.dp),
    ) {
        destinations.forEach { destination ->
            val selected =
                currentDestination?.hierarchy?.any { it.route == destination.route } == true
            var badgeModifier = Modifier.clip(shape = RoundedCornerShape(16.dp))
            NavigationBarItem(
                modifier = badgeModifier,
                colors = NavigationBarItemColors(
                    selectedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    selectedIconColor = MaterialTheme.colorScheme.onSecondary,
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    disabledIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    disabledTextColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(destination.icon),
                        //tint = badgeColor,
                        modifier = Modifier.size(22.dp),
                        contentDescription = null
                    )
                },
                /**label = {
                    Text(
                        text = destination.iconText
                    )
                }*/)
        }
    }
}

@Composable
fun HomeNavHost(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String
) {

    NavHost(
        navController = navController, startDestination = startDestination, modifier = modifier
    ) {
        composable(route = NavRoute.Home.route) {
            HomePage()
        }
        composable(route = NavRoute.PresetsList.route) {
            PresetsPage()
        }
        composable(route = NavRoute.AddTask.route) {
            AddTaskPage()
        }
        composable(route = NavRoute.CalendarPage.route) {
            CalendarPage()
        }
        composable(route = NavRoute.Settings.route) {
            SettingsPage()
        }
    }
}

@Composable
fun HomePage() {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        item {
            TaskContainerWithSubtasks()
        }
    }
}

@Composable
fun AddTaskPage() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Добавить задачу", fontSize = 20.sp)
    }
}

@Composable
fun PresetsPage() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Шаблоны", fontSize = 20.sp)
    }
}

@Composable
fun CalendarPage() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Календарь и поиск", fontSize = 20.sp)
    }
}

@Composable
fun SettingsPage() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Настройки и загрузка в облако", fontSize = 20.sp)
    }
}

@Composable
fun Ex11(
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavRoute.Home.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = NavRoute.Home.route) {
            BasicElements()
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    widthDp = 360,
    heightDp = 800)
@Composable
private fun BasicElementsPreview() {
    PlannerTheme {
        Ex11()
    }
}
