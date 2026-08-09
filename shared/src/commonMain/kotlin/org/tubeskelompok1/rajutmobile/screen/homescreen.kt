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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.tubeskelompok1.rajutmobile.generated.resources.Res
import org.tubeskelompok1.rajutmobile.generated.resources.workshop_group
import org.tubeskelompok1.rajutmobile.model.DataMockup
import org.tubeskelompok1.rajutmobile.ui.AppColors

@Composable
fun HomeScreen(
    onWorkshop: () -> Unit,
    onAllProducts: () -> Unit,
    onProductClick: (Int) -> Unit,
    onCustomOrder: () -> Unit,
    onCart: () -> Unit,
    onOrders: () -> Unit,
    onProfile: () -> Unit,
    products: List<org.tubeskelompok1.rajutmobile.model.Produk> = DataMockup.daftarProduk
) {
    Scaffold(
        containerColor = AppColors.Background,
        bottomBar = {
            ArajutBottomBar(
                selected = "home",
                onHome = {},
                onOrders = onOrders,
                onProfile = onProfile
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { HomeHeader(onCart = onCart) }
            item {
                WorkshopHero(
                    onClick = onWorkshop,
                    modifier = Modifier.padding(horizontal = 22.dp)
                )
            }
            item {
                Column(modifier = Modifier.padding(horizontal = 22.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Jelajahi Produk", fontWeight = FontWeight.SemiBold)
                        Text(
                            "Lihat Semua",
                            color = AppColors.Primary,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.clickable(onClick = onAllProducts)
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                        products.take(2).forEach { product ->
                            ProductCard(
                                product = product,
                                onClick = { onProductClick(product.id) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
            item {
                CustomOrderBanner(
                    onClick = onCustomOrder,
                    modifier = Modifier.padding(horizontal = 22.dp)
                )
            }
            item { Spacer(Modifier.height(2.dp)) }
        }
    }
}

@Composable
private fun HomeHeader(onCart: () -> Unit) {
    Surface(
        color = Color(0xFFFFD8DC),
        shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
        shadowElevation = 5.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp, vertical = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Halo, Azzahra!",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF521923)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Yuk temukan produk rajut favoritmu.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF7D3941)
                )
            }
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Search, contentDescription = "Cari", tint = AppColors.TextPrimary)
            }
            IconButton(onClick = onCart) {
                Icon(Icons.Filled.ShoppingBasket, contentDescription = "Keranjang", tint = AppColors.TextPrimary)
            }
        }
    }
}

@Composable
private fun WorkshopHero(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(195.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(Res.drawable.workshop_group),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(Modifier.fillMaxSize().background(Color(0xFF5A1822).copy(alpha = 0.52f)))
        Column(
            modifier = Modifier.align(Alignment.CenterStart).padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Surface(color = Color.White, shape = RoundedCornerShape(10.dp)) {
                Text(
                    "WORKSHOP",
                    color = AppColors.Primary,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                "Belajar Crochet Bersama Arajut",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(5.dp))
            Text(
                "Tingkatkan kreativitas dan keterampilan merajutmu.",
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(0.78f)
            )
            Spacer(Modifier.height(14.dp))
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF28C9A)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(38.dp)
            ) {
                Text("Lihat Workshop", color = Color.White)
            }
        }
    }
}

@Composable
private fun CustomOrderBanner(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(158.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFA34B57)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(width = 205.dp, height = 125.dp)
                .drawBehind {
                    drawOval(
                        color = Color.White.copy(alpha = 0.3f),
                        style = Stroke(
                            width = 1.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 7f))
                        )
                    )
                }
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Punya desain sendiri?",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(5.dp))
            Text(
                "Wujudkan rajutan impianmu melalui layanan\nCustom Order Arajut.",
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.height(38.dp)
            ) {
                Text("Pesan Costum", color = Color(0xFFA34B57))
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen({}, {}, {}, {}, {}, {}, {})
}
