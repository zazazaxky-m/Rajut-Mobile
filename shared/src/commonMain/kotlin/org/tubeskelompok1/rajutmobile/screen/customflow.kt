package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.tubeskelompok1.rajutmobile.generated.resources.Res
import org.tubeskelompok1.rajutmobile.generated.resources.custom_order_success
import org.tubeskelompok1.rajutmobile.model.DataMockup
import org.tubeskelompok1.rajutmobile.ui.AppColors

@Composable
fun CustomConfirmationScreen(onBack: () -> Unit, onContinue: () -> Unit) {
    val customProduct = DataMockup.customProduct
    Scaffold(
        containerColor = AppColors.Background,
        topBar = { ArajutTopBar("Detail Pesanan", onBack) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 22.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Konfirmasi Pesanan", style = MaterialTheme.typography.titleLarge, color = Color(0xFFA34B57), fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))
            Text("Tinjau detail pesanan Anda sebelum melanjutkan\npembayaran.", textAlign = TextAlign.Center, style = MaterialTheme.typography.bodySmall, color = Color(0xFF866D72))
            Spacer(Modifier.height(28.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Row {
                        Image(
                            painterResource(customProduct.gambar),
                            customProduct.nama,
                            Modifier.size(130.dp).clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.width(18.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            CustomLabel("JENIS PRODUK")
                            Text("Totebag", fontWeight = FontWeight.SemiBold)
                            Spacer(Modifier.height(5.dp))
                            CustomLabel("WARNA")
                            Text("Pink")
                            Spacer(Modifier.height(5.dp))
                            CustomLabel("UKURAN")
                            Text("20 cm x 30 cm")
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column { CustomLabel("ESTIMASI PENGERJAAN"); Text("5-7 Hari") }
                        Column { CustomLabel("HARGA"); Text("Rp70.000") }
                    }
                }
            }
            Spacer(Modifier.height(28.dp))
            Row(
                modifier = Modifier.fillMaxWidth().background(Color(0xFFF0E1E3)).padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(Icons.Outlined.Info, null, modifier = Modifier.size(17.dp), tint = Color(0xFF80666B))
                Spacer(Modifier.width(12.dp))
                Text("Waktu pengerjaan dapat berubah\nmenyesuaikan tingkat kesulitan dan jumlah\npesanan yang masuk.", style = MaterialTheme.typography.bodySmall, color = Color(0xFF80666B))
            }
            Spacer(Modifier.height(28.dp))
            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD27A85))
            ) { Text("Lanjut Checkout", color = Color.White, fontWeight = FontWeight.SemiBold) }
        }
    }
}

@Composable
private fun CustomLabel(text: String) {
    Text(text, style = MaterialTheme.typography.labelSmall, color = Color(0xFFA4888D))
}

@Composable
fun CustomOrderSuccessScreen(onViewOrders: () -> Unit, onHome: () -> Unit) {
    Scaffold(containerColor = AppColors.Background) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(88.dp))
            Image(
                painterResource(Res.drawable.custom_order_success),
                "Permintaan terkirim",
                modifier = Modifier.size(175.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(44.dp))
            Text("Permintaan Terkirim!", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = Color(0xFFA34B57))
            Spacer(Modifier.height(12.dp))
            Text(
                "Terima kasih telah mempercayakan kreasi\nrajutmu kepada Arajut. Pantau\nperkembangan pesananmu melalui menu\nPesanan.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF866D72)
            )
            Spacer(Modifier.weight(1f))
            Button(
                onClick = onViewOrders,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD27A85))
            ) { Text("Lihat Pesanan", color = Color.White, fontWeight = FontWeight.SemiBold) }
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = onHome,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE2E0E0))
            ) { Text("Kembali ke Home", color = Color(0xFF656565), fontWeight = FontWeight.SemiBold) }
            Spacer(Modifier.height(72.dp))
        }
    }
}

@Preview
@Composable
fun CustomConfirmationScreenPreview() { CustomConfirmationScreen({}, {}) }

@Preview
@Composable
fun CustomOrderSuccessScreenPreview() { CustomOrderSuccessScreen({}, {}) }
