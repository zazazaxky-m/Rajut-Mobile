package org.tubeskelompok1.rajutmobile.model

import org.jetbrains.compose.resources.DrawableResource
import org.tubeskelompok1.rajutmobile.generated.resources.Res
import org.tubeskelompok1.rajutmobile.generated.resources.workshop_beginner
import org.tubeskelompok1.rajutmobile.generated.resources.workshop_group
import org.tubeskelompok1.rajutmobile.generated.resources.workshop_poster

data class Workshop(
    val id: Int,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val price: String = "Rp155.000",
    val description: String = "Tingkatkan kreativitas dan pelajari keahlian baru dalam membuat kerajinan rajut buatan tangan bersama mentor berpengalaman!",
    val image: DrawableResource = Res.drawable.workshop_beginner,
    val posterImage: DrawableResource = Res.drawable.workshop_poster,
    val whatYouGet: List<String> = listOf(
        "Kit & Tools: Seluruh perlengkapan dan benang rajut premium sudah disediakan.",
        "Your Craft: Hasil karya buatanmu sendiri yang bisa langsung dibawa pulang!",
        "New Skills & Friends: Pengalaman belajar teknik merajut dari dasar dan networking bersama komunitas crafting."
    ),
    val contact: String = "DM Instagram @arajut.id atau WhatsApp 0858-4688-2724"
)

object WorkshopData {
    val items = listOf(
        Workshop(
            id = 1,
            title = "Beginner Crochet Bag Workshop",
            date = "Sabtu, 30 September 2026",
            time = "10.00 - Selesai (Your Bag Ready)",
            location = "Bandung Creative Hub, Jl. Laswi No. 7",
            price = "Rp155.000",
            description = "Tingkatkan kreativitas dan pelajari keahlian baru dalam membuat tas rajut sendiri dari nol bersama Arajut.",
            image = Res.drawable.workshop_beginner,
            posterImage = Res.drawable.workshop_poster,
            whatYouGet = listOf(
                "Kit & Tools: Seluruh perlengkapan dan bahan merajut sudah disediakan lengkap.",
                "Your Crochet Bag: Hasil karya tas rajut buatanmu sendiri yang bisa langsung dibawa pulang!",
                "New Friend & New Skills: Pengalaman belajar merajut dari dasar, keahlian baru, serta teman baru sesama pecinta crafting."
            ),
            contact = "Direct Message (DM) Instagram: @arajut.id / WhatsApp: 0858-4688-2724"
        ),
        Workshop(
            id = 2,
            title = "Intermediate Amigurumi Class",
            date = "Minggu, 15 Oktober 2026",
            time = "13.00 - 16.00 WIB",
            location = "Komorebi Studio, Bandung",
            price = "Rp175.000",
            description = "Kelas merajut boneka amigurumi tingkat menengah. Pelajari pola 3D, teknik stitching rapi, dan finishing karakter lucu.",
            image = Res.drawable.workshop_group,
            posterImage = Res.drawable.workshop_group,
            whatYouGet = listOf(
                "Benang rajut katun susu premium + hook amigurumi.",
                "Pola eksklusif boneka rajut Arajut dan dakron isian.",
                "Sertifikat workshop & merchandise spesial Arajut."
            ),
            contact = "Direct Message (DM) Instagram: @arajut.id / WhatsApp: 0858-4688-2724"
        )
    )
}
