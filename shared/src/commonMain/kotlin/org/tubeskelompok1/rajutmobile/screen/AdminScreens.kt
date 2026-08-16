package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.tubeskelompok1.rajutmobile.data.remote.AdminCategoryDto
import org.tubeskelompok1.rajutmobile.data.remote.AdminDashboardDto
import org.tubeskelompok1.rajutmobile.data.remote.AdminOrderDto
import org.tubeskelompok1.rajutmobile.data.remote.AdminProductDto
import org.tubeskelompok1.rajutmobile.data.remote.AdminProductRequest
import org.tubeskelompok1.rajutmobile.data.remote.AdminWorkshopDto
import org.tubeskelompok1.rajutmobile.data.remote.AdminWorkshopRequest
import org.tubeskelompok1.rajutmobile.model.formatRupiah
import org.tubeskelompok1.rajutmobile.ui.AppColors

private val AdminInk = Color(0xFF332D2E)
private val AdminGreen = Color(0xFF3D7864)
private val AdminAmber = Color(0xFFC88336)
private val AdminSurface = Color(0xFFFFFBFB)
private val AdminBorder = Color(0xFFE7DCDD)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdminTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    onRefresh: (() -> Unit)? = null,
    onLogout: (() -> Unit)? = null
) {
    TopAppBar(
        title = { Text(title, fontWeight = FontWeight.SemiBold, color = AdminInk) },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                }
            }
        },
        actions = {
            if (onRefresh != null) {
                IconButton(onClick = onRefresh) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Muat ulang")
                }
            }
            if (onLogout != null) {
                IconButton(onClick = onLogout) {
                    Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Keluar")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFFE3E7))
    )
}

@Composable
private fun AdminBottomBar(
    selected: String,
    onDashboard: () -> Unit,
    onProducts: () -> Unit,
    onOrders: () -> Unit,
    onWorkshops: () -> Unit
) {
    val items = listOf(
        Triple("dashboard", "Ringkasan", Icons.Filled.Dashboard),
        Triple("products", "Produk", Icons.Filled.Inventory2),
        Triple("orders", "Pesanan", Icons.AutoMirrored.Filled.ReceiptLong),
        Triple("workshops", "Workshop", Icons.Filled.Event)
    )
    Column(Modifier.fillMaxWidth().background(Color.White).navigationBarsPadding()) {
        NavigationBar(
            modifier = Modifier.fillMaxWidth().height(70.dp),
            containerColor = Color.White,
            windowInsets = WindowInsets(0.dp),
            tonalElevation = 3.dp
        ) {
            items.forEach { (key, label, icon) ->
                NavigationBarItem(
                    selected = key == selected,
                    onClick = {
                        when (key) {
                            "dashboard" -> onDashboard()
                            "products" -> onProducts()
                            "orders" -> onOrders()
                            else -> onWorkshops()
                        }
                    },
                    icon = { Icon(icon, contentDescription = label, Modifier.size(22.dp)) },
                    label = { Text(label, style = MaterialTheme.typography.labelSmall, maxLines = 1) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = AppColors.Primary,
                        indicatorColor = AppColors.Primary,
                        unselectedIconColor = Color(0xFF9A8588),
                        unselectedTextColor = Color(0xFF9A8588)
                    )
                )
            }
        }
    }
}

