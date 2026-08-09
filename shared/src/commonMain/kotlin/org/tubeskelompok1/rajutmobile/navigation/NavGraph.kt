package org.tubeskelompok1.rajutmobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.savedstate.read
import org.tubeskelompok1.rajutmobile.data.AppRepository
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
fun RajutNavGraph(repository: AppRepository) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val products by repository.products.collectAsState()
    val workshops by repository.workshops.collectAsState()
    val cartItems by repository.cart.collectAsState()
    val orders by repository.orders.collectAsState()
    val customOrders by repository.customOrders.collectAsState()
    val selectedOrder by repository.selectedOrder.collectAsState()
    val selectedCustomOrder by repository.selectedCustomOrder.collectAsState()
    val user by repository.user.collectAsState()
    val addresses by repository.addresses.collectAsState()
    val adminDashboard by repository.adminDashboard.collectAsState()
    val adminProducts by repository.adminProducts.collectAsState()
    val adminCategories by repository.adminCategories.collectAsState()
    val adminOrders by repository.adminOrders.collectAsState()
    val adminWorkshops by repository.adminWorkshops.collectAsState()
    LaunchedEffect(Unit) {
        val hasSession = repository.bootstrap()
        if (!hasSession) {
            repository.refreshCatalog()
        }

        delay(1_400)
        val destination = when {
            hasSession && repository.user.value?.role == "admin" -> Screen.AdminDashboard.route
            hasSession -> Screen.Home.route
            repository.hasCompletedOnboarding() -> Screen.Welcome.route
            else -> Screen.Onboarding.route
        }
        navController.navigate(destination) {
            popUpTo(Screen.Splash.route) { inclusive = true }
            launchSingleTop = true
        }
    }
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
            composable(Screen.Splash.route) {
                SplashScreen()
            }

            composable(Screen.Onboarding.route) {
                OnboardingScreen(
                    onSelesai = {
                        scope.launch {
                            repository.completeOnboarding()
                            navController.navigate(Screen.Welcome.route) {
                                popUpTo(Screen.Onboarding.route) { inclusive = true }
                            }
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
                    onLogin = repository::login,
                    onLoginBerhasil = {
                        val destination = if (repository.user.value?.role == "admin") Screen.AdminDashboard.route else Screen.Home.route
                        navController.navigate(destination) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    },
                    onGoToRegister = {
                        navController.navigate(Screen.Register.route)
                    }
                )
            }

            composable(Screen.Register.route) {
                RegisterScreen(
                    onRegister = repository::register,
                    onRegisterBerhasil = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    },
                    onBackToLogin = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    onWorkshop = { navController.navigate(Screen.Workshop.route) },
                    onAllProducts = { navController.navigate(Screen.Katalog.route) },
                    onProductClick = { productId ->
                        navController.navigate(Screen.Detail.createRoute(productId))
                    },
                    onCustomOrder = { navController.navigate(Screen.PesanCustom.route) },
                    onCart = { navController.navigate(Screen.Keranjang.route) },
                    onOrders = { navController.navigate(Screen.Riwayat.route) },
                    onProfile = { navController.navigate(Screen.Profile.route) },
                    products = products
                )
            }

            composable(Screen.Workshop.route) {
                WorkshopScreen(
                    onBack = { navController.popBackStack() },
                    onWorkshopClick = { workshopId ->
                        navController.navigate(Screen.DetailWorkshop.createRoute(workshopId))
                    },
                    workshops = workshops.ifEmpty { org.tubeskelompok1.rajutmobile.model.WorkshopData.items }
                )
            }

            composable(
                route = Screen.DetailWorkshop.route,
                arguments = listOf(navArgument("workshopId") { type = NavType.IntType })
            ) { backStackEntry ->
                val workshopId = backStackEntry.arguments?.read { getIntOrNull("workshopId") } ?: 1
                DetailWorkshopScreen(
                    workshopId = workshopId,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Screen.PesanCustom.route) {
                CustomOrderScreen(
                    onBack = { navController.popBackStack() },
                    onSubmit = repository::submitCustomOrder,
                    onSubmitBerhasil = { navController.navigate(Screen.CustomConfirmation.route) }
                )
            }

            composable(Screen.CustomConfirmation.route) {
                CustomConfirmationScreen(
                    onBack = { navController.popBackStack() },
                    onContinue = { navController.navigate(Screen.CustomSuccess.route) }
                )
            }

            composable(Screen.CustomSuccess.route) {
                CustomOrderSuccessScreen(
                    onViewOrders = {
                        navController.navigate(Screen.Riwayat.route) {
                            popUpTo(Screen.Home.route)
                        }
                    },
                    onHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Screen.Katalog.route) {
                KatalogScreen(
                    onProdukClick = { produkId ->
                        navController.navigate(Screen.Detail.createRoute(produkId))
                    },
                    onBack = { navController.popBackStack() },
                    onCart = { navController.navigate(Screen.Keranjang.route) },
                    remoteProducts = products
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
                    },
                    products = products,
                    onAddToCart = repository::addToCart
                )
            }

            composable(Screen.Keranjang.route) {
                KeranjangScreen(
                    onBack = { navController.popBackStack() },
                    onCheckout = { navController.navigate(Screen.Checkout.route) },
                    itemsInCart = cartItems,
                    onQuantityChange = { product, quantity -> scope.launch { repository.updateCart(product, quantity) } },
                    onDelete = { product -> scope.launch { repository.deleteCart(product) } }
                )
            }

            composable(Screen.Checkout.route) {
                CheckoutScreen(
                    onBack = { navController.popBackStack() },
                    cartItems = cartItems,
                    address = addresses.firstOrNull(),
                    onEditAddress = { navController.navigate(Screen.AddressList.route) },
                    onCreateOrder = { total, deliveryMethod ->
                        scope.launch {
                            if (repository.checkout(deliveryMethod) != null) {
                                navController.navigate(Screen.Payment.createRoute(total))
                            }
                        }
                    }
                )
            }

            composable(
                route = Screen.Payment.route,
                arguments = listOf(navArgument("total") { type = NavType.IntType })
            ) { backStackEntry ->
                val total = backStackEntry.arguments?.read { getIntOrNull("total") } ?: 92_000
                PaymentScreen(
                    total = total,
                    onBack = { navController.popBackStack() },
                    orderId = repository.lastOrderId ?: "-",
                    onPaymentConfirmed = {
                        scope.launch {
                            if (repository.confirmPayment()) {
                                navController.navigate(Screen.PaymentSuccess.createRoute(total)) {
                                    popUpTo(Screen.Checkout.route) { inclusive = true }
                                }
                            }
                        }
                    }
                )
            }

            composable(
                route = Screen.PaymentSuccess.route,
                arguments = listOf(navArgument("total") { type = NavType.IntType })
            ) { backStackEntry ->
                val total = backStackEntry.arguments?.read { getIntOrNull("total") } ?: 92_000
                PaymentSuccessScreen(
                    total = total,
                    onViewOrders = {
                        navController.navigate(Screen.Riwayat.route) {
                            popUpTo(Screen.Home.route)
                        }
                    },
                    onHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Screen.Riwayat.route) {
                RiwayatScreen(
                    onHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    onProfile = { navController.navigate(Screen.Profile.route) },
                    onOrderClick = { type -> navController.navigate(Screen.OrderDetail.createRoute(type)) },
                    orders = orders,
                    customOrders = customOrders
                )
            }

            composable(
                route = Screen.OrderDetail.route,
                arguments = listOf(navArgument("type") { type = NavType.StringType })
            ) { backStackEntry ->
                val orderRef = backStackEntry.arguments?.read { getStringOrNull("type") }.orEmpty()
                LaunchedEffect(orderRef) {
                    runCatching {
                        if (orderRef.startsWith("custom-")) {
                            repository.loadCustomOrder(orderRef.removePrefix("custom-"))
                        } else {
                            repository.loadOrder(orderRef)
                        }
                    }
                }
                OrderDetailScreen(
                    onBack = { navController.popBackStack() },
                    order = selectedOrder,
                    customOrder = selectedCustomOrder
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    onHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    onOrders = { navController.navigate(Screen.Riwayat.route) },
                    onAccount = { navController.navigate(Screen.AccountInfo.route) },
                    onAddress = { navController.navigate(Screen.AddressList.route) },
                    onLogout = {
                        scope.launch {
                            repository.logout()
                            navController.navigate(Screen.Onboarding.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                        }
                    },
                    user = user
                )
            }

            composable(Screen.AccountInfo.route) {
                AccountInfoScreen(onBack = { navController.popBackStack() }, user = user)
            }

            composable(Screen.AddressList.route) {
                AddressListScreen(
                    onBack = { navController.popBackStack() },
                    onEdit = { navController.navigate(Screen.EditAddress.route) },
                    onAdd = { navController.navigate(Screen.AddAddress.route) },
                    addresses = addresses,
                    onDelete = { id -> scope.launch { repository.deleteAddress(id) } }
                )
            }

            composable(Screen.EditAddress.route) {
                AddressFormScreen(
                    isEdit = true,
                    onBack = { navController.popBackStack() },
                    initialAddress = addresses.firstOrNull(),
                    onSave = { request -> repository.saveAddress(addresses.firstOrNull()?.id, request) },
                    onSaved = { navController.popBackStack() }
                )
            }

            composable(Screen.AddAddress.route) {
                AddressFormScreen(
                    isEdit = false,
                    onBack = { navController.popBackStack() },
                    onSave = { request -> repository.saveAddress(null, request) },
                    onSaved = { navController.popBackStack() }
                )
            }

            composable(Screen.AdminDashboard.route) {
                AdminDashboardScreen(
                    dashboard = adminDashboard,
                    products = adminProducts,
                    orders = adminOrders,
                    onProducts = { navController.navigate(Screen.AdminProducts.route) { launchSingleTop = true } },
                    onOrders = { navController.navigate(Screen.AdminOrders.route) { launchSingleTop = true } },
                    onWorkshops = { navController.navigate(Screen.AdminWorkshops.route) { launchSingleTop = true } },
                    onRefresh = { scope.launch { repository.refreshAdmin() } },
                    onLogout = {
                        scope.launch {
                            repository.logout()
                            navController.navigate(Screen.Onboarding.route) {
                                popUpTo(Screen.AdminDashboard.route) { inclusive = true }
                            }
                        }
                    }
                )
            }

            composable(Screen.AdminProducts.route) {
                AdminProductsScreen(
                    products = adminProducts,
                    onDashboard = { navController.navigate(Screen.AdminDashboard.route) { popUpTo(Screen.AdminDashboard.route); launchSingleTop = true } },
                    onOrders = { navController.navigate(Screen.AdminOrders.route) { popUpTo(Screen.AdminDashboard.route); launchSingleTop = true } },
                    onWorkshops = { navController.navigate(Screen.AdminWorkshops.route) { popUpTo(Screen.AdminDashboard.route); launchSingleTop = true } },
                    onAdd = { navController.navigate(Screen.AdminProductForm.createRoute()) },
                    onEdit = { navController.navigate(Screen.AdminProductForm.createRoute(it)) },
                    onDeactivate = repository::deleteAdminProduct
                )
            }

            composable(
                route = Screen.AdminProductForm.route,
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { entry ->
                val productId = entry.arguments?.read { getStringOrNull("productId") }.orEmpty()
                val product = adminProducts.firstOrNull { it.id == productId }
                AdminProductFormScreen(
                    product = product,
                    categories = adminCategories,
                    onBack = { navController.popBackStack() },
                    onSave = { request -> repository.saveAdminProduct(product?.id, request) }
                )
            }

            composable(Screen.AdminOrders.route) {
                AdminOrdersScreen(
                    orders = adminOrders,
                    onDashboard = { navController.navigate(Screen.AdminDashboard.route) { popUpTo(Screen.AdminDashboard.route); launchSingleTop = true } },
                    onProducts = { navController.navigate(Screen.AdminProducts.route) { popUpTo(Screen.AdminDashboard.route); launchSingleTop = true } },
                    onWorkshops = { navController.navigate(Screen.AdminWorkshops.route) { popUpTo(Screen.AdminDashboard.route); launchSingleTop = true } },
                    onUpdateStatus = repository::updateAdminOrderStatus
                )
            }

            composable(Screen.AdminWorkshops.route) {
                AdminWorkshopsScreen(
                    workshops = adminWorkshops,
                    onDashboard = { navController.navigate(Screen.AdminDashboard.route) { popUpTo(Screen.AdminDashboard.route); launchSingleTop = true } },
                    onProducts = { navController.navigate(Screen.AdminProducts.route) { popUpTo(Screen.AdminDashboard.route); launchSingleTop = true } },
                    onOrders = { navController.navigate(Screen.AdminOrders.route) { popUpTo(Screen.AdminDashboard.route); launchSingleTop = true } },
                    onAdd = { navController.navigate(Screen.AdminWorkshopForm.createRoute()) },
                    onEdit = { navController.navigate(Screen.AdminWorkshopForm.createRoute(it)) },
                    onDeactivate = repository::deleteAdminWorkshop
                )
            }

            composable(
                route = Screen.AdminWorkshopForm.route,
                arguments = listOf(navArgument("workshopId") { type = NavType.StringType })
            ) { entry ->
                val workshopId = entry.arguments?.read { getStringOrNull("workshopId") }.orEmpty()
                val workshop = adminWorkshops.firstOrNull { it.id == workshopId }
                AdminWorkshopFormScreen(
                    workshop = workshop,
                    onBack = { navController.popBackStack() },
                    onSave = { request -> repository.saveAdminWorkshop(workshop?.id, request) }
                )
            }
    }
}
