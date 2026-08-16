package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Textsms
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.tubeskelompok1.rajutmobile.generated.resources.Res
import org.tubeskelompok1.rajutmobile.generated.resources.profile_azzahra
import org.tubeskelompok1.rajutmobile.data.remote.AddressDto
import org.tubeskelompok1.rajutmobile.data.remote.AddressRequest
import org.tubeskelompok1.rajutmobile.data.remote.UserDto
import org.tubeskelompok1.rajutmobile.ui.AppColors
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    onHome: () -> Unit,
    onOrders: () -> Unit,
    onAccount: () -> Unit,
    onAddress: () -> Unit,
    onLogout: () -> Unit,
    user: UserDto? = null
) {
    var showLogout by remember { mutableStateOf(false) }
    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            Surface(
                color = Color(0xFFFFE3E7),
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Profil",
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.TextPrimary
                    )
                }
            }
        },
        bottomBar = { ArajutBottomBar("profile", onHome, onOrders, {}) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(28.dp))
            Box {
                Image(
                    painterResource(Res.drawable.profile_azzahra),
                    "Azzahra Putri",
                    Modifier.size(132.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Surface(
                    color = Color(0xFFA34B57),
                    shape = CircleShape,
                    modifier = Modifier.align(Alignment.BottomEnd).size(38.dp)
                ) { Box(contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Edit, "Ubah foto", tint = Color.White, modifier = Modifier.size(19.dp)) } }
            }
            Spacer(Modifier.height(15.dp))
            Text(user?.name ?: "Azzahra Putri", fontWeight = FontWeight.SemiBold)
            Text(user?.email ?: "azzahra@gmail.com", style = MaterialTheme.typography.labelSmall, color = AppColors.TextSecondary)
            Spacer(Modifier.height(28.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                ProfileMenuRow(Icons.Outlined.Person, "Informasi Akun", onAccount)
                HorizontalDivider(color = Color(0xFFF0E5E7))
                ProfileMenuRow(Icons.Outlined.LocationOn, "Alamat Saya", onAddress)
                HorizontalDivider(color = Color(0xFFF0E5E7))
                ProfileMenuRow(Icons.Outlined.Textsms, "Hubungi Seller (WhatsApp)", {})
            }
            Spacer(Modifier.height(24.dp))
            OutlinedButton(
                onClick = { showLogout = true },
                modifier = Modifier.fillMaxWidth().height(58.dp),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, Color(0xFFFF4F5E)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF4F5E))
            ) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, null, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(12.dp))
                Text("Keluar")
                Spacer(Modifier.weight(1f))
            }
        }
    }
    if (showLogout) {
        AlertDialog(
            onDismissRequest = { showLogout = false },
            shape = RoundedCornerShape(24.dp),
            title = { Text("Yakin Ingin Keluar?", modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center, style = MaterialTheme.typography.titleMedium) },
            text = { Text("Anda perlu masuk kembali untuk\nmengakses keranjang dan pesanan Anda.", modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center, style = MaterialTheme.typography.bodySmall) },
            dismissButton = { OutlinedButton(onClick = { showLogout = false }, border = BorderStroke(1.dp, Color(0xFFD27A85))) { Text("Batal", color = Color(0xFFD27A85)) } },
            confirmButton = { Button(onClick = onLogout, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD27A85))) { Text("Keluar", color = Color.White) } },
            containerColor = Color.White
        )
    }
}

@Composable
private fun ProfileMenuRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 18.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(color = Color(0xFFFFE5E8), shape = CircleShape, modifier = Modifier.size(38.dp)) {
            Box(contentAlignment = Alignment.Center) { Icon(icon, null, tint = Color(0xFFB75B68), modifier = Modifier.size(20.dp)) }
        }
        Spacer(Modifier.width(14.dp))
        Text(label, style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, modifier = Modifier.size(22.dp))
    }
}

@Composable
fun AddressListScreen(
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onAdd: () -> Unit,
    addresses: List<AddressDto> = emptyList(),
    onDelete: (String) -> Unit = {}
) {
    Scaffold(
        containerColor = AppColors.Background,
        topBar = { ArajutTopBar("Alamat Saya", onBack) },
        bottomBar = {
            Surface(color = AppColors.Background) {
                Button(onClick = onAdd, Modifier.fillMaxWidth().padding(22.dp).height(50.dp), shape = RoundedCornerShape(25.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD27A85))) {
                    Text("Tambah Alamat Baru", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            addresses.forEach { address ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Home, null, tint = Color(0xFF9C4653), modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(address.label ?: "Alamat", color = Color(0xFF8D3440), fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.weight(1f))
                    if (address.isDefault) AddressStatusPill("Utama")
                }
                Spacer(Modifier.height(10.dp))
                Text(address.recipientName, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(5.dp))
                Text(address.phone, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(7.dp))
                Text("${address.addressLine}, ${address.city}, ${address.province}\n${address.postalCode}", style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(10.dp))
                HorizontalDivider(color = Color(0xFFC9AEB3))
                Spacer(Modifier.height(8.dp))
                Row {
                    TextButton(onClick = onEdit, contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)) { Icon(Icons.Outlined.Edit, null, Modifier.size(16.dp)); Spacer(Modifier.width(5.dp)); Text("Ubah", color = Color(0xFF8D3440), style = MaterialTheme.typography.labelSmall) }
                    Spacer(Modifier.width(14.dp))
                    TextButton(onClick = { onDelete(address.id) }, contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)) { Icon(Icons.Outlined.DeleteOutline, null, Modifier.size(16.dp)); Spacer(Modifier.width(5.dp)); Text("Hapus", color = AppColors.TextPrimary, style = MaterialTheme.typography.labelSmall) }
                }
                    }
                }
            }
        }
    }
}

