package org.tubeskelompok1.rajutmobile.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiEnvelope<T>(
    val data: T,
    val success: Boolean? = true,
    val message: String? = null
)

@Serializable
data class ApiErrorDto(
    val error: String? = "Terjadi kesalahan",
    val message: String? = null
)

@Serializable
data class UserDto(
    val id: String = "",
    val userId: String? = null,
    val name: String = "",
    val fullName: String? = null,
    val email: String = "",
    val phone: String? = null,
    val role: String = "customer",
    val avatarUrl: String? = null
) {
    val primaryId: String
        get() = id.ifBlank { userId.orEmpty() }

    val displayName: String
        get() = name.ifBlank { fullName.orEmpty() }
}

@Serializable
data class UserEnvelope(
    val user: UserDto? = null,
    val data: UserDto? = null
) {
    val currentUser: UserDto
        get() = user ?: data ?: UserDto()
}

@Serializable
data class AuthDto(
    val token: String = "",
    val user: UserDto? = null,
    val data: UserDto? = null
) {
    val currentUser: UserDto
        get() = user ?: data ?: UserDto()
}

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val phone: String? = null,
    val password: String,
    @SerialName("password_confirmation") val passwordConfirmation: String
)

@Serializable
data class ProductImageDto(
    val id: String = "",
    val url: String = "",
    val path: String? = null,
    @SerialName("is_primary") val isPrimary: Boolean? = false
)

@Serializable
data class ProductVariantDto(
    val variantId: String = "",
    val productId: String = "",
    val color: String? = null,
    val size: String? = null,
    val stock: Int = 0
)

@Serializable
data class ProductDto(
    val id: String = "",
    val productId: String? = null,
    val name: String = "",
    val slug: String? = null,
    val description: String? = null,
    @SerialName("short_description") val shortDescription: String? = null,
    @SerialName("full_description") val fullDescription: String? = null,
    val price: Double = 0.0,
    @SerialName("base_price") val basePrice: Double? = null,
    val stock: Int = 0,
    val category: String = "Umum",
    @SerialName("category_slug") val categorySlug: String? = null,
    val image: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    val imageUrls: List<String> = emptyList(),
    val images: List<ProductImageDto> = emptyList(),
    val variants: List<ProductVariantDto> = emptyList(),
    @SerialName("is_active") val isActive: Boolean? = true,
    @SerialName("is_featured") val isFeatured: Boolean? = true,
    @SerialName("availability_type") val availabilityType: String? = "ready_stock",
    @SerialName("preorder_duration") val preorderDuration: String? = null
) {
    val primaryId: String
        get() = id.ifBlank { productId.orEmpty() }

    val effectivePrice: Double
        get() = if (price > 0.0) price else (basePrice ?: 0.0)

    val primaryImageUrl: String?
        get() = imageUrl ?: image ?: images.firstOrNull()?.url ?: imageUrls.firstOrNull()
}

@Serializable
data class CategoryDto(
    val id: String = "",
    val categoryId: String? = null,
    val name: String = "",
    val slug: String? = null,
    val description: String? = null,
    val icon: String? = null,
    val color: String? = null,
    @SerialName("background_color") val backgroundColor: String? = null,
    @SerialName("sort_order") val sortOrder: Int? = 0,
    @SerialName("is_active") val isActive: Boolean? = true
)

@Serializable
data class WorkshopDto(
    val id: String = "",
    val title: String = "",
    val slug: String = "",
    val description: String = "",
    @SerialName("event_date") val eventDate: String = "",
    @SerialName("end_time") val endTime: String = "",
    val location: String = "",
    val price: Double = 0.0,
    @SerialName("is_active") val isActive: Boolean? = true
)

@Serializable
data class CartDataDto(
    val cartId: String? = null,
    val items: List<CartItemDto> = emptyList()
)

@Serializable
data class CartItemDto(
    val quantity: Int = 1,
    val product: ProductDto
)

@Serializable
data class CartItemRequest(
    @SerialName("product_id") val productId: String,
    val quantity: Int
)

@Serializable
data class AddressDto(
    val id: String = "",
    val addressId: String? = null,
    val label: String? = null,
    @SerialName("recipient_name") val recipientName: String = "",
    @SerialName("receiver_name") val receiverName: String? = null,
    val phone: String = "",
    val province: String = "",
    val city: String = "",
    val district: String = "",
    @SerialName("postal_code") val postalCode: String = "",
    @SerialName("address_line") val addressLine: String = "",
    val address: String? = null,
    val notes: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    @SerialName("is_default") val isDefault: Boolean = false
) {
    val primaryId: String
        get() = id.ifBlank { addressId.orEmpty() }

    val effectiveRecipientName: String
        get() = recipientName.ifBlank { receiverName.orEmpty() }

    val effectiveAddressLine: String
        get() = addressLine.ifBlank { address.orEmpty() }
}

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
    @SerialName("is_default") val isDefault: Boolean,
    val notes: String? = null
)

@Serializable
data class CheckoutRequest(
    @SerialName("address_id") val addressId: String,
    @SerialName("delivery_method") val deliveryMethod: String = "gojek",
    val notes: String? = null,
    @SerialName("promo_code") val promoCode: String? = null
)

