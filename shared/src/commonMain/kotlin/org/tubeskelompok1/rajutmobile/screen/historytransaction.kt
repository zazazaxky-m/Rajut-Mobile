package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.tubeskelompok1.rajutmobile.model.DataMockup
import org.tubeskelompok1.rajutmobile.model.Produk
import org.tubeskelompok1.rajutmobile.model.formatRupiah
import org.tubeskelompok1.rajutmobile.ui.AppColors
import org.tubeskelompok1.rajutmobile.data.remote.OrderDto
import org.tubeskelompok1.rajutmobile.data.remote.OrderDetailDto
import org.tubeskelompok1.rajutmobile.data.remote.CustomOrderDto

@Composable
fun RiwayatScreen(
    onHome: () -> Unit,
    onProfile: () -> Unit,
    onOrderClick: (String) -> Unit,
    orders: List<OrderDto> = emptyList(),
    customOrders: List<CustomOrderDto> = emptyList()
) {
    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            Surface(
                color = Color(0xFFFFE3E7),
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Riwayat Pesanan",
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.TextPrimary
                    )
                }
            }
        },
        bottomBar = {
            ArajutBottomBar("orders", onHome, {}, onProfile)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(22.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(orders, key = { "order-${it.id}" }) { order ->
                OrderHistoryCard(
                    orderId = "#${order.orderNumber}",
                    status = orderStatusLabel(order.status),
                    product = DataMockup.daftarProduk.first(),
                    details = listOf("1x"),
                    total = order.total.toInt(),
                    onClick = { onOrderClick(order.id) }
                )
            }
            items(customOrders, key = { "custom-${it.primaryId}" }) { order ->
                OrderHistoryCard(
                    orderId = if (order.primaryId.length > 8) "#CSM-${order.primaryId.take(8).uppercase()}" else "#CSM${order.primaryId.padStart(7, '0')}",
                    status = "Custom",
                    product = DataMockup.customProduct,
                    details = listOf(order.color, order.size).filter { it.isNotBlank() }.ifEmpty { listOf("Custom") },
                    total = order.estimatedPrice?.toInt() ?: 0,
                    onClick = { onOrderClick("custom-${order.primaryId}") }
                )
            }
            if (orders.isEmpty() && customOrders.isEmpty()) {
                item { Text("Belum ada pesanan.", color = AppColors.TextSecondary) }
            }
        }
    }
}

private fun orderStatusLabel(status: String) = when (status) {
    "pending" -> "Menunggu"
    "processing" -> "Diproses"
    "packed" -> "Dikemas"
    "shipped" -> "Dikirim"
    "completed" -> "Selesai"
    "cancelled" -> "Dibatalkan"
    else -> status.replaceFirstChar { it.uppercase() }
}

@Composable
private fun OrderHistoryCard(
    orderId: String,
    status: String,
    product: Produk,
    details: List<String>,
    total: Int,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.AutoMirrored.Outlined.ReceiptLong,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color(0xFF974552)
                )
                Spacer(Modifier.width(10.dp))
                Text(orderId, color = Color(0xFF8D3440), fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.weight(1f))
                Surface(color = Color(0xFFEBD9DC), shape = RoundedCornerShape(12.dp)) {
                    Text(
                        status,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF8D5060),
                        modifier = Modifier.padding(horizontal = 11.dp, vertical = 4.dp)
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(color = Color(0xFFC9AEB3))
            Spacer(Modifier.height(12.dp))
            Row {
                Image(
                    painter = painterResource(product.gambar),
                    contentDescription = product.nama,
                    modifier = Modifier.size(88.dp).clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        if (product.id == "1") "Blue Vest" else product.nama,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    details.forEach { Text(it, style = MaterialTheme.typography.bodySmall) }
                    Spacer(Modifier.height(10.dp))
                    Text(formatRupiah(total), color = Color(0xFF8D3440), fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth().height(36.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD27A85)),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
            ) {
                Text("Lihat Detail Pesanan", style = MaterialTheme.typography.labelSmall, color = Color.White)
            }
        }
    }
}

