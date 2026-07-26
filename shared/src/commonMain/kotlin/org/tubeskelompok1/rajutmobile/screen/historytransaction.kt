package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.tubeskelompok1.rajutmobile.model.CartManager
import org.tubeskelompok1.rajutmobile.model.Transaksi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiwayatScreen() {
    val daftarTransaksi = CartManager.riwayatTransaksi

    Scaffold(
        topBar = { TopAppBar(title = { Text("Riwayat Transaksi") }) }
    ) { innerPadding ->
        if (daftarTransaksi.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada transaksi")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(daftarTransaksi, key = { it.id }) { transaksi ->
                    KartuTransaksi(transaksi)
                }
            }
        }
    }
}

@Composable
private fun KartuTransaksi(transaksi: Transaksi) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Transaksi #${transaksi.id}", fontWeight = FontWeight.Bold)
                AssistChip(onClick = { }, label = { Text(transaksi.status) })
            }
            Text(transaksi.tanggal, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(6.dp))

            transaksi.items.forEach { item ->
                Text(
                    "- ${item.produk.nama} x${item.jumlah}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                "Total: Rp${transaksi.totalHarga}",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}