@Serializable
data class IdDto(
    val id: String = "",
    @SerialName("transaction_id") val transactionId: String? = null
) {
    val primaryId: String
        get() = id.ifBlank { transactionId.orEmpty() }
}

@Serializable
data class PaymentRequest(
    val method: String = "qris"
)

@Serializable
data class PromoDto(
    @SerialName("promo_id") val promoId: String? = null,
    @SerialName("promo_name") val promoName: String? = null,
    @SerialName("promo_code") val promoCode: String? = null,
    @SerialName("discount_amount") val discountAmount: Double? = null,
    @SerialName("discount_percent") val discountPercent: Double? = null,
    @SerialName("valid_until") val validUntil: String? = null
)

@Serializable
data class PromoValidationRequest(
    @SerialName("promo_code") val promoCode: String,
    val subtotal: Double
)

@Serializable
data class OrderItemDto(
    val id: String = "",
    @SerialName("transaction_item_id") val transactionItemId: String? = null,
    @SerialName("variant_id") val variantId: String? = null,
    @SerialName("product_name") val productName: String = "Produk Rajut",
    @SerialName("product_image") val productImage: String? = null,
    val price: Double = 0.0,
    val quantity: Int = 1,
    val subtotal: Double = 0.0
)

@Serializable
data class PaymentDto(
    val id: String = "",
    @SerialName("payment_id") val paymentId: String? = null,
    val method: String = "qris",
    val amount: Double = 0.0,
    val status: String = "pending",
    val createdAt: String? = null
)

@Serializable
data class ShippingAddressDto(
    val label: String? = null,
    @SerialName("recipient_name") val recipientName: String = "",
    @SerialName("street_address") val streetAddress: String? = null,
    @SerialName("address_line") val addressLine: String = "",
    val phone: String = "",
    val district: String = "",
    val city: String = "",
    val province: String = "",
    @SerialName("postal_code") val postalCode: String = ""
)

@Serializable
data class OrderDto(
    val id: String = "",
    @SerialName("transaction_id") val transactionId: String? = null,
    @SerialName("order_number") val orderNumber: String = "",
    val status: String = "Pending",
    val total: Double = 0.0,
    @SerialName("total_price") val totalPrice: Double? = null,
    @SerialName("paid_amount") val paidAmount: Double? = 0.0,
    @SerialName("remaining_payment") val remainingPayment: Double? = 0.0,
    @SerialName("delivery_method") val deliveryMethod: String? = null,
    @SerialName("shipping_cost") val shippingCost: Double? = null,
    @SerialName("customer_name") val customerName: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("transaction_date") val transactionDate: String? = null,
    val address: AddressDto? = null,
    val items: List<OrderItemDto> = emptyList(),
    val payments: List<PaymentDto> = emptyList()
) {
    val primaryId: String
        get() = id.ifBlank { transactionId.orEmpty() }

    val effectiveTotal: Double
        get() = if (total > 0.0) total else (totalPrice ?: 0.0)
}

@Serializable
data class OrderDetailDto(
    val id: String = "",
    @SerialName("transaction_id") val transactionId: String? = null,
    @SerialName("order_number") val orderNumber: String = "",
    val status: String = "Pending",
    val subtotal: Double = 0.0,
    @SerialName("shipping_cost") val shippingCost: Double = 0.0,
    val total: Double = 0.0,
    @SerialName("total_price") val totalPrice: Double? = null,
    @SerialName("paid_amount") val paidAmount: Double? = 0.0,
    @SerialName("remaining_payment") val remainingPayment: Double? = 0.0,
    @SerialName("delivery_method") val deliveryMethod: String? = null,
    val address: AddressDto? = null,
    @SerialName("shipping_address") val shippingAddress: ShippingAddressDto? = null,
    val items: List<OrderItemDto> = emptyList(),
    val payments: List<PaymentDto> = emptyList()
) {
    val primaryId: String
        get() = id.ifBlank { transactionId.orEmpty() }

    val effectiveTotal: Double
        get() = if (total > 0.0) total else (totalPrice ?: 0.0)

    val effectiveAddress: ShippingAddressDto
        get() = shippingAddress ?: ShippingAddressDto(
            label = address?.label,
            recipientName = address?.effectiveRecipientName.orEmpty(),
            phone = address?.phone.orEmpty(),
            addressLine = address?.effectiveAddressLine.orEmpty(),
            district = address?.district.orEmpty(),
            city = address?.city.orEmpty(),
            province = address?.province.orEmpty(),
            postalCode = address?.postalCode.orEmpty()
        )
}

@Serializable
data class CustomResponseDto(
    val responseId: String = "",
    val requestId: String = "",
    val adminId: String? = null,
    val responseMessage: String = "",
    val responseImages: List<String> = emptyList(),
    @SerialName("estimated_price") val estimatedPrice: Double? = null,
    @SerialName("estimated_finish_date") val estimatedFinishDate: String? = null,
    @SerialName("status_after_response") val statusAfterResponse: String? = null,
    val createdAt: String? = null
)

