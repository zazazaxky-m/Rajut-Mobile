package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.tubeskelompok1.rajutmobile.ui.AppColors
import kotlinx.coroutines.launch

@Composable
fun CustomOrderScreen(
    onBack: () -> Unit,
    onUpload: () -> Unit = {},
    onSubmit: suspend (String, String, String, String) -> String? = { _, _, _, _ -> null },
    onSubmitBerhasil: () -> Unit = {}
) {
    var productType by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var size by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = AppColors.Background,
        topBar = { ArajutTopBar(title = "Pesan Costum", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("Unggah Referensi", style = MaterialTheme.typography.bodyMedium)
            UploadBox(onClick = onUpload)
            CustomField("Jenis Produk", "Contoh: Cardigan", productType) { productType = it }
            CustomField("Warna", "Contoh: Biru muda", color) { color = it }
            CustomField("Ukuran", "Contoh: 20 cm x 30 cm", size) { size = it }
            CustomField(
                label = "Catatan Tambahan",
                placeholder = "Jelaskan detail yang kamu inginkan seperti jenis benang, motif, atau aksesoris lainnya",
                value = notes,
                singleLine = false,
                minLines = 4,
                onValueChange = { notes = it }
            )
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        errorMessage = onSubmit(productType, color, size, notes)
                        isLoading = false
                        if (errorMessage == null) onSubmitBerhasil()
                    }
                },
                enabled = !isLoading && productType.isNotBlank() && color.isNotBlank() && size.isNotBlank(),
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD27A85)),
                shape = RoundedCornerShape(26.dp)
            ) {
                Text(if (isLoading) "Mengirim..." else "Kirim Permintaan", color = Color.White)
            }
            errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
            Spacer(Modifier.height(18.dp))
        }
    }
}

@Composable
private fun UploadBox(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .drawBehind {
                drawRoundRect(
                    color = Color(0xFFC9AEB1),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(12.dp.toPx()),
                    style = Stroke(
                        width = 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f))
                    )
                )
            }
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Outlined.CloudUpload,
                contentDescription = "Unggah foto",
                tint = Color(0xFF6E2632)
            )
            Spacer(Modifier.height(10.dp))
            Text(
                "Ketuk untuk unggah foto referensi\nrajutanmu",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = AppColors.TextPrimary
            )
            Spacer(Modifier.height(14.dp))
            Text("JPG, PNG (MAX 5MB)", style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
private fun CustomField(
    label: String,
    placeholder: String,
    value: String,
    singleLine: Boolean = true,
    minLines: Int = 1,
    onValueChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color(0xFFC9C5C5)) },
            singleLine = singleLine,
            minLines = minLines,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFFB98C91),
                unfocusedBorderColor = Color(0xFFC9AEB1)
            )
        )
    }
}

@Preview
@Composable
fun CustomOrderScreenPreview() {
    CustomOrderScreen(onBack = {})
}
