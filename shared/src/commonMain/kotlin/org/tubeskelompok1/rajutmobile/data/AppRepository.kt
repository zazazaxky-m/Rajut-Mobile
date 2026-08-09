package org.tubeskelompok1.rajutmobile.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.tubeskelompok1.rajutmobile.data.local.AppPreferences
import org.tubeskelompok1.rajutmobile.data.remote.AddressDto
import org.tubeskelompok1.rajutmobile.data.remote.AddressRequest
import org.tubeskelompok1.rajutmobile.data.remote.ArajutApi
import org.tubeskelompok1.rajutmobile.data.remote.CheckoutRequest
import org.tubeskelompok1.rajutmobile.data.remote.CustomOrderRequest
import org.tubeskelompok1.rajutmobile.data.remote.CustomOrderDto
import org.tubeskelompok1.rajutmobile.data.remote.OrderDto
import org.tubeskelompok1.rajutmobile.data.remote.OrderDetailDto
import org.tubeskelompok1.rajutmobile.data.remote.ProductDto
import org.tubeskelompok1.rajutmobile.data.remote.RegisterRequest
import org.tubeskelompok1.rajutmobile.data.remote.UserDto
import org.tubeskelompok1.rajutmobile.data.remote.AdminCategoryDto
import org.tubeskelompok1.rajutmobile.data.remote.AdminDashboardDto
import org.tubeskelompok1.rajutmobile.data.remote.AdminOrderDto
import org.tubeskelompok1.rajutmobile.data.remote.AdminProductDto
import org.tubeskelompok1.rajutmobile.data.remote.AdminProductRequest
import org.tubeskelompok1.rajutmobile.data.remote.AdminWorkshopDto
import org.tubeskelompok1.rajutmobile.data.remote.AdminWorkshopRequest
import org.tubeskelompok1.rajutmobile.generated.resources.Res
import org.tubeskelompok1.rajutmobile.generated.resources.product_baby_shoes
import org.tubeskelompok1.rajutmobile.generated.resources.product_bag_gray
import org.tubeskelompok1.rajutmobile.generated.resources.product_handbag_red
import org.tubeskelompok1.rajutmobile.generated.resources.product_hat_yellow
import org.tubeskelompok1.rajutmobile.generated.resources.product_keychain_flower
import org.tubeskelompok1.rajutmobile.generated.resources.product_sling_cream
import org.tubeskelompok1.rajutmobile.generated.resources.product_totebag_orange
import org.tubeskelompok1.rajutmobile.generated.resources.product_vest_blue
import org.tubeskelompok1.rajutmobile.generated.resources.workshop_beginner
import org.tubeskelompok1.rajutmobile.generated.resources.workshop_group
import org.tubeskelompok1.rajutmobile.model.DataMockup
import org.tubeskelompok1.rajutmobile.model.ItemKeranjang
import org.tubeskelompok1.rajutmobile.model.Produk
import org.tubeskelompok1.rajutmobile.model.Workshop

