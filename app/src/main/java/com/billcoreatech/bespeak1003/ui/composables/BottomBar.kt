package com.billcoreatech.bespeak1003.ui.composables

import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.billcoreatech.bespeak1003.R
import com.billcoreatech.bespeak1003.ui.theme.fonts
import com.billcoreatech.bespeak1003.ui.theme.softBlue
import com.billcoreatech.bespeak1003.widget.NavGraphs
import com.billcoreatech.bespeak1003.widget.destinations.DirectionDestination
import com.billcoreatech.bespeak1003.widget.destinations.HomeScreenDestination
import com.billcoreatech.bespeak1003.widget.destinations.ManagerScreenDestination
import com.billcoreatech.bespeak1003.widget.destinations.ProductItemScreenDestination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.utils.isRouteOnBackStack

@Composable
fun BottomBar(
    navController: NavHostController
) {
    BottomNavigation {
        BottomBarItem.values().forEach { destination ->

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            BottomNavigationItem(
                alwaysShowLabel = true,
                selected = currentRoute == destination.direction.route ,
                onClick = {
                    navController.navigate(destination.direction) {
                        popUpTo(NavGraphs.root) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        destination.icon,
                        contentDescription = stringResource(destination.label)
                    )
                },
                label = { Text(
                    text = stringResource(destination.label),
                    style = TextStyle(
                        fontFamily = fonts,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )) },
            )
        }
    }
}

enum class BottomBarItem(
    val direction: DirectionDestination,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Home(HomeScreenDestination, Icons.Outlined.Home, R.string.Home),
    ProductItem(ProductItemScreenDestination, Icons.Outlined.ShoppingCart, R.string.productItems),
    Setting(ManagerScreenDestination, Icons.Outlined.Settings, R.string.Setting)
}