@Composable
fun AdminDashboardScreen(
    dashboard: AdminDashboardDto?,
    products: List<AdminProductDto>,
    orders: List<AdminOrderDto>,
    onProducts: () -> Unit,
    onOrders: () -> Unit,
    onWorkshops: () -> Unit,
    onRefresh: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        containerColor = AppColors.Background,
        topBar = { AdminTopBar("Admin Arajut", onRefresh = onRefresh, onLogout = onLogout) },
        bottomBar = { AdminBottomBar("dashboard", {}, onProducts, onOrders, onWorkshops) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column {
                    Text("Ringkasan Toko", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = AdminInk)
                    Text("Pantau aktivitas dan hal yang perlu ditangani.", style = MaterialTheme.typography.bodySmall, color = AppColors.TextSecondary)
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        AdminMetric("Produk Aktif", dashboard?.productCount ?: 0, Icons.Filled.Inventory2, AdminGreen, Modifier.weight(1f))
                        AdminMetric("Stok Menipis", dashboard?.lowStockCount ?: 0, Icons.Filled.MoreVert, AdminAmber, Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        AdminMetric("Total Pesanan", dashboard?.orderCount ?: 0, Icons.AutoMirrored.Filled.ReceiptLong, AppColors.Primary, Modifier.weight(1f))
                        AdminMetric("Workshop", dashboard?.workshopCount ?: 0, Icons.Filled.Event, Color(0xFF52739A), Modifier.weight(1f))
                    }
                }
            }
            item {
                Surface(color = AdminInk, shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(18.dp)) {
                        Text("Pendapatan Terbayar", color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.labelMedium)
                        Spacer(Modifier.height(6.dp))
                        Text(formatRupiah(dashboard?.revenue?.toInt() ?: 0), color = Color.White, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    }
                }
            }
            item { AdminSectionHeader("Perlu Restok", "Kelola Produk", onProducts) }
            val lowStock = products.filter { it.isActive && it.stock <= 5 }.take(4)
            if (lowStock.isEmpty()) {
                item { AdminEmptyState("Semua stok masih aman.") }
            } else {
                items(lowStock, key = { "stock-${it.id}" }) { product ->
                    AdminCompactRow(product.name, "Stok ${product.stock}", AdminAmber, onProducts)
                }
            }
            item { AdminSectionHeader("Pesanan Terbaru", "Lihat Semua", onOrders) }
            items(orders.take(3), key = { "recent-${it.id}" }) { order ->
                AdminCompactRow(order.orderNumber, "${order.customerName} - ${adminStatusLabel(order.status)}", statusColor(order.status), onOrders)
            }
        }
    }
}

@Composable
private fun AdminMetric(label: String, value: Int, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, modifier: Modifier) {
    Card(modifier, shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = AdminSurface), border = androidx.compose.foundation.BorderStroke(1.dp, AdminBorder)) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(color = color.copy(alpha = 0.14f), shape = CircleShape, modifier = Modifier.size(38.dp)) {
                Box(contentAlignment = Alignment.Center) { Icon(icon, null, tint = color, modifier = Modifier.size(20.dp)) }
            }
            Spacer(Modifier.width(11.dp))
            Column {
                Text(value.toString(), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = AdminInk)
                Text(label, style = MaterialTheme.typography.labelSmall, color = AppColors.TextSecondary, maxLines = 1)
            }
        }
    }
}

@Composable
private fun AdminSectionHeader(title: String, action: String, onClick: () -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(title, fontWeight = FontWeight.SemiBold, color = AdminInk)
        TextButton(onClick = onClick) { Text(action, color = AppColors.Primary) }
    }
}

@Composable
private fun AdminCompactRow(title: String, subtitle: String, color: Color, onClick: () -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick), color = AdminSurface, shape = RoundedCornerShape(8.dp), border = androidx.compose.foundation.BorderStroke(1.dp, AdminBorder)) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(8.dp).background(color, CircleShape))
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = AppColors.TextSecondary, maxLines = 1)
            }
        }
    }
}

@Composable
private fun AdminEmptyState(text: String) {
    Surface(color = AdminSurface, shape = RoundedCornerShape(8.dp), border = androidx.compose.foundation.BorderStroke(1.dp, AdminBorder), modifier = Modifier.fillMaxWidth()) {
        Text(text, Modifier.padding(18.dp), color = AppColors.TextSecondary, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun AdminProductsScreen(
    products: List<AdminProductDto>,
    onDashboard: () -> Unit,
    onOrders: () -> Unit,
    onWorkshops: () -> Unit,
    onAdd: () -> Unit,
    onEdit: (String) -> Unit,
    onDeactivate: suspend (String) -> String?
) {
    var query by remember { mutableStateOf("") }
    var deleting by remember { mutableStateOf<AdminProductDto?>(null) }
    val scope = rememberCoroutineScope()
    val filtered = products.filter { it.name.contains(query, true) || it.category.contains(query, true) }
    Scaffold(
        containerColor = AppColors.Background,
        topBar = { AdminTopBar("Kelola Produk") },
        bottomBar = { AdminBottomBar("products", onDashboard, {}, onOrders, onWorkshops) },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onAdd, containerColor = AppColors.Primary, contentColor = Color.White, icon = { Icon(Icons.Filled.Add, null) }, text = { Text("Tambah Produk") })
        }
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            item {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Cari produk atau kategori") },
                    leadingIcon = { Icon(Icons.Filled.Search, null) },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp)
                )
            }
            item { Text("${filtered.size} produk", style = MaterialTheme.typography.labelMedium, color = AppColors.TextSecondary) }
            items(filtered, key = { it.id }) { product ->
                AdminProductRow(product, onEdit = { onEdit(product.id) }, onDelete = { deleting = product })
            }
            item { Spacer(Modifier.height(72.dp)) }
        }
    }
    deleting?.let { product ->
        AlertDialog(
            onDismissRequest = { deleting = null },
            title = { Text("Nonaktifkan produk?") },
            text = { Text("${product.name} tidak akan tampil di katalog pelanggan.") },
            dismissButton = { TextButton(onClick = { deleting = null }) { Text("Batal") } },
            confirmButton = {
                Button(onClick = { scope.launch { onDeactivate(product.id); deleting = null } }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB23B49))) { Text("Nonaktifkan") }
            }
        )
    }
}

