package org.tubeskelompok1.rajutmobile.model

// Model data untuk satu produk rajutan
data class Produk(
    val id: Int,
    val nama: String,
    val harga: Int,
    val deskripsi: String,
    val warna: String,
    val stok: Int
)

// Model data untuk satu item di keranjang (produk + jumlah beli)
data class ItemKeranjang(
    val produk: Produk,
    var jumlah: Int
)

// Model data untuk satu riwayat transaksi
data class Transaksi(
    val id: Int,
    val tanggal: String,
    val items: List<ItemKeranjang>,
    val totalHarga: Int,
    val status: String
)

// ==== DATA MOCKUP (dummy, belum dari backend) ====
object DataMockup {

    val daftarProduk = listOf(
        Produk(
            id = 1,
            nama = "Topi Rajut Anak",
            harga = 45000,
            deskripsi = "Topi rajut lembut untuk anak-anak, hangat dan nyaman dipakai sehari-hari.",
            warna = "Kuning",
            stok = 12
        ),
        Produk(
            id = 2,
            nama = "Syal Rajut Wol",
            harga = 85000,
            deskripsi = "Syal rajut berbahan wol premium, cocok untuk cuaca dingin.",
            warna = "Abu-abu",
            stok = 8
        ),
        Produk(
            id = 3,
            nama = "Tas Rajut Serut",
            harga = 60000,
            deskripsi = "Tas rajut model serut, motif polos minimalis, muat banyak barang.",
            warna = "Krem",
            stok = 20
        ),
        Produk(
            id = 4,
            nama = "Sweater Rajut Wanita",
            harga = 150000,
            deskripsi = "Sweater rajut oversize dengan motif kabel klasik.",
            warna = "Merah Marun",
            stok = 5
        ),
        Produk(
            id = 5,
            nama = "Sarung Tangan Rajut",
            harga = 35000,
            deskripsi = "Sarung tangan rajut hangat, elastis dan pas di tangan.",
            warna = "Coklat",
            stok = 15
        ),
        Produk(
            id = 6,
            nama = "Dompet Rajut Mini",
            harga = 40000,
            deskripsi = "Dompet rajut ukuran mini, cocok untuk menyimpan koin dan kartu.",
            warna = "Pink",
            stok = 10
        )
    )

    // Riwayat transaksi dummy, supaya halaman Riwayat tidak kosong saat pertama dibuka
    val daftarTransaksiAwal = listOf(
        Transaksi(
            id = 1001,
            tanggal = "20 Juli 2026",
            items = listOf(
                ItemKeranjang(daftarProduk[0], 2),
                ItemKeranjang(daftarProduk[2], 1)
            ),
            totalHarga = (45000 * 2) + 60000,
            status = "Selesai"
        ),
        Transaksi(
            id = 1002,
            tanggal = "15 Juli 2026",
            items = listOf(
                ItemKeranjang(daftarProduk[3], 1)
            ),
            totalHarga = 150000,
            status = "Selesai"
        )
    )

    fun cariProdukById(id: Int): Produk? = daftarProduk.find { it.id == id }
}