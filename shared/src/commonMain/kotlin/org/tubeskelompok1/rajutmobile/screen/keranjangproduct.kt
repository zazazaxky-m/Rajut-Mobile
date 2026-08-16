package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import org.tubeskelompok1.rajutmobile.model.CartManager
import org.tubeskelompok1.rajutmobile.model.DataMockup
import org.tubeskelompok1.rajutmobile.model.ItemKeranjang
import org.tubeskelompok1.rajutmobile.model.formatRupiah
import org.tubeskelompok1.rajutmobile.ui.AppColors

@Composable
fun KeranjangScreen(
    onBack: () -> Unit,
    onCheckout: () -> Unit,
    itemsInCart: List<ItemKeranjang> = CartManager.itemKeranjang,
    onQuantityChange: (org.tubeskelompok1.rajutmobile.model.Produk, Int) -> Unit = { product, quantity -> CartManager.ubahJumlah(product, quantity) },
    onDelete: (org.tubeskelompok1.rajutmobile.model.Produk) -> Unit = { CartManager.hapusDariKeranjang(it) }
) {
    Scaffold(
        containerColor = AppColors.Background,
        topBar = { ArajutTopBar(title = "Keranjang", onBack = onBack) },
        bottomBar = {
            Surface(color = AppColors.Background) {
                Button(
                    onClick = onCheckout,
                    enabled = itemsInCart.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp, vertical = 20.dp).height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD27A85),
                        disabledContainerColor = Color(0xFFE2C6CA)
                    )
                ) {
                    Text("Checkout", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    ) { padding ->
        if (itemsInCart.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Keranjang kamu masih kosong", color = AppColors.TextSecondary)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(horizontal = 22.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(itemsInCart, key = { it.produk.id }) { item ->
                    CartItemCard(item, onQuantityChange, onDelete)
                }
            }
        }
    }
}

@Composable
private fun CartItemCard(
    item: ItemKeranjang,
    onQuantityChange: (org.tubeskelompok1.rajutmobile.model.Produk, Int) -> Unit,
    onDelete: (org.tubeskelompok1.rajutmobile.model.Produk) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (item.produk.id == "1") "Blue Vest" else item.produk.nama,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(5.dp))
                Text(formatRupiah(item.produk.harga), color = Color(0xFF8D3440))
                Spacer(Modifier.height(7.dp))
                Text("x${item.jumlah}", color = Color(0xFF8D3440), style = MaterialTheme.typography.bodySmall)
            }
            Column(horizontalAlignment = Alignment.End) {
                IconButton(
                    onClick = { onDelete(item.produk) },
                    modifier = Modifier.size(34.dp)
                ) {
                    Icon(
                        Icons.Outlined.DeleteOutline,
                        contentDescription = "Hapus",
                        tint = Color(0xFF913846),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.height(13.dp))
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .then(Modifier),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    QuantityButton("-") {
                        onQuantityChange(item.produk, item.jumlah - 1)
                    }
                    Text(
                        item.jumlah.toString(),
                        modifier = Modifier.width(34.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall
                    )
                    QuantityButton("+") {
                        onQuantityChange(item.produk, item.jumlah + 1)
                    }
                }
            }
        }
    }
}

@Composable
private fun QuantityButton(label: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = Color(0xFFF1E6E8),
        modifier = Modifier.size(width = 34.dp, height = 28.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(label, color = Color(0xFF8D6570))
        }
    }
}

@Preview
@Composable
fun KeranjangScreenPreview() {
    KeranjangScreen(
        onBack = {},
        onCheckout = {},
        itemsInCart = listOf(ItemKeranjang(DataMockup.daftarProduk.first(), 1))
    )
}
