package com.billcoreatech.bespeak1003.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.billcoreatech.bespeak1003.ui.theme.softBlue
import com.billcoreatech.bespeak1003.widget.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ProductItemScreen(
    navigator: DestinationsNavigator,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = {

        }) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = "Coming Soon",
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp),
                tint = softBlue
            )
        }
        Text(text = "Coming Soon ...")
    }
}