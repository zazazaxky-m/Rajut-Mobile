package org.tubeskelompok1.rajutmobile.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.tubeskelompok1.rajutmobile.data.local.AppPreferences
import org.tubeskelompok1.rajutmobile.data.remote.AddressDto
import org.tubeskelompok1.rajutmobile.data.remote.AddressRequest
import org.tubeskelompok1.rajutmobile.data.remote.AdminCategoryDto
import org.tubeskelompok1.rajutmobile.data.remote.AdminDashboardDto
import org.tubeskelompok1.rajutmobile.data.remote.AdminOrderDto
import org.tubeskelompok1.rajutmobile.data.remote.AdminProductDto
import org.tubeskelompok1.rajutmobile.data.remote.AdminProductRequest
import org.tubeskelompok1.rajutmobile.data.remote.AdminWorkshopDto
import org.tubeskelompok1.rajutmobile.data.remote.AdminWorkshopRequest
import org.tubeskelompok1.rajutmobile.data.remote.ArajutApi
import org.tubeskelompok1.rajutmobile.data.remote.CategoryDto
import org.tubeskelompok1.rajutmobile.data.remote.CheckoutRequest
import org.tubeskelompok1.rajutmobile.data.remote.CustomOrderDto
import org.tubeskelompok1.rajutmobile.data.remote.CustomOrderRequest
import org.tubeskelompok1.rajutmobile.data.remote.OrderDto
import org.tubeskelompok1.rajutmobile.data.remote.OrderDetailDto
import org.tubeskelompok1.rajutmobile.data.remote.ProductDto
import org.tubeskelompok1.rajutmobile.data.remote.RegisterRequest
import org.tubeskelompok1.rajutmobile.data.remote.UserDto
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

    private val _categories = MutableStateFlow<List<CategoryDto>>(emptyList())
    val categories: StateFlow<List<CategoryDto>> = _categories.asStateFlow()

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
        _user.value = auth.currentUser
        refreshAll()
        null
    } catch (error: Throwable) {
        error.message ?: "Login gagal. Periksa koneksi dan akun Anda."
    }

    suspend fun register(name: String, email: String, phone: String, password: String): String? = try {
        val auth = api.register(RegisterRequest(name, email, phone, password, password))
        preferences.saveToken(auth.token)
        preferences.completeOnboarding()
        _user.value = auth.currentUser
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
        if (_user.value?.role?.equals("admin", ignoreCase = true) == true) {
            refreshAdmin()
            return
        }
        refreshCatalog()
        refreshSecureData()
    }

    suspend fun refreshCatalog() {
        runCatching {
            val remote = api.products()
            if (remote.isNotEmpty()) {
                _products.value = remote.map(::mapProduct)
            }
        }
        runCatching {
            _categories.value = api.categories()
        }
        runCatching {
            _workshops.value = org.tubeskelompok1.rajutmobile.model.WorkshopData.items
        }
    }

    suspend fun refreshSecureData() {
        runCatching { refreshCart() }
        runCatching { _addresses.value = api.addresses() }
        runCatching { _orders.value = api.orders() }
        runCatching { _customOrders.value = api.customOrders() }
    }

    suspend fun addToCart(product: Produk): String? = try {
        api.updateCart(product.id, 1)
        refreshCart()
        null
    } catch (error: Throwable) {
        error.message ?: "Gagal menambahkan produk"
    }

    suspend fun updateCart(product: Produk, quantity: Int) {
        if (quantity <= 0) {
            api.deleteCart(product.id)
        } else {
            api.updateCart(product.id, quantity)
        }
        refreshCart()
    }

    suspend fun deleteCart(product: Produk) {
        api.deleteCart(product.id)
        refreshCart()
    }

    private suspend fun refreshCart() {
        val knownProducts = _products.value.associateBy { it.id }
        _cart.value = api.cart().items.map { item ->
            val pDto = item.product
            val pId = pDto.primaryId
            val product = knownProducts[pId] ?: mapProduct(pDto)
            ItemKeranjang(product, item.quantity)
        }
    }

    suspend fun checkout(deliveryMethod: String): String? = try {
        val address = _addresses.value.firstOrNull() ?: return null
        lastOrderId = api.checkout(CheckoutRequest(address.primaryId, deliveryMethod))
        lastOrderId
    } catch (_: Throwable) {
        null
    }

    suspend fun confirmPayment(): Boolean = try {
        api.createPayment(lastOrderId ?: return false)
        _orders.value = api.orders()
        _cart.value = emptyList()
        true
    } catch (_: Throwable) {
        false
    }

    suspend fun submitCustomOrder(productType: String, color: String, size: String, notes: String): String? = try {
        api.createCustomOrder(
            CustomOrderRequest(
                customName = productType,
                productType = productType,
                description = notes.ifBlank { "Permintaan custom $productType ($color, $size)" },
                color = color,
                size = size,
                notes = notes
            )
        )
        _customOrders.value = api.customOrders()
        null
    } catch (error: Throwable) {
        error.message ?: "Gagal mengirim permintaan custom"
    }

    suspend fun saveAddress(id: String?, request: AddressRequest): String? = try {
        if (id == null) api.createAddress(request) else api.updateAddress(id, request)
        _addresses.value = api.addresses()
        null
    } catch (error: Throwable) {
        error.message ?: "Gagal menyimpan alamat"
    }

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
        val products = runCatching { api.adminProducts() }.getOrDefault(emptyList())
        val categories = runCatching { api.adminCategories() }.getOrDefault(emptyList())
        val orders = runCatching { api.adminOrders() }.getOrDefault(emptyList())

        _adminProducts.value = products
        _adminCategories.value = categories
        _adminOrders.value = orders

        val activeCount = products.count { it.isActive }
        val lowStockCount = products.count { it.isActive && it.stock <= 5 }
        val orderCount = orders.size
        val totalRevenue = orders.sumOf { it.paidAmount }

        _adminDashboard.value = AdminDashboardDto(
            productCount = activeCount,
            lowStockCount = lowStockCount,
            orderCount = orderCount,
            workshopCount = _adminWorkshops.value.size,
            revenue = totalRevenue
        )
    }

    suspend fun saveAdminProduct(id: String?, request: AdminProductRequest): String? = try {
        if (id == null) api.createAdminProduct(request) else api.updateAdminProduct(id, request)
        refreshAdmin()
        null
    } catch (error: Throwable) {
        error.message ?: "Gagal menyimpan produk"
    }

    suspend fun deleteAdminProduct(id: String): String? = try {
        api.deleteAdminProduct(id)
        refreshAdmin()
        null
    } catch (error: Throwable) {
        error.message ?: "Gagal menonaktifkan produk"
    }

    suspend fun updateAdminOrderStatus(id: String, status: String): String? = try {
        api.updateAdminOrderStatus(id, status)
        refreshAdmin()
        null
    } catch (error: Throwable) {
        error.message ?: "Gagal mengubah status pesanan"
    }

    suspend fun saveAdminWorkshop(id: String?, request: AdminWorkshopRequest): String? = try {
        refreshAdmin()
        null
    } catch (error: Throwable) {
        error.message ?: "Gagal menyimpan workshop"
    }

    suspend fun deleteAdminWorkshop(id: String): String? = try {
        refreshAdmin()
        null
    } catch (error: Throwable) {
        error.message ?: "Gagal menonaktifkan workshop"
    }

    private fun mapProduct(dto: ProductDto): Produk {
        val slug = dto.slug?.lowercase() ?: dto.name.lowercase().replace(" ", "-")
        val nameLower = dto.name.lowercase()
        return Produk(
            id = dto.primaryId,
            nama = dto.name,
            harga = dto.effectivePrice.toInt(),
            deskripsi = dto.description ?: dto.shortDescription ?: "Produk rajut berkualitas buatan tangan.",
            warna = when {
                slug.contains("vest") || nameLower.contains("vest") -> "Biru dan krem"
                slug.contains("sling") || nameLower.contains("sling") -> "Krem"
                slug.contains("sepatu") || nameLower.contains("sepatu") -> "Kuning dan hijau"
                slug.contains("keychain") || nameLower.contains("keychain") -> "Pink dan ungu"
                slug.contains("hand-bag") || nameLower.contains("hand bag") -> "Merah"
                slug.contains("tas-abu") || nameLower.contains("tas abu") -> "Abu-abu"
                slug.contains("topi") || nameLower.contains("topi") -> "Kuning"
                slug.contains("totebag") || nameLower.contains("totebag") -> "Oranye"
                else -> dto.name.substringAfterLast(' ', "Warna-warni")
            },
            stok = dto.stock,
            kategori = dto.category,
            gambar = when {
                slug.contains("vest") || nameLower.contains("vest") -> Res.drawable.product_vest_blue
                slug.contains("sling") || nameLower.contains("sling") -> Res.drawable.product_sling_cream
                slug.contains("sepatu") || nameLower.contains("sepatu") -> Res.drawable.product_baby_shoes
                slug.contains("keychain") || nameLower.contains("keychain") -> Res.drawable.product_keychain_flower
                slug.contains("hand-bag") || nameLower.contains("hand bag") -> Res.drawable.product_handbag_red
                slug.contains("tas-abu") || nameLower.contains("tas abu") -> Res.drawable.product_bag_gray
                slug.contains("topi") || nameLower.contains("topi") -> Res.drawable.product_hat_yellow
                slug.contains("totebag") || nameLower.contains("totebag") -> Res.drawable.product_totebag_orange
                else -> Res.drawable.product_vest_blue
            },
            imageUrl = dto.primaryImageUrl
        )
    }
}
