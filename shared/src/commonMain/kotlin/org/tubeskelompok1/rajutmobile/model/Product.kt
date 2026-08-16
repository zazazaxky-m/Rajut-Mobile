package org.tubeskelompok1.rajutmobile.model

import org.jetbrains.compose.resources.DrawableResource
import org.tubeskelompok1.rajutmobile.generated.resources.Res
import org.tubeskelompok1.rajutmobile.generated.resources.product_baby_shoes
import org.tubeskelompok1.rajutmobile.generated.resources.product_bag_gray
import org.tubeskelompok1.rajutmobile.generated.resources.product_handbag_red
import org.tubeskelompok1.rajutmobile.generated.resources.product_hat_yellow
import org.tubeskelompok1.rajutmobile.generated.resources.product_keychain_flower
import org.tubeskelompok1.rajutmobile.generated.resources.product_sling_cream
import org.tubeskelompok1.rajutmobile.generated.resources.product_totebag_orange
import org.tubeskelompok1.rajutmobile.generated.resources.product_vest_blue

data class Produk(
    val id: String,
    val nama: String,
    val harga: Int,
    val deskripsi: String,
    val warna: String,
    val stok: Int,
    val kategori: String,
    val gambar: DrawableResource,
    val imageUrl: String? = null
)

data class ItemKeranjang(
    val produk: Produk,
    var jumlah: Int
)

data class Transaksi(
    val id: Int,
    val tanggal: String,
    val items: List<ItemKeranjang>,
    val totalHarga: Int,
    val status: String
)

object DataMockup {
    val daftarProduk = listOf(
        Produk(
            id = "1",
            nama = "Vest biru",
            harga = 80_000,
            deskripsi = "Pancarkan gaya retro yang manis dengan Ocean Granny Square Top dari Arajut! Atasan rajut buatan tangan dengan desain penuh perpaduan warna biru dan krem yang estetik, pas banget untuk menyempurnakan OOTD harianmu.",
            warna = "Biru dan krem",
            stok = 8,
            kategori = "Vest",
            gambar = Res.drawable.product_vest_blue
        ),
        Produk(
            id = "2",
            nama = "Sling bag krem",
            harga = 70_000,
            deskripsi = "Tas selempang rajut handmade dengan motif bunga timbul dan warna krem yang lembut.",
            warna = "Krem",
            stok = 10,
            kategori = "Tas",
            gambar = Res.drawable.product_sling_cream
        ),
        Produk(
            id = "3",
            nama = "Sepatu Bayi",
            harga = 75_000,
            deskripsi = "Sepatu bayi rajut yang lembut, ringan, dan nyaman untuk menemani langkah kecil buah hati.",
            warna = "Kuning dan hijau",
            stok = 6,
            kategori = "Sepatu",
            gambar = Res.drawable.product_baby_shoes
        ),
        Produk(
            id = "4",
            nama = "Keychain bunga",
            harga = 50_000,
            deskripsi = "Gantungan kunci berbentuk bunga yang dibuat dengan rajutan detail dalam warna merah muda dan ungu.",
            warna = "Pink dan ungu",
            stok = 15,
            kategori = "Keychain",
            gambar = Res.drawable.product_keychain_flower
        ),
        Produk(
            id = "5",
            nama = "Hand bag merah",
            harga = 50_000,
            deskripsi = "Hand bag rajut merah dengan tali panjang, praktis untuk aktivitas sehari-hari.",
            warna = "Merah",
            stok = 9,
            kategori = "Tas",
            gambar = Res.drawable.product_handbag_red
        ),
        Produk(
            id = "6",
            nama = "Tas abu",
            harga = 80_000,
            deskripsi = "Tas rajut abu bermotif bunga dengan bentuk yang lapang dan tali bahu yang nyaman.",
            warna = "Abu-abu",
            stok = 7,
            kategori = "Tas",
            gambar = Res.drawable.product_bag_gray
        ),
        Produk(
            id = "7",
            nama = "Topi kuning",
            harga = 60_000,
            deskripsi = "Topi rajut kuning dengan detail bunga, cocok untuk tampilan ceria dan kasual.",
            warna = "Kuning",
            stok = 12,
            kategori = "Topi",
            gambar = Res.drawable.product_hat_yellow
        ),
        Produk(
            id = "8",
            nama = "Totebag oranye",
            harga = 60_000,
            deskripsi = "Totebag rajut oranye dengan pola geometris yang ringan untuk dibawa sehari-hari.",
            warna = "Oranye",
            stok = 11,
            kategori = "Tas",
            gambar = Res.drawable.product_totebag_orange
        )
    )

    val customProduct = Produk(
        id = "100",
        nama = "Totebag Pink",
        harga = 70_000,
        deskripsi = "Totebag rajut custom sesuai referensi dan ukuran pilihan pelanggan.",
        warna = "Pink",
        stok = 1,
        kategori = "Custom",
        gambar = Res.drawable.product_handbag_red
    )

    val daftarTransaksiAwal = listOf(
        Transaksi(
            id = 1001,
            tanggal = "20 Juli 2026",
            items = listOf(ItemKeranjang(daftarProduk[0], 1), ItemKeranjang(daftarProduk[3], 1)),
            totalHarga = 130_000,
            status = "Selesai"
        )
    )

    fun cariProdukById(id: String): Produk? = daftarProduk.find { it.id == id }
    fun cariProdukById(id: Int): Produk? = daftarProduk.find { it.id == id.toString() }
}

fun formatRupiah(value: Int): String {
    val formatted = value.toString().reversed().chunked(3).joinToString(".").reversed()
    return "Rp$formatted"
}
