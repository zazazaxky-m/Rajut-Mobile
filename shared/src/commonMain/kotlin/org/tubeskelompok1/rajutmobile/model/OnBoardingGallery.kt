package org.tubeskelompok1.rajutmobile.model

import org.jetbrains.compose.resources.DrawableResource
import org.tubeskelompok1.rajutmobile.generated.resources.Res
import org.tubeskelompok1.rajutmobile.generated.resources.onboarding_1
import org.tubeskelompok1.rajutmobile.generated.resources.onboarding_2
import org.tubeskelompok1.rajutmobile.generated.resources.onboarding_3

// Satu slide onboarding: gambar + judul + deskripsi
data class OnboardingItem(
    val image: DrawableResource,
    val title: String,
    val description: String
)

object DataOnboarding {
    val gallery = listOf(
        OnboardingItem(
            image = Res.drawable.onboarding_1,
            title = "Temukan Produk Rajut Handmade",
            description = "Jelajahi berbagai produk rajut unik yang dibuat dengan penuh kreativitas dan ketelitian."
        ),
        OnboardingItem(
            image = Res.drawable.onboarding_2,
            title = "Custom Sesuai Keinginanmu",
            description = "Pesan produk rajut dengan warna, ukuran, dan desain favoritmu."
        ),
        OnboardingItem(
            image = Res.drawable.onboarding_3,
            title = "Pantau Progress Pesanan",
            description = "Rajutanmu sedang dibuat dengan penuh perhatian. Pantau setiap prosesnya hingga sampai ke tanganmu."
        )
    )
}