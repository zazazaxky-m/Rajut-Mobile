package org.tubeskelompok1.rajutmobile.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")

    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")

    object Katalog : Screen("katalog")

    object Keranjang : Screen("keranjang")

    object Riwayat : Screen("riwayat")

    object Detail : Screen("detail/{produkId}") {
        fun createRoute(produkId: Int) = "detail/$produkId"
    }
}
