package org.tubeskelompok1.rajutmobile.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")

    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")

    object Home : Screen("home")

    object Workshop : Screen("workshop")

    object DetailWorkshop : Screen("detail-workshop/{workshopId}") {
        fun createRoute(workshopId: Int) = "detail-workshop/$workshopId"
    }

    object PesanCustom : Screen("pesan-custom")

    object CustomConfirmation : Screen("custom-confirmation")

    object CustomSuccess : Screen("custom-success")

    object Katalog : Screen("katalog")

    object Keranjang : Screen("keranjang")

    object Checkout : Screen("checkout")

    object Payment : Screen("payment/{total}") {
        fun createRoute(total: Int) = "payment/$total"
    }

    object PaymentSuccess : Screen("payment-success/{total}") {
        fun createRoute(total: Int) = "payment-success/$total"
    }

    object Riwayat : Screen("riwayat")

    object OrderDetail : Screen("order-detail/{type}") {
        fun createRoute(type: String) = "order-detail/$type"
    }

    object Profile : Screen("profile")

    object AccountInfo : Screen("account-info")

    object AddressList : Screen("address-list")

    object EditAddress : Screen("edit-address")

    object AddAddress : Screen("add-address")

    object AdminDashboard : Screen("admin-dashboard")
    object AdminProducts : Screen("admin-products")
    object AdminProductForm : Screen("admin-product/{productId}") {
        fun createRoute(productId: String? = null) = "admin-product/${productId ?: "new"}"
    }
    object AdminOrders : Screen("admin-orders")
    object AdminWorkshops : Screen("admin-workshops")
    object AdminWorkshopForm : Screen("admin-workshop/{workshopId}") {
        fun createRoute(workshopId: String? = null) = "admin-workshop/${workshopId ?: "new"}"
    }

    object Detail : Screen("detail/{produkId}") {
        fun createRoute(produkId: String) = "detail/$produkId"
        fun createRoute(produkId: Int) = "detail/$produkId"
    }
}