@Composable
fun OrderDetailScreen(
    onBack: () -> Unit,
    order: OrderDetailDto? = null,
    customOrder: CustomOrderDto? = null
) {
    if (customOrder != null) {
        CustomOrderDetailContent(onBack, customOrder)
        return
    }
    if (order == null) {
        Scaffold(containerColor = AppColors.Background, topBar = { ArajutTopBar("Detail Pesanan", onBack) }) { padding ->
            Column(Modifier.fillMaxSize().padding(padding), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Memuat detail pesanan...")
            }
        }
        return
    }
    val item = order.items.firstOrNull()
    val product = DataMockup.daftarProduk.firstOrNull { it.nama.equals(item?.productName, ignoreCase = true) }
        ?: DataMockup.daftarProduk.first()
    val address = order.effectiveAddress
    Scaffold(
        containerColor = AppColors.Background,
        topBar = { ArajutTopBar("Detail Pesanan", onBack) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            CardSection {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Outlined.ReceiptLong, null, tint = Color(0xFF974552), modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(10.dp))
                    Text("#${order.orderNumber}", color = Color(0xFF8D3440), fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.weight(1f))
                    StatusPill(orderStatusLabel(order.status))
                }
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = Color(0xFFC9AEB3))
                Spacer(Modifier.height(12.dp))
                Text("Status pesanan saat ini: ${orderStatusLabel(order.status)}.", style = MaterialTheme.typography.bodySmall)
            }
            OrderSectionLabel("Produk")
            CardSection {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painterResource(product.gambar), null, Modifier.size(88.dp).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
                    Spacer(Modifier.width(16.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(item?.productName ?: product.nama, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                        Text(formatRupiah(item?.price?.toInt() ?: product.harga), color = Color(0xFF8D3440))
                        Text("x${item?.quantity ?: 1}", color = Color(0xFF8D3440))
                    }
                }
            }
            OrderSectionLabel("Informasi Pengiriman")
            CardSection {
                Text(address.recipientName.ifBlank { "Penerima" }, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(7.dp))
                Text(address.phone.ifBlank { "-" }, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(7.dp))
                Text("${address.addressLine}, ${address.city}, ${address.province}\n${address.postalCode}", style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(10.dp))
                HorizontalDivider(color = Color(0xFFC9AEB3))
                Spacer(Modifier.height(10.dp))
                SummaryLine("Ongkos Kirim", formatRupiah(order.shippingCost.toInt()))
            }
            CardSection {
                Text("Ringkasan Pesanan", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(16.dp))
                SummaryLine("Total Produk", formatRupiah(order.subtotal.toInt()))
                Spacer(Modifier.height(9.dp))
                SummaryLine("Ongkos Kirim", formatRupiah(order.shippingCost.toInt()))
                Spacer(Modifier.height(10.dp))
                HorizontalDivider(color = Color(0xFFC9AEB3))
                Spacer(Modifier.height(10.dp))
                SummaryLine("Total", formatRupiah(order.total.toInt()), true)
            }
        }
    }
}

@Composable
private fun CustomOrderDetailContent(onBack: () -> Unit, order: CustomOrderDto) {
    Scaffold(containerColor = AppColors.Background, topBar = { ArajutTopBar("Detail Pesanan", onBack) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(22.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
            CardSection {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Outlined.ReceiptLong, null, tint = Color(0xFF974552), modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(10.dp))
                    Text(if (order.primaryId.length > 8) "#CSM-${order.primaryId.take(8).uppercase()}" else "#CSM${order.primaryId.padStart(7, '0')}", color = Color(0xFF8D3440), fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.weight(1f))
                    StatusPill(orderStatusLabel(order.status))
                }
            }
            OrderSectionLabel("Produk Custom")
            CardSection {
                SummaryLine("Jenis Produk", order.effectiveTitle)
                Spacer(Modifier.height(10.dp))
                SummaryLine("Warna", order.color.ifBlank { "Sesuai Request" })
                Spacer(Modifier.height(10.dp))
                SummaryLine("Ukuran", order.size.ifBlank { "All Size" })
                Spacer(Modifier.height(10.dp))
                SummaryLine("Estimasi Pengerjaan", order.estimatedDays ?: "Menunggu konfirmasi")
                Spacer(Modifier.height(10.dp))
                HorizontalDivider(color = Color(0xFFC9AEB3))
                Spacer(Modifier.height(10.dp))
                SummaryLine("Estimasi Harga", formatRupiah(order.estimatedPrice?.toInt() ?: 0), true)
            }
        }
    }
}

@Composable
private fun CardSection(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) { Column(Modifier.fillMaxWidth().padding(20.dp), content = content) }
}

@Composable
private fun OrderSectionLabel(text: String) {
    Text(text, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF4E1821))
}

@Composable
private fun StatusPill(text: String) {
    Surface(color = Color(0xFFEBD9DC), shape = RoundedCornerShape(12.dp)) {
        Text(text, style = MaterialTheme.typography.labelSmall, color = Color(0xFF8D5060), modifier = Modifier.padding(horizontal = 11.dp, vertical = 4.dp))
    }
}

@Composable
private fun SummaryLine(label: String, value: String, bold: Boolean = false) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodySmall, fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal)
        Text(value, style = MaterialTheme.typography.bodySmall, fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal, color = if (bold) Color(0xFF8D3440) else AppColors.TextPrimary)
    }
}

@Preview
@Composable
fun RiwayatScreenPreview() { RiwayatScreen({}, {}, {}) }

@Preview
@Composable
fun OrderDetailScreenPreview() { OrderDetailScreen({}) }
