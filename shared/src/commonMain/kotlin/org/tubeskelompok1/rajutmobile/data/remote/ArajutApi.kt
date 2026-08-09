package org.tubeskelompok1.rajutmobile.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

class ArajutApi(private val client: HttpClient) {
    suspend fun login(email: String, password: String): AuthDto =
        client.post("auth/login") { setBody(LoginRequest(email, password)) }.body()

    suspend fun register(request: RegisterRequest): AuthDto =
        client.post("auth/register") { setBody(request) }.body()

    suspend fun me(): UserDto = client.get("auth/me").body<UserEnvelope>().user
    suspend fun logout() { client.post("auth/logout") }
    suspend fun products(): List<ProductDto> = client.get("products").body<ApiEnvelope<List<ProductDto>>>().data
    suspend fun workshops(): List<WorkshopDto> = client.get("workshops").body<ApiEnvelope<List<WorkshopDto>>>().data
    suspend fun cart(): CartDataDto = client.get("cart").body<ApiEnvelope<CartDataDto>>().data
    suspend fun updateCart(productId: String, quantity: Int) { client.put("cart/items") { setBody(CartItemRequest(productId, quantity)) } }
    suspend fun deleteCart(productId: String) { client.delete("cart/items/$productId") }
    suspend fun addresses(): List<AddressDto> = client.get("addresses").body<ApiEnvelope<List<AddressDto>>>().data
    suspend fun createAddress(request: AddressRequest): AddressDto = client.post("addresses") { setBody(request) }.body<ApiEnvelope<AddressDto>>().data
    suspend fun updateAddress(id: String, request: AddressRequest): AddressDto = client.put("addresses/$id") { setBody(request) }.body<ApiEnvelope<AddressDto>>().data
    suspend fun deleteAddress(id: String) { client.delete("addresses/$id") }
    suspend fun checkout(request: CheckoutRequest): String = client.post("orders/checkout") { setBody(request) }.body<ApiEnvelope<IdDto>>().data.id
    suspend fun createPayment(orderId: String) { client.post("orders/$orderId/payments") { setBody(PaymentRequest("qris")) } }
    suspend fun orders(): List<OrderDto> = client.get("orders").body<ApiEnvelope<List<OrderDto>>>().data
    suspend fun order(id: String): OrderDetailDto = client.get("orders/$id").body<ApiEnvelope<OrderDetailDto>>().data
    suspend fun customOrders(): List<CustomOrderDto> = client.get("custom-orders").body<ApiEnvelope<List<CustomOrderDto>>>().data
    suspend fun customOrder(id: String): CustomOrderDto = client.get("custom-orders/$id").body<ApiEnvelope<CustomOrderDto>>().data
    suspend fun createCustomOrder(request: CustomOrderRequest) { client.post("custom-orders") { setBody(request) } }

    suspend fun adminDashboard(): AdminDashboardDto = client.get("admin/dashboard").body<ApiEnvelope<AdminDashboardDto>>().data
    suspend fun adminProducts(): List<AdminProductDto> = client.get("admin/products").body<ApiEnvelope<List<AdminProductDto>>>().data
    suspend fun adminCategories(): List<AdminCategoryDto> = client.get("admin/categories").body<ApiEnvelope<List<AdminCategoryDto>>>().data
    suspend fun createAdminProduct(request: AdminProductRequest) { client.post("admin/products") { setBody(request) } }
    suspend fun updateAdminProduct(id: String, request: AdminProductRequest) { client.put("admin/products/$id") { setBody(request) } }
    suspend fun deleteAdminProduct(id: String) { client.delete("admin/products/$id") }
    suspend fun adminOrders(): List<AdminOrderDto> = client.get("admin/orders").body<ApiEnvelope<List<AdminOrderDto>>>().data
    suspend fun updateAdminOrderStatus(id: String, status: String) { client.patch("admin/orders/$id/status") { setBody(StatusRequest(status)) } }
    suspend fun adminWorkshops(): List<AdminWorkshopDto> = client.get("admin/workshops").body<ApiEnvelope<List<AdminWorkshopDto>>>().data
    suspend fun createAdminWorkshop(request: AdminWorkshopRequest) { client.post("admin/workshops") { setBody(request) } }
    suspend fun updateAdminWorkshop(id: String, request: AdminWorkshopRequest) { client.put("admin/workshops/$id") { setBody(request) } }
    suspend fun deleteAdminWorkshop(id: String) { client.delete("admin/workshops/$id") }
}
