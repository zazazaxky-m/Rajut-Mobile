package org.tubeskelompok1.rajutmobile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.tubeskelompok1.rajutmobile.generated.resources.Res
import org.tubeskelompok1.rajutmobile.ui.AppColors
import org.tubeskelompok1.rajutmobile.generated.resources.logo
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onRegister: suspend (String, String, String, String) -> String? = { _, _, _, _ -> null },
    onRegisterBerhasil: () -> Unit,
    onBackToLogin: () -> Unit
) {
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var noTelepon by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var konfirmasiPassword by remember { mutableStateOf("") }
    var passwordTerlihat by remember { mutableStateOf(false) }
    var konfirmasiTerlihat by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painterResource(Res.drawable.logo),
            contentDescription = "logo Arajut",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Daftar Akun",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = AppColors.TextPrimary,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Buat akun dan temukan produk rajut favoritmu.",
            style = MaterialTheme.typography.bodyMedium,
            color = AppColors.TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )

        LabelField("Nama Lengkap")
        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            placeholder = { Text("Masukkan nama lengkap") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        LabelField("Email")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Masukkan alamat email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        LabelField("Nomor Telepon")
        OutlinedTextField(
            value = noTelepon,
            onValueChange = { noTelepon = it },
            placeholder = { Text("Masukkan nomor telepon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        LabelField("Kata Sandi")
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Masukkan kata sandi") },
            singleLine = true,
            visualTransformation = if (passwordTerlihat) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordTerlihat = !passwordTerlihat }) {
                    Icon(
                        imageVector = if (passwordTerlihat) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = "Tampilkan/sembunyikan kata sandi"
                    )
                }
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        LabelField("Konfirmasi Kata Sandi")
        OutlinedTextField(
            value = konfirmasiPassword,
            onValueChange = { konfirmasiPassword = it },
            placeholder = { Text("Konfirmasi kata sandi") },
            singleLine = true,
            visualTransformation = if (konfirmasiTerlihat) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { konfirmasiTerlihat = !konfirmasiTerlihat }) {
                    Icon(
                        imageVector = if (konfirmasiTerlihat) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = "Tampilkan/sembunyikan konfirmasi kata sandi"
                    )
                }
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (password != konfirmasiPassword) {
                    errorMessage = "Konfirmasi kata sandi tidak sama"
                } else {
                    scope.launch {
                        isLoading = true
                        errorMessage = onRegister(nama.trim(), email.trim(), noTelepon.trim(), password)
                        isLoading = false
                        if (errorMessage == null) onRegisterBerhasil()
                    }
                }
            },
            enabled = !isLoading && nama.isNotBlank() && email.isNotBlank() && password.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.Primary),
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text(if (isLoading) "Memproses..." else "Daftar", color = AppColors.White, fontWeight = FontWeight.SemiBold)
        }

        errorMessage?.let {
            Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 10.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Sudah punya akun? ", color = AppColors.TextPrimary)
            Text(
                text = "Masuk",
                color = AppColors.Primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { onBackToLogin() }
            )
        }
    }
}

@Composable
private fun LabelField(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelLarge,
        color = AppColors.TextPrimary,
        modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp, top = 4.dp)
    )
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        onRegisterBerhasil = {},
        onBackToLogin = {}
    )
}
