package org.tubeskelompok1.rajutmobile.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.savedstate.read
import org.tubeskelompok1.rajutmobile.screen.*

// Rute yang akan ditampilkan di bottom navigation bar (setelah login)
/*
private val bottomNavItems = listOf(
    Triple(Screen.Katalog.route, "Katalog", Icons.Filled.Storefront),
    Triple(Screen.Keranjang.route, "Keranjang", Icons.Filled.ShoppingCart),
    Triple(Screen.Riwayat.route, "Riwayat", Icons.Filled.History)
)
*/
@Composable
fun RajutNavGraph() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Bottom bar hanya muncul ketika sudah login
    //val tampilkanBottomBar = currentRoute in bottomNavItems.map { it.first }
    val tampilkanBottomBar = false

    Scaffold(
        /*
        bottomBar = {
            if (tampilkanBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { (route, label, icon) ->
                        NavigationBarItem(
                            selected = currentRoute == route,
                            onClick = {
                                navController.navigate(route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) }
                        )
                    }
                }
            }
        }
        */
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Onboarding.route,
            modifier = androidx.compose.ui.Modifier.padding(innerPadding)
        ) {
            composable(Screen.Onboarding.route) {
                OnboardingScreen(
                    onSelesai = {
                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Welcome.route) {
                WelcomeScreen(
                    onMasuk = { navController.navigate(Screen.Login.route) },
                    onDaftar = { navController.navigate(Screen.Register.route) }
                )
            }

            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginBerhasil = {
                        /*
                        navController.navigate(Screen.Katalog.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                         */
                    },
                    onGoToRegister = {
                        navController.navigate(Screen.Register.route)
                    }
                )
            }

            composable(Screen.Register.route) {
                RegisterScreen(
                    onRegisterBerhasil = {
                        /*
                        navController.navigate(Screen.Katalog.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                         */
                    },
                    onBackToLogin = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Katalog.route) {
                KatalogScreen(
                    onProdukClick = { produkId ->
                        navController.navigate(Screen.Detail.createRoute(produkId))
                    }
                )
            }

            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("produkId") { type = NavType.IntType })
            ) { backStackEntry ->
                val produkId = backStackEntry.arguments?.read { getIntOrNull("produkId")} ?: 0
                DetailBarangScreen(
                    produkId = produkId,
                    onBack = { navController.popBackStack() },
                    onLihatKeranjang = {
                        navController.navigate(Screen.Keranjang.route)
                    }
                )
            }

            composable(Screen.Keranjang.route) {
                KeranjangScreen(
                    onCheckoutBerhasil = {
                        navController.navigate(Screen.Riwayat.route) {
                            popUpTo(Screen.Katalog.route)
                        }
                    }
                )
            }

            composable(Screen.Riwayat.route) {
                RiwayatScreen()
            }
        }
    }
}