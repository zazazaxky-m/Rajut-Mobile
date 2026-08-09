package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.tubeskelompok1.rajutmobile.model.CartManager
import org.tubeskelompok1.rajutmobile.model.DataMockup
import org.tubeskelompok1.rajutmobile.model.ItemKeranjang
import org.tubeskelompok1.rajutmobile.model.formatRupiah
import org.tubeskelompok1.rajutmobile.data.remote.AddressDto
import org.tubeskelompok1.rajutmobile.ui.AppColors

@Composable
fun CheckoutScreen(
    onBack: () -> Unit,
    onCreateOrder: (Int, String) -> Unit,
    cartItems: List<ItemKeranjang> = CartManager.itemKeranjang,
    address: AddressDto? = null,
    onEditAddress: () -> Unit = {}
) {
    val productTotal = cartItems.sumOf { it.produk.harga * it.jumlah }
    var shippingCost by remember { mutableIntStateOf(12_000) }
    val deliveryMethod = if (shippingCost == 12_000) "jne_regular" else "jnt_express"
    val grandTotal = productTotal + shippingCost

    Scaffold(
        containerColor = AppColors.Background,
        topBar = { ArajutTopBar(title = "Checkout", onBack = onBack) },
        bottomBar = {
            Surface(color = AppColors.Background) {
                Button(
                    onClick = { onCreateOrder(grandTotal, deliveryMethod) },
                    enabled = cartItems.isNotEmpty() && address != null,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp, vertical = 18.dp).height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD27A85))
                ) {
                    Text("Buat Pesanan", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionLabel("Informasi Pengiriman")
            AddressCard(address, onEditAddress)
            SectionLabel("Detail Produk")
            cartItems.forEach { CheckoutProductCard(it) }
            if (cartItems.isEmpty()) {
                Text("Keranjang masih kosong", color = AppColors.TextSecondary)
            }
            SectionLabel("Metode Pengiriman")
            ShippingCard(
                title = "JNE Regular",
                estimate = "Estimasi pengiriman 2-3 hari",
                price = 12_000,
                selected = shippingCost == 12_000,
                onClick = { shippingCost = 12_000 }
            )
            ShippingCard(
                title = "J&T Express",
                estimate = "Estimasi pengiriman 1-2 hari",
                price = 15_000,
                selected = shippingCost == 15_000,
                onClick = { shippingCost = 15_000 }
            )
            OrderSummary(productTotal, shippingCost, grandTotal)
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(text, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF4E1821))
}

@Composable
private fun AddressCard(address: AddressDto?, onEditAddress: () -> Unit) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(18.dp)) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                if (address == null) {
                    Text("Tambahkan alamat pengiriman sebelum membuat pesanan.", style = MaterialTheme.typography.bodyMedium)
                    return@Column
                }
                InfoLabel("NAMA PENERIMA")
                Text(address.recipientName, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(7.dp))
                InfoLabel("NOMOR TELEPON")
                Text(address.phone, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(7.dp))
                InfoLabel("ALAMAT LENGKAP")
                Text(
                    "${address.addressLine}, ${address.district},\n${address.city}, ${address.province} ${address.postalCode}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onEditAddress, modifier = Modifier.size(34.dp)) {
                Icon(Icons.Outlined.Edit, contentDescription = "Ubah alamat", tint = Color(0xFF5A1E28))
            }
        }
    }
}

@Composable
private fun InfoLabel(text: String) {
    Text(text, style = MaterialTheme.typography.labelSmall, color = Color(0xFFB08D92))
}

@Composable
private fun CheckoutProductCard(item: ItemKeranjang) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(item.produk.gambar),
                contentDescription = item.produk.nama,
                modifier = Modifier.size(78.dp).clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(14.dp))
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(item.produk.nama, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(formatRupiah(item.produk.harga), color = Color(0xFF8D3440))
                Text("x${item.jumlah}", color = Color(0xFF8D3440), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun ShippingCard(
    title: String,
    estimate: String,
    price: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, if (selected) Color(0xFF84333F) else Color(0xFFE8DDE0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp, vertical = 17.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(estimate, style = MaterialTheme.typography.labelSmall, color = AppColors.TextSecondary)
            }
            Text(formatRupiah(price), fontWeight = FontWeight.SemiBold, color = Color(0xFF4E1821))
        }
    }
}

@Composable
private fun OrderSummary(productTotal: Int, shippingCost: Int, grandTotal: Int) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0E1E3))
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 18.dp)) {
            Text("Ringkasan Pesanan", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(14.dp))
            SummaryRow("Total Produk", formatRupiah(productTotal))
            Spacer(Modifier.height(9.dp))
            SummaryRow("Ongkos Kirim", formatRupiah(shippingCost))
            Spacer(Modifier.height(10.dp))
            HorizontalDivider(color = Color(0xFFD5BEC2))
            Spacer(Modifier.height(10.dp))
            SummaryRow("Total", formatRupiah(grandTotal), emphasized = true)
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String, emphasized: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontWeight = if (emphasized) FontWeight.Bold else FontWeight.Normal)
        Text(
            value,
            fontWeight = if (emphasized) FontWeight.Bold else FontWeight.Normal,
            color = if (emphasized) Color(0xFF8D3440) else AppColors.TextPrimary
        )
    }
}

@Preview
@Composable
fun CheckoutScreenPreview() {
    CheckoutScreen(
        onBack = {},
        onCreateOrder = { _, _ -> },
        cartItems = listOf(ItemKeranjang(DataMockup.daftarProduk.first(), 1))
    )
}
