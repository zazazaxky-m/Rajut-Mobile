package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import org.tubeskelompok1.rajutmobile.generated.resources.payment_success
import org.tubeskelompok1.rajutmobile.model.formatRupiah
import org.tubeskelompok1.rajutmobile.ui.AppColors

@Composable
fun PaymentSuccessScreen(
    total: Int,
    onViewOrders: () -> Unit,
    onHome: () -> Unit
) {
    Scaffold(containerColor = AppColors.Background) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(72.dp))
            Image(
                painter = painterResource(Res.drawable.payment_success),
                contentDescription = "Pembayaran berhasil",
                modifier = Modifier.size(width = 235.dp, height = 185.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(18.dp))
            Text(
                "Pembayaran Berhasil",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFA34B57)
            )
            Spacer(Modifier.height(10.dp))
            Text(
                "Pesanan Anda berhasil dibuat dan akan\nsegera diproses.",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF866D72),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(48.dp))
            SuccessInfoCard(total)
            Spacer(Modifier.weight(1f))
            Button(
                onClick = onViewOrders,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD27A85))
            ) {
                Text("Lihat Pesanan", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = onHome,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE2E0E0))
            ) {
                Text("Kembali ke Home", color = Color(0xFF656565), fontWeight = FontWeight.SemiBold)
            }
            Spacer(Modifier.height(42.dp))
        }
    }
}

@Composable
private fun SuccessInfoCard(total: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
            SuccessInfoRow("ORDER ID", "#ARJ1234567")
            Spacer(Modifier.height(12.dp))
            SuccessInfoRow("TOTAL TERBAYAR", formatRupiah(total), valueColor = Color(0xFF8D3440))
            Spacer(Modifier.height(10.dp))
            HorizontalDivider(color = Color(0xFFD2B9BD))
            Spacer(Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color(0xFF8D6570)
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    "Pengrajin kami akan segera memproses pesanan\nAnda dalam 1-2 hari kerja.",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF8D6570)
                )
            }
        }
    }
}

@Composable
private fun SuccessInfoRow(label: String, value: String, valueColor: Color = AppColors.TextPrimary) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color(0xFF745B60))
        Text(value, style = MaterialTheme.typography.labelMedium, color = valueColor, fontWeight = FontWeight.SemiBold)
    }
}

@Preview
@Composable
fun PaymentSuccessScreenPreview() {
    PaymentSuccessScreen(total = 92_000, onViewOrders = {}, onHome = {})
}