@Composable
private fun AdminProductRow(product: AdminProductDto, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = AdminSurface), border = androidx.compose.foundation.BorderStroke(1.dp, AdminBorder)) {
        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(color = if (product.isActive) Color(0xFFE6F1EC) else Color(0xFFF0E7E8), shape = RoundedCornerShape(6.dp), modifier = Modifier.size(50.dp)) {
                Box(contentAlignment = Alignment.Center) { Icon(Icons.Filled.Inventory2, null, tint = if (product.isActive) AdminGreen else Color(0xFF987E82)) }
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(product.name, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text("${product.category} | ${formatRupiah(product.price.toInt())}", style = MaterialTheme.typography.bodySmall, color = AppColors.TextSecondary, maxLines = 1)
                Text(if (product.isActive) "Stok ${product.stock}" else "Nonaktif", style = MaterialTheme.typography.labelSmall, color = if (product.stock <= 5) AdminAmber else AdminGreen)
            }
            IconButton(onClick = onEdit) { Icon(Icons.Filled.Edit, "Ubah", tint = AdminInk) }
            IconButton(onClick = onDelete, enabled = product.isActive) { Icon(Icons.Filled.DeleteOutline, "Nonaktifkan", tint = Color(0xFFB23B49)) }
        }
    }
}

@Composable
fun AdminProductFormScreen(
    product: AdminProductDto?,
    categories: List<AdminCategoryDto>,
    onBack: () -> Unit,
    onSave: suspend (AdminProductRequest) -> String?
) {
    var name by remember(product) { mutableStateOf(product?.name.orEmpty()) }
    var slug by remember(product) { mutableStateOf(product?.slug.orEmpty()) }
    var description by remember(product) { mutableStateOf(product?.description.orEmpty()) }
    var price by remember(product) { mutableStateOf(product?.price?.toInt()?.toString().orEmpty()) }
    var stock by remember(product) { mutableStateOf(product?.stock?.toString().orEmpty()) }
    var categoryId by remember(product, categories) { mutableStateOf(product?.categoryId ?: categories.firstOrNull()?.id.orEmpty()) }
    var isFeatured by remember(product) { mutableStateOf(product?.isFeatured ?: false) }
    var isActive by remember(product) { mutableStateOf(product?.isActive ?: true) }
    var categoryMenu by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var saving by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Scaffold(containerColor = AppColors.Background, topBar = { AdminTopBar(if (product == null) "Tambah Produk" else "Ubah Produk", onBack) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AdminField("Nama Produk", name) {
                name = it
                if (product == null) slug = it.lowercase().trim().replace(" ", "-").filter { char -> char.isLetterOrDigit() || char == '-' }
            }
            AdminField("Slug", slug) { slug = it }
            Box {
                OutlinedButton(onClick = { categoryMenu = true }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
                    Text(categories.firstOrNull { it.id == categoryId }?.name ?: "Pilih Kategori", modifier = Modifier.weight(1f), color = AdminInk)
                }
                DropdownMenu(expanded = categoryMenu, onDismissRequest = { categoryMenu = false }) {
                    categories.forEach { category -> DropdownMenuItem(text = { Text(category.name) }, onClick = { categoryId = category.id; categoryMenu = false }) }
                }
            }
            AdminField("Deskripsi", description, singleLine = false, minLines = 4) { description = it }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AdminField("Harga", price, Modifier.weight(1f), KeyboardType.Number) { price = it }
                AdminField("Stok", stock, Modifier.weight(1f), KeyboardType.Number) { stock = it }
            }
            AdminToggleRow("Produk Unggulan", "Tampilkan lebih dulu di katalog", isFeatured) { isFeatured = it }
            AdminToggleRow("Produk Aktif", "Dapat dibeli oleh pelanggan", isActive) { isActive = it }
            error?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
            Button(
                onClick = {
                    scope.launch {
                        saving = true
                        error = onSave(
                            AdminProductRequest(
                                name = name,
                                slug = slug,
                                categoryId = categoryId,
                                category = categories.firstOrNull { it.id == categoryId }?.name,
                                shortDescription = description.take(120),
                                description = description,
                                price = price.toDoubleOrNull() ?: 0.0,
                                stock = stock.toIntOrNull() ?: 0,
                                isFeatured = isFeatured,
                                isActive = isActive
                            )
                        )
                        saving = false
                        if (error == null) onBack()
                    }
                },
                enabled = !saving && name.isNotBlank() && slug.isNotBlank() && categoryId.isNotBlank() && description.isNotBlank() && price.toDoubleOrNull() != null,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.Primary)
            ) { Text(if (saving) "Menyimpan..." else "Simpan Produk") }
            Spacer(Modifier.height(18.dp))
        }
    }
}

