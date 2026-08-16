package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.tubeskelompok1.rajutmobile.model.DataMockup
import org.tubeskelompok1.rajutmobile.model.Produk
import org.tubeskelompok1.rajutmobile.model.formatRupiah
import org.tubeskelompok1.rajutmobile.ui.AppColors

@Composable
fun DetailBarangScreen(
    produkId: String,
    onBack: () -> Unit,
    onLihatKeranjang: () -> Unit,
    products: List<Produk> = DataMockup.daftarProduk,
    onAddToCart: suspend (Produk) -> String? = { null }
) {
    val product = products.find { it.id == produkId } ?: DataMockup.cariProdukById(produkId)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = AppColors.Background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            ArajutTopBar(
                title = "Detail Produk",
                onBack = onBack,
                showActions = true,
                onCart = onLihatKeranjang
            )
        }
    ) { padding ->
        if (product == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) { Text("Produk tidak ditemukan") }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Image(
                painter = painterResource(product.gambar),
                contentDescription = product.nama,
                modifier = Modifier.fillMaxWidth().height(330.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(20.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        product.nama.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.TextPrimary
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(
                        formatRupiah(product.harga),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6B2430)
                    )
                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider(color = Color(0xFFBA9DA1))
                    Spacer(Modifier.height(12.dp))
                    Text(product.deskripsi, style = MaterialTheme.typography.bodySmall)
                    Spacer(Modifier.height(14.dp))
                    Text("Detail & Spesifikasi:", style = MaterialTheme.typography.bodySmall)
                    Text(
                        "• Material: Premium Cotton Thread (halus, tidak panas, dan nyaman dipakai seharian)\n" +
                            "• Motif: Classic Granny Square Pattern\n" +
                            "• Warna: ${product.warna}\n" +
                            "• Detail: Aksen tali pengikat di bagian depan\n" +
                            "• Fit: Relaxed Fit",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(14.dp))
                    Text("Ukuran Detail (All Size):", style = MaterialTheme.typography.bodySmall)
                    Text(
                        "• Lingkar Dada: ~95 cm\n" +
                            "• Panjang Baju: ~44 cm (Toleransi perbedaan ukuran 1-2 cm karena pengerjaan handmade)",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(22.dp))
                    Button(
                        onClick = {
                            scope.launch {
                                val error = onAddToCart(product)
                                snackbarHostState.showSnackbar(error ?: "Produk dimasukkan ke keranjang")
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD27A85)),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Text("Masukkan Keranjang", color = Color.White)
                    }
                }
            }
            Spacer(Modifier.height(22.dp))
        }
    }
}

@Preview
@Composable
fun DetailBarangScreenPreview() {
    DetailBarangScreen("1", {}, {})
}
