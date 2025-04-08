package com.example.vinilos

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vinilos.ui.navigation.Home
import com.example.vinilos.ui.screens.VinilosHome

@Composable
fun VinilosApp(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<Home> {
            VinilosHome(navController = navController)
        }
    }
}
