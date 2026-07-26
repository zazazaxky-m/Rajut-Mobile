package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.tubeskelompok1.rajutmobile.model.CartManager
import kotlinx.coroutines.launch
import org.tubeskelompok1.rajutmobile.model.DataMockup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBarangScreen(
    produkId: Int,
    onBack: () -> Unit,
    onLihatKeranjang: () -> Unit
) {
    val produk = DataMockup.cariProdukById(produkId)
    var jumlah by remember { mutableStateOf(1) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Detail Produk") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (produk == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Produk tidak ditemukan")
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Placeholder gambar produk
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Color(0xFFEDE7F6)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.CardGiftcard,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Color(0xFF7E57C2)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(produk.nama, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Rp${produk.harga}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("Warna: ${produk.warna}  |  Stok: ${produk.stok}", color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))
            Text("Deskripsi", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(produk.deskripsi, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            // Pemilih jumlah
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Jumlah:", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(12.dp))
                IconButton(onClick = { if (jumlah > 1) jumlah-- }) { Text("-", style = MaterialTheme.typography.titleLarge) }
                Text("$jumlah", style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = { if (jumlah < produk.stok) jumlah++ }) { Text("+", style = MaterialTheme.typography.titleLarge) }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    CartManager.tambahKeKeranjang(produk, jumlah)
                    scope.launch {
                        snackbarHostState.showSnackbar("Ditambahkan ke keranjang")
                    }
                },
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Tambah ke Keranjang")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onLihatKeranjang,
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Lihat Keranjang")
            }
        }
    }
}