@Serializable
data class CustomOrderDto(
    val id: String = "",
    val requestId: String? = null,
    @SerialName("user_id") val userId: String? = null,
    @SerialName("custom_name") val customName: String? = null,
    @SerialName("product_type") val productType: String? = null,
    val description: String = "",
    val color: String = "",
    val size: String = "",
    val status: String = "Pending",
    @SerialName("reference_images") val referenceImages: List<String> = emptyList(),
    @SerialName("referenceImages") val refImagesCamel: List<String> = emptyList(),
    @SerialName("estimated_price") val estimatedPrice: Double? = null,
    @SerialName("estimated_days") val estimatedDays: String? = null,
    val responses: List<CustomResponseDto> = emptyList(),
    val createdAt: String? = null
) {
    val primaryId: String
        get() = id.ifBlank { requestId.orEmpty() }

    val effectiveTitle: String
        get() = customName ?: productType ?: "Pesanan Custom"
}

@Serializable
data class CustomOrderRequest(
    @SerialName("custom_name") val customName: String = "",
    @SerialName("product_type") val productType: String? = null,
    val description: String = "",
    val color: String? = null,
    val size: String? = null,
    val notes: String? = null,
    @SerialName("reference_images") val referenceImages: List<String> = emptyList()
)

@Serializable
data class AdminDashboardDto(
    @SerialName("product_count") val productCount: Int = 0,
    @SerialName("low_stock_count") val lowStockCount: Int = 0,
    @SerialName("order_count") val orderCount: Int = 0,
    @SerialName("workshop_count") val workshopCount: Int = 0,
    val revenue: Double = 0.0
)

@Serializable
data class AdminProductDto(
    val id: String = "",
    val name: String = "",
    val slug: String = "",
    @SerialName("category_id") val categoryId: String = "",
    val category: String = "Umum",
    @SerialName("short_description") val shortDescription: String? = null,
    val description: String = "",
    val price: Double = 0.0,
    val stock: Int = 0,
    @SerialName("availability_type") val availabilityType: String = "ready_stock",
    @SerialName("preorder_duration") val preorderDuration: String? = null,
    @SerialName("is_featured") val isFeatured: Boolean = true,
    @SerialName("is_active") val isActive: Boolean = true,
    @SerialName("image_path") val imagePath: String? = null,
    val image: String? = null,
    val imageUrls: List<String> = emptyList()
)

@Serializable
data class AdminProductRequest(
    val name: String,
    val slug: String? = null,
    @SerialName("category_id") val categoryId: String? = null,
    val category: String? = null,
    @SerialName("short_description") val shortDescription: String? = null,
    val description: String = "",
    val price: Double = 0.0,
    val stock: Int = 0,
    @SerialName("availability_type") val availabilityType: String = "ready_stock",
    @SerialName("preorder_duration") val preorderDuration: String? = null,
    @SerialName("is_featured") val isFeatured: Boolean = true,
    @SerialName("is_active") val isActive: Boolean = true
)

@Serializable
data class AdminCategoryDto(
    val id: String = "",
    val categoryId: String? = null,
    val name: String = "",
    val slug: String = "",
    val description: String? = null,
    val icon: String? = null,
    val color: String? = null,
    @SerialName("is_active") val isActive: Boolean = true
)

@Serializable
data class AdminOrderDto(
    val id: String = "",
    @SerialName("transaction_id") val transactionId: String? = null,
    @SerialName("order_number") val orderNumber: String = "",
    val status: String = "Pending",
    @SerialName("customer_name") val customerName: String = "",
    @SerialName("full_name") val fullName: String? = null,
    @SerialName("customer_email") val customerEmail: String = "",
    val total: Double = 0.0,
    @SerialName("total_price") val totalPrice: Double? = null,
    @SerialName("paid_amount") val paidAmount: Double = 0.0,
    @SerialName("remaining_payment") val remainingPayment: Double = 0.0,
    @SerialName("transaction_date") val transactionDate: String? = null
)

@Serializable
data class StatusRequest(val status: String)

@Serializable
data class AdminWorkshopDto(
    val id: String = "",
    val title: String = "",
    val slug: String = "",
    val description: String = "",
    @SerialName("event_date") val eventDate: String = "",
    @SerialName("end_time") val endTime: String = "",
    val location: String = "",
    val price: Double = 0.0,
    @SerialName("is_active") val isActive: Boolean = true
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
    @SerialName("is_active") val isActive: Boolean = true
)

@Serializable
data class SiteSettingsDto(
    @SerialName("brand_name") val brandName: String? = "Arajut",
    val tagline: String? = null,
    @SerialName("hero_heading") val heroHeading: String? = null,
    @SerialName("hero_highlight") val heroHighlight: String? = null,
    @SerialName("hero_description") val heroDescription: String? = null,
    @SerialName("whatsapp_number") val whatsappNumber: String? = null,
    @SerialName("instagram_url") val instagramUrl: String? = null
)

@Serializable
data class StorefrontDto(
    val products: List<ProductDto> = emptyList(),
    val categories: List<CategoryDto> = emptyList(),
    val settings: SiteSettingsDto? = null
)