class AppRepository(
    private val api: ArajutApi,
    private val preferences: AppPreferences
) {
    private val _user = MutableStateFlow<UserDto?>(null)
    val user: StateFlow<UserDto?> = _user.asStateFlow()

    private val _products = MutableStateFlow(DataMockup.daftarProduk)
    val products: StateFlow<List<Produk>> = _products.asStateFlow()

    private val _workshops = MutableStateFlow<List<Workshop>>(emptyList())
    val workshops: StateFlow<List<Workshop>> = _workshops.asStateFlow()

    private val _cart = MutableStateFlow<List<ItemKeranjang>>(emptyList())
    val cart: StateFlow<List<ItemKeranjang>> = _cart.asStateFlow()

    private val _addresses = MutableStateFlow<List<AddressDto>>(emptyList())
    val addresses: StateFlow<List<AddressDto>> = _addresses.asStateFlow()

    private val _orders = MutableStateFlow<List<OrderDto>>(emptyList())
    val orders: StateFlow<List<OrderDto>> = _orders.asStateFlow()

    private val _customOrders = MutableStateFlow<List<CustomOrderDto>>(emptyList())
    val customOrders: StateFlow<List<CustomOrderDto>> = _customOrders.asStateFlow()

    private val _selectedOrder = MutableStateFlow<OrderDetailDto?>(null)
    val selectedOrder: StateFlow<OrderDetailDto?> = _selectedOrder.asStateFlow()

    private val _selectedCustomOrder = MutableStateFlow<CustomOrderDto?>(null)
    val selectedCustomOrder: StateFlow<CustomOrderDto?> = _selectedCustomOrder.asStateFlow()

    private val _adminDashboard = MutableStateFlow<AdminDashboardDto?>(null)
    val adminDashboard: StateFlow<AdminDashboardDto?> = _adminDashboard.asStateFlow()

    private val _adminProducts = MutableStateFlow<List<AdminProductDto>>(emptyList())
    val adminProducts: StateFlow<List<AdminProductDto>> = _adminProducts.asStateFlow()

    private val _adminCategories = MutableStateFlow<List<AdminCategoryDto>>(emptyList())
    val adminCategories: StateFlow<List<AdminCategoryDto>> = _adminCategories.asStateFlow()

    private val _adminOrders = MutableStateFlow<List<AdminOrderDto>>(emptyList())
    val adminOrders: StateFlow<List<AdminOrderDto>> = _adminOrders.asStateFlow()

    private val _adminWorkshops = MutableStateFlow<List<AdminWorkshopDto>>(emptyList())
    val adminWorkshops: StateFlow<List<AdminWorkshopDto>> = _adminWorkshops.asStateFlow()

    var lastOrderId: String? = null
        private set

    suspend fun bootstrap(): Boolean {
        if (preferences.getToken().isBlank()) return false
        return try {
            _user.value = api.me()
            refreshAll()
            true
        } catch (_: Throwable) {
            preferences.clear()
            false
        }
    }

    suspend fun login(email: String, password: String): String? = try {
        val auth = api.login(email, password)
        preferences.saveToken(auth.token)
        preferences.completeOnboarding()
        _user.value = auth.user
        refreshAll()
        null
    } catch (error: Throwable) {
        error.message ?: "Login gagal. Periksa koneksi dan akun Anda."
    }

    suspend fun register(name: String, email: String, phone: String, password: String): String? = try {
        val auth = api.register(RegisterRequest(name, email, phone, password, password))
        preferences.saveToken(auth.token)
        preferences.completeOnboarding()
        _user.value = auth.user
        refreshAll()
        null
    } catch (error: Throwable) {
        error.message ?: "Pendaftaran gagal."
    }

    suspend fun logout() {
        runCatching { api.logout() }
        preferences.clearForLogout()
        _user.value = null
        _cart.value = emptyList()
        _addresses.value = emptyList()
        _orders.value = emptyList()
        _customOrders.value = emptyList()
        _selectedOrder.value = null
        _selectedCustomOrder.value = null
        _adminDashboard.value = null
        _adminProducts.value = emptyList()
        _adminCategories.value = emptyList()
        _adminOrders.value = emptyList()
        _adminWorkshops.value = emptyList()
    }

    suspend fun hasCompletedOnboarding(): Boolean = preferences.hasCompletedOnboarding()

    suspend fun completeOnboarding() {
        preferences.completeOnboarding()
    }

    suspend fun refreshAll() {
        if (_user.value?.role == "admin") {
            refreshAdmin()
            return
        }
        refreshCatalog()
        refreshSecureData()
    }

    suspend fun refreshCatalog() {
        runCatching { _products.value = api.products().map(::mapProduct) }
        runCatching {
            _workshops.value = api.workshops().mapIndexed { index, item ->
                Workshop(
                    id = item.id.toInt(),
                    title = item.title,
                    date = item.eventDate.take(10),
                    time = item.endTime,
                    location = item.location,
                    image = if (index == 0) Res.drawable.workshop_beginner else Res.drawable.workshop_group
                )
            }
        }
    }

    suspend fun refreshSecureData() {
        runCatching { refreshCart() }
        runCatching { _addresses.value = api.addresses() }
        runCatching { _orders.value = api.orders() }
        runCatching { _customOrders.value = api.customOrders() }
    }

    suspend fun addToCart(product: Produk): String? = try {
        api.updateCart(product.id.toString(), 1)
        refreshCart()
        null
    } catch (error: Throwable) { error.message ?: "Gagal menambahkan produk" }

    suspend fun updateCart(product: Produk, quantity: Int) {
        if (quantity <= 0) api.deleteCart(product.id.toString()) else api.updateCart(product.id.toString(), quantity)
        refreshCart()
    }

    suspend fun deleteCart(product: Produk) {
        api.deleteCart(product.id.toString())
        refreshCart()
    }

    private suspend fun refreshCart() {
        val knownProducts = _products.value.associateBy { it.id }
        _cart.value = api.cart().items.mapNotNull { item ->
            val id = item.product.id.toIntOrNull() ?: return@mapNotNull null
            val product = knownProducts[id] ?: Produk(
                id = id,
                nama = item.product.name,
                harga = item.product.price.toInt(),
                deskripsi = "Produk rajut Arajut",
                warna = "",
                stok = item.product.stock,
                kategori = "",
                gambar = Res.drawable.product_vest_blue
            )
            ItemKeranjang(product, item.quantity)
        }
    }

    suspend fun checkout(deliveryMethod: String): String? = try {
        val address = _addresses.value.firstOrNull() ?: return null
        lastOrderId = api.checkout(CheckoutRequest(address.id, deliveryMethod))
        lastOrderId
    } catch (_: Throwable) { null }

    suspend fun confirmPayment(): Boolean = try {
        api.createPayment(lastOrderId ?: return false)
        _orders.value = api.orders()
        _cart.value = emptyList()
        true
    } catch (_: Throwable) { false }

    suspend fun submitCustomOrder(productType: String, color: String, size: String, notes: String): String? = try {
        api.createCustomOrder(CustomOrderRequest(productType, color, size, notes))
        _customOrders.value = api.customOrders()
        null
    } catch (error: Throwable) { error.message ?: "Gagal mengirim permintaan custom" }

    suspend fun saveAddress(id: String?, request: AddressRequest): String? = try {
        if (id == null) api.createAddress(request) else api.updateAddress(id, request)
        _addresses.value = api.addresses()
        null
    } catch (error: Throwable) { error.message ?: "Gagal menyimpan alamat" }

    suspend fun deleteAddress(id: String) {
        api.deleteAddress(id)
        _addresses.value = api.addresses()
    }

    suspend fun loadOrder(id: String) {
        _selectedOrder.value = null
        _selectedCustomOrder.value = null
        _selectedOrder.value = api.order(id)
    }

    suspend fun loadCustomOrder(id: String) {
        _selectedOrder.value = null
        _selectedCustomOrder.value = null
        _selectedCustomOrder.value = api.customOrder(id)
    }

    suspend fun refreshAdmin() {
        runCatching { _adminDashboard.value = api.adminDashboard() }
        runCatching { _adminProducts.value = api.adminProducts() }
        runCatching { _adminCategories.value = api.adminCategories() }
        runCatching { _adminOrders.value = api.adminOrders() }
        runCatching { _adminWorkshops.value = api.adminWorkshops() }
    }

    suspend fun saveAdminProduct(id: String?, request: AdminProductRequest): String? = try {
        if (id == null) api.createAdminProduct(request) else api.updateAdminProduct(id, request)
        refreshAdmin()
        null
    } catch (error: Throwable) { error.message ?: "Gagal menyimpan produk" }

    suspend fun deleteAdminProduct(id: String): String? = try {
        api.deleteAdminProduct(id)
        refreshAdmin()
        null
    } catch (error: Throwable) { error.message ?: "Gagal menonaktifkan produk" }

    suspend fun updateAdminOrderStatus(id: String, status: String): String? = try {
        api.updateAdminOrderStatus(id, status)
        refreshAdmin()
        null
    } catch (error: Throwable) { error.message ?: "Gagal mengubah status pesanan" }

    suspend fun saveAdminWorkshop(id: String?, request: AdminWorkshopRequest): String? = try {
        if (id == null) api.createAdminWorkshop(request) else api.updateAdminWorkshop(id, request)
        refreshAdmin()
        null
    } catch (error: Throwable) { error.message ?: "Gagal menyimpan workshop" }

    suspend fun deleteAdminWorkshop(id: String): String? = try {
        api.deleteAdminWorkshop(id)
        refreshAdmin()
        null
    } catch (error: Throwable) { error.message ?: "Gagal menonaktifkan workshop" }

    private fun mapProduct(dto: ProductDto): Produk = Produk(
        id = dto.id.toInt(),
        nama = dto.name,
        harga = dto.price.toInt(),
        deskripsi = dto.description,
        warna = when (dto.slug) {
            "vest-biru" -> "Biru dan krem"
            "sling-bag-krem" -> "Krem"
            "sepatu-bayi" -> "Kuning dan hijau"
            "keychain-bunga" -> "Pink dan ungu"
            else -> dto.name.substringAfterLast(' ', "")
        },
        stok = dto.stock,
        kategori = dto.category,
        gambar = when (dto.slug) {
            "vest-biru" -> Res.drawable.product_vest_blue
            "sling-bag-krem" -> Res.drawable.product_sling_cream
            "sepatu-bayi" -> Res.drawable.product_baby_shoes
            "keychain-bunga" -> Res.drawable.product_keychain_flower
            "hand-bag-merah" -> Res.drawable.product_handbag_red
            "tas-abu" -> Res.drawable.product_bag_gray
            "topi-kuning" -> Res.drawable.product_hat_yellow
            "totebag-oranye" -> Res.drawable.product_totebag_orange
            else -> Res.drawable.product_vest_blue
        }
    )
}
