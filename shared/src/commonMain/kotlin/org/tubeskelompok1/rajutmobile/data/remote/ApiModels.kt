package org.tubeskelompok1.rajutmobile.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable data class ApiEnvelope<T>(val data: T)
@Serializable data class ApiErrorDto(val error: String = "Terjadi kesalahan")

@Serializable
data class UserDto(val id: String, val name: String, val email: String, val phone: String? = null, val role: String)

@Serializable
data class UserEnvelope(val user: UserDto)

@Serializable
data class AuthDto(val token: String, val user: UserDto)

@Serializable
data class LoginRequest(val email: String, val password: String)

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    @SerialName("password_confirmation") val passwordConfirmation: String
)

@Serializable
data class ProductDto(
    val id: String,
    val name: String,
    val slug: String,
    val description: String,
    @SerialName("short_description") val shortDescription: String? = null,
    val price: Double,
    val stock: Int,
    val category: String,
    @SerialName("category_slug") val categorySlug: String = "",
    @SerialName("image_url") val imageUrl: String? = null
)

@Serializable
data class WorkshopDto(
    val id: String,
    val title: String,
    val slug: String,
    val description: String,
    @SerialName("event_date") val eventDate: String,
    @SerialName("end_time") val endTime: String,
    val location: String,
    val price: Double
)

@Serializable data class CartDataDto(val items: List<CartItemDto>)
@Serializable data class CartItemDto(val quantity: Int, val product: CartProductDto)
@Serializable data class CartProductDto(val id: String, val name: String, val slug: String, val price: Double, val stock: Int)
@Serializable data class CartItemRequest(@SerialName("product_id") val productId: String, val quantity: Int)

@Serializable
data class AddressDto(
    val id: String,
    val label: String? = null,
    @SerialName("recipientName") val recipientName: String,
    val phone: String,
    val province: String,
    val city: String,
    val district: String,
    @SerialName("postalCode") val postalCode: String,
    @SerialName("addressLine") val addressLine: String,
    @SerialName("isDefault") val isDefault: Boolean
)

@Serializable
data class AddressRequest(
    val label: String,
    @SerialName("recipient_name") val recipientName: String,
    val phone: String,
    val province: String,
    val city: String,
    val district: String,
    @SerialName("postal_code") val postalCode: String,
    @SerialName("address_line") val addressLine: String,
    @SerialName("is_default") val isDefault: Boolean
)

@Serializable data class CheckoutRequest(@SerialName("address_id") val addressId: String, @SerialName("delivery_method") val deliveryMethod: String, val notes: String? = null)
@Serializable data class IdDto(val id: String)
@Serializable data class PaymentRequest(val method: String)

@Serializable
data class OrderDto(
    val id: String,
    @SerialName("order_number") val orderNumber: String,
    val status: String,
    val total: Double,
    @SerialName("created_at") val createdAt: String? = null
)

@Serializable
data class ShippingAddressDto(
    val label: String? = null,
    @SerialName("recipient_name") val recipientName: String,
    val phone: String,
    @SerialName("address_line") val addressLine: String,
    val district: String,
    val city: String,
    val province: String,
    @SerialName("postal_code") val postalCode: String
)

@Serializable
data class OrderItemDto(
    val id: String,
    @SerialName("product_name") val productName: String,
    @SerialName("product_image") val productImage: String? = null,
    val price: Double,
    val quantity: Int,
    val subtotal: Double
)

@Serializable
data class OrderDetailDto(
    val id: String,
    @SerialName("order_number") val orderNumber: String,
    val status: String,
    val subtotal: Double,
    @SerialName("shipping_cost") val shippingCost: Double,
    val total: Double,
    @SerialName("shipping_address") val shippingAddress: ShippingAddressDto,
    val items: List<OrderItemDto>
)

@Serializable
data class CustomOrderDto(
    val id: String,
    @SerialName("product_type") val productType: String,
    val color: String,
    val size: String,
    val status: String,
    @SerialName("estimated_price") val estimatedPrice: Double? = null,
    @SerialName("estimated_days") val estimatedDays: String? = null
)

@Serializable
data class CustomOrderRequest(
    @SerialName("product_type") val productType: String,
    val color: String,
    val size: String,
    val notes: String? = null,
    @SerialName("reference_image") val referenceImage: String? = null
)

@Serializable
data class AdminDashboardDto(
    @SerialName("product_count") val productCount: Int,
    @SerialName("low_stock_count") val lowStockCount: Int,
    @SerialName("order_count") val orderCount: Int,
    @SerialName("workshop_count") val workshopCount: Int,
    val revenue: Double
)

@Serializable
data class AdminProductDto(
    val id: String,
    val name: String,
    val slug: String,
    @SerialName("category_id") val categoryId: String,
    val category: String,
    @SerialName("short_description") val shortDescription: String? = null,
    val description: String,
    val price: Double,
    val stock: Int,
    @SerialName("availability_type") val availabilityType: String,
    @SerialName("preorder_duration") val preorderDuration: String? = null,
    @SerialName("is_featured") val isFeatured: Boolean,
    @SerialName("is_active") val isActive: Boolean,
    @SerialName("image_path") val imagePath: String? = null
)

@Serializable
data class AdminProductRequest(
    val name: String,
    val slug: String,
    @SerialName("category_id") val categoryId: String,
    @SerialName("short_description") val shortDescription: String,
    val description: String,
    val price: Double,
    val stock: Int,
    @SerialName("availability_type") val availabilityType: String = "ready_stock",
    @SerialName("preorder_duration") val preorderDuration: String? = null,
    @SerialName("is_featured") val isFeatured: Boolean,
    @SerialName("is_active") val isActive: Boolean
)

@Serializable
data class AdminCategoryDto(
    val id: String,
    val name: String,
    val slug: String,
    @SerialName("is_active") val isActive: Boolean
)

@Serializable
data class AdminOrderDto(
    val id: String,
    @SerialName("order_number") val orderNumber: String,
    val status: String,
    @SerialName("customer_name") val customerName: String,
    @SerialName("customer_email") val customerEmail: String,
    val total: Double,
    @SerialName("paid_amount") val paidAmount: Double,
    @SerialName("remaining_payment") val remainingPayment: Double
)

@Serializable data class StatusRequest(val status: String)

@Serializable
data class AdminWorkshopDto(
    val id: String,
    val title: String,
    val slug: String,
    val description: String,
    @SerialName("event_date") val eventDate: String,
    @SerialName("end_time") val endTime: String,
    val location: String,
    val price: Double,
    @SerialName("is_active") val isActive: Boolean
)

@Serializable
data class AdminWorkshopRequest(
    val title: String,
    val slug: String,
    val description: String,
    @SerialName("event_date") val eventDate: String,
    @SerialName("end_time") val endTime: String,
    val location: String,
    val price: Double,
    @SerialName("is_active") val isActive: Boolean
)
