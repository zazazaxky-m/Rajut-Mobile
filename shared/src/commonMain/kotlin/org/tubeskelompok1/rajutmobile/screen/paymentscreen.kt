package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.tubeskelompok1.rajutmobile.generated.resources.Res
import org.tubeskelompok1.rajutmobile.generated.resources.qris_code
import org.tubeskelompok1.rajutmobile.model.formatRupiah
import org.tubeskelompok1.rajutmobile.ui.AppColors

@Composable
fun PaymentScreen(
    total: Int,
    onBack: () -> Unit,
    onPaymentConfirmed: () -> Unit,
    orderId: String = "ARJ1234567"
) {
    Scaffold(
        containerColor = AppColors.Background,
        topBar = { ArajutTopBar(title = "Pembayaran", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 22.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            PaymentIdentityCard(total, orderId)
            Text("Metode Pembayaran", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF4E1821))
            QrisCard(onPaymentConfirmed)
        }
    }
}

@Composable
private fun PaymentIdentityCard(total: Int, orderId: String) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp)) {
            PaymentInfoRow("TOTAL PEMBAYARAN", formatRupiah(total))
            Spacer(Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFD2B9BD))
            Spacer(Modifier.height(12.dp))
            PaymentInfoRow("ID Pesanan", "#$orderId")
        }
    }
}

@Composable
private fun PaymentInfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color(0xFF856A70))
        Text(value, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun QrisCard(onPaymentConfirmed: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(40.dp).background(Color(0xFFF1E3E5), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.QrCode2,
                        contentDescription = null,
                        tint = Color(0xFF8D3440),
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(Modifier.size(14.dp))
                Column {
                    Text("QRIS", fontWeight = FontWeight.SemiBold)
                    Text("Instan & Otomatis", style = MaterialTheme.typography.labelSmall)
                }
            }
            HorizontalDivider(color = Color(0xFFD2B9BD))
            Image(
                painter = painterResource(Res.drawable.qris_code),
                contentDescription = "Kode QRIS",
                modifier = Modifier
                    .padding(top = 26.dp)
                    .size(205.dp)
                    .clickable(onClick = onPaymentConfirmed),
                contentScale = ContentScale.Fit
            )
            Text(
                "Simpan QR code ini dan pindai menggunakan\naplikasi m-banking atau e-wallet pilihan\nAnda.",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = AppColors.TextPrimary
            )
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.clickable { },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.Download,
                    contentDescription = null,
                    tint = Color(0xFF8D3440),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.size(8.dp))
                Text("Simpan Gambar", color = Color(0xFF8D3440), style = MaterialTheme.typography.labelSmall)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Preview
@Composable
fun PaymentScreenPreview() {
    PaymentScreen(total = 92_000, onBack = {}, onPaymentConfirmed = {})
}
