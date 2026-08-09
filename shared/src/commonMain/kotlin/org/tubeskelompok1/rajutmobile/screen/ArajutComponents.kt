package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.tubeskelompok1.rajutmobile.model.Produk
import org.tubeskelompok1.rajutmobile.model.formatRupiah
import org.tubeskelompok1.rajutmobile.ui.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ArajutTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    showActions: Boolean = false,
    onSearch: () -> Unit = {},
    onCart: () -> Unit = {}
) {
    TopAppBar(
        modifier = Modifier.shadow(4.dp),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppColors.TextPrimary
            )
        },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                }
            }
        },
        actions = {
            if (showActions) {
                IconButton(onClick = onSearch) {
                    Icon(Icons.Filled.Search, contentDescription = "Cari")
                }
                IconButton(onClick = onCart) {
                    Icon(Icons.Filled.ShoppingBasket, contentDescription = "Keranjang")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFFE3E7))
    )
}

@Composable
internal fun ProductCard(
    product: Produk,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Image(
                painter = painterResource(product.gambar),
                contentDescription = product.nama,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(132.dp)
                    .clip(RoundedCornerShape(14.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = product.nama,
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.TextPrimary,
                maxLines = 1
            )
            Text(
                text = formatRupiah(product.harga),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF7A2330)
            )
        }
    }
}

@Composable
internal fun ArajutBottomBar(
    selected: String,
    onHome: () -> Unit,
    onOrders: () -> Unit,
    onProfile: () -> Unit
) {
    val items = listOf(
        Triple("home", "Home", Icons.Filled.Home),
        Triple("orders", "Pesanan", Icons.AutoMirrored.Filled.ReceiptLong),
        Triple("profile", "Profil", Icons.Filled.Person)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .navigationBarsPadding()
    ) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 4.dp,
            windowInsets = WindowInsets(0.dp),
            modifier = Modifier.fillMaxWidth().height(68.dp)
        ) {
            items.forEach { (key, label, icon) ->
                NavigationBarItem(
                    selected = selected == key,
                    onClick = {
                        when (key) {
                            "home" -> onHome()
                            "orders" -> onOrders()
                            else -> onProfile()
                        }
                    },
                    icon = { Icon(icon, contentDescription = label, modifier = Modifier.size(23.dp)) },
                    label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = AppColors.Primary,
                        indicatorColor = Color(0xFFE98290),
                        unselectedIconColor = Color(0xFFBE9FA2),
                        unselectedTextColor = Color(0xFFBE9FA2)
                    )
                )
            }
        }
    }
}
