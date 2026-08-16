package org.tubeskelompok1.rajutmobile.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

class ArajutApi(private val client: HttpClient) {
    suspend fun login(email: String, password: String): AuthDto =
        client.post("auth/login") { setBody(LoginRequest(email, password)) }.body()

    suspend fun register(request: RegisterRequest): AuthDto =
        client.post("auth/register") { setBody(request) }.body()

    suspend fun me(): UserDto =
        client.get("auth/me").body<UserEnvelope>().currentUser

    suspend fun logout() {
        client.post("auth/logout")
    }

    suspend fun products(category: String? = null, search: String? = null): List<ProductDto> =
        client.get("products") {
            if (!category.isNullOrBlank() && category != "Semua") parameter("category", category)
            if (!search.isNullOrBlank()) parameter("search", search)
        }.body<ApiEnvelope<List<ProductDto>>>().data

    suspend fun product(id: String): ProductDto =
        client.get("products/$id").body<ApiEnvelope<ProductDto>>().data

    suspend fun categories(): List<CategoryDto> =
        client.get("categories").body<ApiEnvelope<List<CategoryDto>>>().data

    suspend fun storefront(): StorefrontDto =
        client.get("storefront").body<ApiEnvelope<StorefrontDto>>().data

    suspend fun cart(): CartDataDto =
        client.get("cart").body<ApiEnvelope<CartDataDto>>().data

    suspend fun updateCart(productId: String, quantity: Int): CartDataDto =
        client.put("cart/items") { setBody(CartItemRequest(productId, quantity)) }.body<ApiEnvelope<CartDataDto>>().data

    suspend fun deleteCart(productId: String): CartDataDto =
        client.delete("cart/items/$productId").body<ApiEnvelope<CartDataDto>>().data

    suspend fun addresses(): List<AddressDto> =
        client.get("addresses").body<ApiEnvelope<List<AddressDto>>>().data

    suspend fun createAddress(request: AddressRequest): AddressDto =
        client.post("addresses") { setBody(request) }.body<ApiEnvelope<AddressDto>>().data

    suspend fun updateAddress(id: String, request: AddressRequest): AddressDto =
        client.put("addresses/$id") { setBody(request) }.body<ApiEnvelope<AddressDto>>().data

    suspend fun deleteAddress(id: String) {
        client.delete("addresses/$id")
    }

    suspend fun validatePromo(promoCode: String, subtotal: Double): PromoDto =
        client.post("promos/validate") { setBody(PromoValidationRequest(promoCode, subtotal)) }.body<ApiEnvelope<PromoDto>>().data

    suspend fun checkout(request: CheckoutRequest): String =
        client.post("checkout") { setBody(request) }.body<ApiEnvelope<OrderDetailDto>>().data.primaryId

    suspend fun createPayment(orderId: String) {
        client.post("orders/$orderId/payments") { setBody(PaymentRequest("qris")) }
    }

    suspend fun orders(): List<OrderDto> =
        client.get("orders").body<ApiEnvelope<List<OrderDto>>>().data

    suspend fun order(id: String): OrderDetailDto =
        client.get("orders/$id").body<ApiEnvelope<OrderDetailDto>>().data

    suspend fun customOrders(): List<CustomOrderDto> =
        client.get("custom-products/request").body<ApiEnvelope<List<CustomOrderDto>>>().data

    suspend fun customOrder(id: String): CustomOrderDto =
        client.get("custom-products/request/$id").body<ApiEnvelope<CustomOrderDto>>().data

    suspend fun createCustomOrder(request: CustomOrderRequest) {
        client.post("custom-products/request") { setBody(request) }
    }

    suspend fun adminProducts(): List<AdminProductDto> {
        val products = client.get("products").body<ApiEnvelope<List<ProductDto>>>().data
        return products.map { p ->
            AdminProductDto(
                id = p.primaryId,
                name = p.name,
                slug = p.slug.orEmpty(),
                categoryId = p.category,
                category = p.category,
                shortDescription = p.shortDescription,
                description = p.description.orEmpty(),
                price = p.effectivePrice,
                stock = p.stock,
                isFeatured = p.isFeatured ?: true,
                isActive = p.isActive ?: true,
                imageUrls = p.imageUrls,
                image = p.primaryImageUrl
            )
        }
    }

    suspend fun adminCategories(): List<AdminCategoryDto> {
        val categories = client.get("categories").body<ApiEnvelope<List<CategoryDto>>>().data
        return categories.map { c ->
            AdminCategoryDto(
                id = c.id.ifBlank { c.categoryId.orEmpty() },
                categoryId = c.categoryId,
                name = c.name,
                slug = c.slug.orEmpty(),
                description = c.description,
                icon = c.icon,
                color = c.color ?: c.backgroundColor,
                isActive = c.isActive ?: true
            )
        }
    }

    suspend fun createAdminProduct(request: AdminProductRequest) {
        client.post("admin/products") { setBody(request) }
    }

    suspend fun updateAdminProduct(id: String, request: AdminProductRequest) {
        client.put("admin/products/$id") { setBody(request) }
    }

    suspend fun deleteAdminProduct(id: String) {
        client.delete("admin/products/$id")
    }

    suspend fun createAdminCategory(name: String, slug: String? = null, description: String? = null) {
        client.post("admin/categories") {
            setBody(mapOf("name" to name, "slug" to (slug ?: name.lowercase().replace(" ", "-")), "description" to description))
        }
    }

    suspend fun deleteAdminCategory(id: String) {
        client.delete("admin/categories/$id")
    }

    suspend fun adminOrders(): List<AdminOrderDto> =
        client.get("admin/orders").body<ApiEnvelope<List<AdminOrderDto>>>().data

    suspend fun updateAdminOrderStatus(id: String, status: String) {
        client.patch("admin/transactions/$id/status") { setBody(StatusRequest(status)) }
    }

    suspend fun adminPromos(): List<PromoDto> =
        client.get("admin/promos").body<ApiEnvelope<List<PromoDto>>>().data

    suspend fun createAdminPromo(request: PromoDto) {
        client.post("admin/promos") { setBody(request) }
    }

    suspend fun deleteAdminPromo(id: String) {
        client.delete("admin/promos/$id")
    }
}