@Composable
private fun AddressStatusPill(text: String) {
    Surface(color = Color(0xFFEBD9DC), shape = RoundedCornerShape(12.dp)) {
        Text(
            text,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF8D5060),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
        )
    }
}

@Composable
fun AddressFormScreen(
    isEdit: Boolean,
    onBack: () -> Unit,
    initialAddress: AddressDto? = null,
    onSave: suspend (AddressRequest) -> String? = { null },
    onSaved: () -> Unit = {}
) {
    val defaults = initialAddress?.let {
        listOf(it.label.orEmpty(), it.recipientName, it.phone, it.addressLine, it.city, it.province, it.postalCode)
    } ?: List(7) { "" }
    val labels = listOf("Label Alamat", "Nama Penerima", "Nomor Telepon", "Alamat Lengkap", "Kota", "Provinsi", "Kode Pos")
    val placeholders = listOf("Contoh: Rumah", "Masukkan nama penerima", "Masukkan nomor telepon penerima", "Masukkan alamat lengkap penerima", "Masukkan kota penerima", "Masukkan provinsi penerima", "Masukkan kode pos penerima")
    var values by remember(initialAddress) { mutableStateOf(defaults) }
    var primary by remember(initialAddress) { mutableStateOf(initialAddress?.isDefault ?: false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isSaving by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Scaffold(
        containerColor = AppColors.Background,
        topBar = { ArajutTopBar(if (isEdit) "Ubah Alamat" else "Tambah Alamat", onBack) }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            labels.forEachIndexed { index, label ->
                Text(label, style = MaterialTheme.typography.bodySmall)
                OutlinedTextField(
                    value = values[index],
                    onValueChange = { newValue -> values = values.toMutableList().also { it[index] = newValue } },
                    placeholder = { Text(placeholders[index], color = Color(0xFFC7C2C3), style = MaterialTheme.typography.bodySmall) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White, focusedBorderColor = Color(0xFFB98C91), unfocusedBorderColor = Color(0xFFC9AEB1))
                )
            }
            Spacer(Modifier.height(8.dp))
            Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
                Row(Modifier.fillMaxWidth().padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                    Surface(color = Color(0xFFFFE5E8), shape = CircleShape, modifier = Modifier.size(46.dp)) { Box(contentAlignment = Alignment.Center) { Icon(Icons.Outlined.LocationOn, null, tint = Color(0xFFB75B68)) } }
                    Spacer(Modifier.width(14.dp))
                    Column(Modifier.weight(1f)) { Text("Jadikan Alamat Utama", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodySmall); Text("Alamat ini akan digunakan\nsecara otomatis saat checkout.", style = MaterialTheme.typography.labelSmall) }
                    Switch(checked = primary, onCheckedChange = { primary = it }, colors = SwitchDefaults.colors(checkedTrackColor = Color(0xFFD27A85)))
                }
            }
            Spacer(Modifier.height(16.dp))
            errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
            Button(
                onClick = {
                    scope.launch {
                        isSaving = true
                        errorMessage = onSave(
                            AddressRequest(
                                label = values[0],
                                recipientName = values[1],
                                phone = values[2],
                                addressLine = values[3],
                                city = values[4],
                                district = values[4],
                                province = values[5],
                                postalCode = values[6],
                                isDefault = primary
                            )
                        )
                        isSaving = false
                        if (errorMessage == null) onSaved()
                    }
                },
                enabled = !isSaving && values.all { it.isNotBlank() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD27A85))
            ) {
                Text(if (isSaving) "Menyimpan..." else if (isEdit) "Simpan Perubahan" else "Simpan Alamat", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
            if (isEdit) TextButton(onClick = {}, Modifier.fillMaxWidth()) { Text("Hapus Alamat", color = Color(0xFFB34E5C)) }
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun AccountInfoScreen(onBack: () -> Unit, user: UserDto? = null) {
    Scaffold(containerColor = AppColors.Background, topBar = { ArajutTopBar("Informasi Akun", onBack) }) { padding ->
        Card(
            modifier = Modifier.padding(padding).padding(22.dp).fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            AccountRow("NAMA LENGKAP", user?.name ?: "Azzahra Putri")
            HorizontalDivider(color = Color(0xFFF0E5E7))
            AccountRow("EMAIL", user?.email ?: "azzahra@gmail.com")
            HorizontalDivider(color = Color(0xFFF0E5E7))
            AccountRow("NOMOR TELEPON", user?.phone ?: "-")
        }
    }
}

@Composable
private fun AccountRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 15.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) { Text(label, style = MaterialTheme.typography.labelSmall, color = Color(0xFFA4888D)); Spacer(Modifier.height(4.dp)); Text(value, style = MaterialTheme.typography.bodySmall) }
        Icon(Icons.Outlined.Edit, "Ubah", tint = Color(0xFF4E1821), modifier = Modifier.size(20.dp))
    }
}

@Preview
@Composable
fun ProfileScreenPreview() { ProfileScreen({}, {}, {}, {}, {}) }

@Preview
@Composable
fun AddressListScreenPreview() { AddressListScreen({}, {}, {}) }

@Preview
@Composable
fun EditAddressScreenPreview() { AddressFormScreen(true, {}, initialAddress = null) }

@Preview
@Composable
fun AddAddressScreenPreview() { AddressFormScreen(false, {}) }

@Preview
@Composable
fun AccountInfoScreenPreview() { AccountInfoScreen({}) }
