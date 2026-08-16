package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.tubeskelompok1.rajutmobile.model.WorkshopData
import org.tubeskelompok1.rajutmobile.ui.AppColors

@Composable
fun DetailWorkshopScreen(workshopId: Int, onBack: () -> Unit) {
    val workshop = WorkshopData.items.find { it.id == workshopId } ?: WorkshopData.items.first()
    Scaffold(
        containerColor = AppColors.Background,
        topBar = { ArajutTopBar(title = "Detail Workshop", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Image(
                painter = painterResource(workshop.posterImage),
                contentDescription = "Poster ${workshop.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(18.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        workshop.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF6B2430)
                    )
                    Spacer(Modifier.height(8.dp))
                    HorizontalDivider(color = Color(0xFF9A7479))
                    Spacer(Modifier.height(12.dp))
                    Text(
                        workshop.description,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(14.dp))
                    WorkshopDetailText(
                        "Informasi Pelaksanaan\n" +
                            "• Hari & Tanggal: ${workshop.date}\n" +
                            "• Waktu: ${workshop.time}\n" +
                            "• Lokasi: ${workshop.location}\n" +
                            "• Biaya pendaftaran (HTM): ${workshop.price}"
                    )
                    Spacer(Modifier.height(14.dp))
                    WorkshopDetailText(
                        "Apa yang Akan Kamu Dapatkan? (What Will You Get)\n" +
                            workshop.whatYouGet.mapIndexed { idx, item -> "${idx + 1}. $item" }.joinToString("\n")
                    )
                    Spacer(Modifier.height(14.dp))
                    WorkshopDetailText(
                        "Pendaftaran & Informasi Kontak\n" +
                            workshop.contact
                    )
                    Spacer(Modifier.height(14.dp))
                    WorkshopDetailText(
                        "Catatan: Penyelenggara acara ini bekerja sama antara Arajut dan mitra komunitas crafting."
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun WorkshopDetailText(text: String) {
    Text(text, style = MaterialTheme.typography.bodySmall, color = AppColors.TextPrimary)
}

@Preview
@Composable
fun DetailWorkshopScreenPreview() {
    DetailWorkshopScreen(1, {})
}