@Composable
private fun AdminField(label: String, value: String, modifier: Modifier = Modifier.fillMaxWidth(), keyboardType: KeyboardType = KeyboardType.Text, singleLine: Boolean = true, minLines: Int = 1, onChange: (String) -> Unit) {
    OutlinedTextField(value = value, onValueChange = onChange, label = { Text(label) }, modifier = modifier, singleLine = singleLine, minLines = minLines, shape = RoundedCornerShape(8.dp), keyboardOptions = KeyboardOptions(keyboardType = keyboardType))
}

@Composable
private fun AdminToggleRow(title: String, subtitle: String, checked: Boolean, onChecked: (Boolean) -> Unit) {
    Surface(color = AdminSurface, shape = RoundedCornerShape(8.dp), border = androidx.compose.foundation.BorderStroke(1.dp, AdminBorder)) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) { Text(title, fontWeight = FontWeight.SemiBold); Text(subtitle, style = MaterialTheme.typography.bodySmall, color = AppColors.TextSecondary) }
            Switch(checked, onChecked)
        }
    }
}

@Composable
fun AdminOrdersScreen(
    orders: List<AdminOrderDto>,
    onDashboard: () -> Unit,
    onProducts: () -> Unit,
    onWorkshops: () -> Unit,
    onUpdateStatus: suspend (String, String) -> String?
) {
    val filters = listOf("Semua", "Diproses", "Dikemas", "Dikirim", "Selesai")
    var selectedFilter by remember { mutableStateOf("Semua") }
    val filtered = orders.filter { selectedFilter == "Semua" || adminStatusLabel(it.status) == selectedFilter }
    Scaffold(
        containerColor = AppColors.Background,
        topBar = { AdminTopBar("Kelola Pesanan") },
        bottomBar = { AdminBottomBar("orders", onDashboard, onProducts, {}, onWorkshops) }
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                    filters.take(4).forEach { filter -> FilterChip(selected = selectedFilter == filter, onClick = { selectedFilter = filter }, label = { Text(filter, maxLines = 1) }) }
                }
            }
            items(filtered, key = { it.id }) { order -> AdminOrderCard(order, onUpdateStatus) }
            if (filtered.isEmpty()) item { AdminEmptyState("Belum ada pesanan pada status ini.") }
        }
    }
}

