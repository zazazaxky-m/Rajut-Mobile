package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import org.tubeskelompok1.rajutmobile.model.Workshop
import org.tubeskelompok1.rajutmobile.model.WorkshopData
import org.tubeskelompok1.rajutmobile.ui.AppColors

@Composable
fun WorkshopScreen(
    onBack: () -> Unit,
    onWorkshopClick: (Int) -> Unit,
    workshops: List<Workshop> = WorkshopData.items
) {
    Scaffold(
        containerColor = AppColors.Background,
        topBar = { ArajutTopBar(title = "Workshop", onBack = onBack) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            items(workshops) { workshop ->
                WorkshopCard(workshop, onClick = { onWorkshopClick(workshop.id) })
            }
        }
    }
}

@Composable
private fun WorkshopCard(workshop: Workshop, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(workshop.image),
                    contentDescription = workshop.title,
                    modifier = Modifier.fillMaxWidth().height(205.dp).clip(
                        RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp)
                    ),
                    contentScale = ContentScale.Crop
                )
                Surface(
                    color = Color(0xFF7E3542),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.align(Alignment.BottomEnd).padding(14.dp)
                ) {
                    Text(
                        "Lihat Detail",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 7.dp)
                    )
                }
            }
            Column(modifier = Modifier.padding(horizontal = 17.dp, vertical = 14.dp)) {
                Text(
                    workshop.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF6B2430),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(10.dp))
                WorkshopInfoRow(Icons.Filled.CalendarMonth, workshop.date)
                WorkshopInfoRow(Icons.Filled.AccessTime, workshop.time)
                WorkshopInfoRow(Icons.Filled.LocationOn, workshop.location)
            }
        }
    }
}

@Composable
private fun WorkshopInfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(17.dp), tint = AppColors.TextPrimary)
        Spacer(Modifier.size(12.dp))
        Text(text, style = MaterialTheme.typography.bodySmall, color = AppColors.TextPrimary)
    }
}

@Preview
@Composable
fun WorkshopScreenPreview() {
    WorkshopScreen({}, {})
}
