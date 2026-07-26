package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.tubeskelompok1.rajutmobile.model.CartManager
import org.tubeskelompok1.rajutmobile.model.ItemKeranjang

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeranjangScreen(
    onCheckoutBerhasil: () -> Unit
) {
    val daftarItem = CartManager.itemKeranjang

    Scaffold(
        topBar = { TopAppBar(title = { Text("Keranjang") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (daftarItem.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Keranjang kamu masih kosong")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(daftarItem, key = { it.produk.id }) { item ->
                        BarisItemKeranjang(item)
                    }
                }

                Divider()

                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(
                            "Rp${CartManager.totalHargaKeranjang()}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            // Simulasi checkout: pindahkan isi keranjang ke riwayat transaksi
                            CartManager.checkout(tanggal = "Hari ini")
                            onCheckoutBerhasil()
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text("Checkout")
                    }
                }
            }
        }
    }
}

@Composable
private fun BarisItemKeranjang(item: ItemKeranjang) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.produk.nama, fontWeight = FontWeight.SemiBold)
                Text("Rp${item.produk.harga} x ${item.jumlah}", style = MaterialTheme.typography.bodySmall)
                Text(
                    "Subtotal: Rp${item.produk.harga * item.jumlah}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
            }
            IconButton(onClick = { CartManager.hapusDariKeranjang(item.produk) }) {
                Icon(Icons.Filled.Delete, contentDescription = "Hapus")
            }
        }
    }
}