@Composable
private fun AdminOrderCard(order: AdminOrderDto, onUpdateStatus: suspend (String, String) -> String?) {
    var menuOpen by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = AdminSurface), border = androidx.compose.foundation.BorderStroke(1.dp, AdminBorder)) {
        Column(Modifier.fillMaxWidth().padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(order.orderNumber, fontWeight = FontWeight.Bold, color = AdminInk)
                    Text(order.customerName, style = MaterialTheme.typography.bodySmall, color = AppColors.TextSecondary)
                }
                AdminStatusPill(adminStatusLabel(order.status), statusColor(order.status))
            }
            Spacer(Modifier.height(12.dp)); HorizontalDivider(color = AdminBorder); Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column { Text("Total", style = MaterialTheme.typography.labelSmall, color = AppColors.TextSecondary); Text(formatRupiah(order.total.toInt()), fontWeight = FontWeight.SemiBold) }
                Column(horizontalAlignment = Alignment.End) { Text("Terbayar", style = MaterialTheme.typography.labelSmall, color = AppColors.TextSecondary); Text(formatRupiah(order.paidAmount.toInt()), color = AdminGreen, fontWeight = FontWeight.SemiBold) }
            }
            Spacer(Modifier.height(12.dp))
            Box(Modifier.align(Alignment.End)) {
                OutlinedButton(onClick = { menuOpen = true }, shape = RoundedCornerShape(7.dp)) { Text("Ubah Status") }
                DropdownMenu(expanded = menuOpen, onDismissRequest = { menuOpen = false }) {
                    listOf("pending", "processing", "packed", "shipped", "completed", "cancelled").forEach { status ->
                        DropdownMenuItem(text = { Text(adminStatusLabel(status)) }, onClick = { menuOpen = false; scope.launch { onUpdateStatus(order.id, status) } })
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminStatusPill(text: String, color: Color) {
    Surface(color = color.copy(alpha = 0.13f), shape = RoundedCornerShape(20.dp)) { Text(text, color = color, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) }
}

private fun adminStatusLabel(status: String) = when (status) {
    "pending" -> "Menunggu"
    "processing" -> "Diproses"
    "packed" -> "Dikemas"
    "shipped" -> "Dikirim"
    "completed" -> "Selesai"
    "cancelled" -> "Dibatalkan"
    "submitted" -> "Diajukan"
    else -> status
}

private fun statusColor(status: String) = when (status) {
    "completed" -> AdminGreen
    "cancelled" -> Color(0xFFB23B49)
    "shipped" -> Color(0xFF52739A)
    "packed" -> AdminAmber
    else -> AppColors.Primary
}

@Composable
fun AdminWorkshopsScreen(
    workshops: List<AdminWorkshopDto>,
    onDashboard: () -> Unit,
    onProducts: () -> Unit,
    onOrders: () -> Unit,
    onAdd: () -> Unit,
    onEdit: (String) -> Unit,
    onDeactivate: suspend (String) -> String?
) {
    val scope = rememberCoroutineScope()
    Scaffold(
        containerColor = AppColors.Background,
        topBar = { AdminTopBar("Kelola Workshop") },
        bottomBar = { AdminBottomBar("workshops", onDashboard, onProducts, onOrders, {}) },
        floatingActionButton = { ExtendedFloatingActionButton(onClick = onAdd, containerColor = AppColors.Primary, contentColor = Color.White, icon = { Icon(Icons.Filled.Add, null) }, text = { Text("Tambah Workshop") }) }
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(workshops, key = { it.id }) { workshop ->
                Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = AdminSurface), border = androidx.compose.foundation.BorderStroke(1.dp, AdminBorder)) {
                    Column(Modifier.padding(15.dp)) {
                        Row(verticalAlignment = Alignment.Top) {
                            Column(Modifier.weight(1f)) {
                                Text(workshop.title, fontWeight = FontWeight.Bold, maxLines = 2)
                                Text(workshop.location, style = MaterialTheme.typography.bodySmall, color = AppColors.TextSecondary)
                            }
                            AdminStatusPill(if (workshop.isActive) "Aktif" else "Nonaktif", if (workshop.isActive) AdminGreen else Color(0xFF987E82))
                        }
                        Spacer(Modifier.height(10.dp))
                        Text(workshop.eventDate.take(10), style = MaterialTheme.typography.bodySmall)
                        Text(formatRupiah(workshop.price.toInt()), color = AppColors.Primary, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            TextButton(onClick = { onEdit(workshop.id) }) { Icon(Icons.Filled.Edit, null, Modifier.size(17.dp)); Spacer(Modifier.width(5.dp)); Text("Ubah") }
                            TextButton(onClick = { scope.launch { onDeactivate(workshop.id) } }, enabled = workshop.isActive) { Icon(Icons.Filled.DeleteOutline, null, Modifier.size(17.dp)); Spacer(Modifier.width(5.dp)); Text("Nonaktifkan") }
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(72.dp)) }
        }
    }
}

@Composable
fun AdminWorkshopFormScreen(
    workshop: AdminWorkshopDto?,
    onBack: () -> Unit,
    onSave: suspend (AdminWorkshopRequest) -> String?
) {
    var title by remember(workshop) { mutableStateOf(workshop?.title.orEmpty()) }
    var slug by remember(workshop) { mutableStateOf(workshop?.slug.orEmpty()) }
    var description by remember(workshop) { mutableStateOf(workshop?.description.orEmpty()) }
    var eventDate by remember(workshop) { mutableStateOf(workshop?.eventDate.orEmpty()) }
    var endTime by remember(workshop) { mutableStateOf(workshop?.endTime.orEmpty()) }
    var location by remember(workshop) { mutableStateOf(workshop?.location.orEmpty()) }
    var price by remember(workshop) { mutableStateOf(workshop?.price?.toInt()?.toString().orEmpty()) }
    var isActive by remember(workshop) { mutableStateOf(workshop?.isActive ?: true) }
    var error by remember { mutableStateOf<String?>(null) }
    var saving by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Scaffold(containerColor = AppColors.Background, topBar = { AdminTopBar(if (workshop == null) "Tambah Workshop" else "Ubah Workshop", onBack) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AdminField("Nama Workshop", title) { title = it; if (workshop == null) slug = it.lowercase().trim().replace(" ", "-") }
            AdminField("Slug", slug) { slug = it }
            AdminField("Deskripsi", description, singleLine = false, minLines = 4) { description = it }
            AdminField("Tanggal & Waktu", eventDate) { eventDate = it }
            AdminField("Waktu Selesai", endTime) { endTime = it }
            AdminField("Lokasi", location) { location = it }
            AdminField("Harga", price, keyboardType = KeyboardType.Number) { price = it }
            AdminToggleRow("Workshop Aktif", "Tampil pada aplikasi pelanggan", isActive) { isActive = it }
            error?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
            Button(
                onClick = { scope.launch { saving = true; error = onSave(AdminWorkshopRequest(title, slug, description, eventDate, endTime, location, price.toDoubleOrNull() ?: 0.0, isActive)); saving = false; if (error == null) onBack() } },
                enabled = !saving && listOf(title, slug, description, eventDate, endTime, location).all { it.isNotBlank() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.Primary)
            ) { Text(if (saving) "Menyimpan..." else "Simpan Workshop") }
        }
    }
}

private val previewDashboard = AdminDashboardDto(8, 2, 12, 2, 1_250_000.0)
private val previewProduct = AdminProductDto(id = "1", name = "Vest Biru", slug = "vest-biru", categoryId = "1", category = "Vest", shortDescription = null, description = "Vest rajut handmade", price = 80_000.0, stock = 3, availabilityType = "ready_stock", preorderDuration = null, isFeatured = true, isActive = true)
private val previewOrder = AdminOrderDto(id = "1", orderNumber = "ARJ1234567", status = "packed", customerName = "Azzahra Putri", customerEmail = "azzahra@gmail.com", total = 92_000.0, paidAmount = 92_000.0, remainingPayment = 0.0)
private val previewWorkshop = AdminWorkshopDto(id = "1", title = "Beginner Crochet Bag Workshop", slug = "beginner-crochet", description = "Belajar crochet", eventDate = "2026-09-30T10:00:00+07:00", endTime = "13.00", location = "Bandung Creative Hub", price = 155_000.0, isActive = true)

@Preview @Composable fun AdminDashboardPreview() { AdminDashboardScreen(previewDashboard, listOf(previewProduct), listOf(previewOrder), {}, {}, {}, {}, {}) }
@Preview @Composable fun AdminProductsPreview() { AdminProductsScreen(listOf(previewProduct), {}, {}, {}, {}, {}, { null }) }
@Preview @Composable fun AdminProductFormPreview() { AdminProductFormScreen(previewProduct, listOf(AdminCategoryDto(id = "1", name = "Vest", slug = "vest", isActive = true)), {}, { null }) }
@Preview @Composable fun AdminOrdersPreview() { AdminOrdersScreen(listOf(previewOrder), {}, {}, {}, { _, _ -> null }) }
@Preview @Composable fun AdminWorkshopsPreview() { AdminWorkshopsScreen(listOf(previewWorkshop), {}, {}, {}, {}, {}, { null }) }
@Preview @Composable fun AdminWorkshopFormPreview() { AdminWorkshopFormScreen(previewWorkshop, {}, { null }) }
