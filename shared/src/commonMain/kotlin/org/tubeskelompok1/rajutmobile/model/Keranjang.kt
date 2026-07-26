package org.tubeskelompok1.rajutmobile.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

/**
 * Object singleton sederhana untuk menyimpan state keranjang & riwayat transaksi
 * selama aplikasi berjalan (belum tersambung backend, jadi datanya hilang saat app ditutup).
 *
 * Ini BUKAN cara yang "benar-benar production ready" (idealnya pakai ViewModel + Repository),
 * tapi cukup untuk keperluan slicing UI dengan data mockup.
 */
object CartManager {

    // daftar isi keranjang, "mutableStateListOf" supaya UI otomatis update saat isinya berubah
    val itemKeranjang = mutableStateListOf<ItemKeranjang>()

    // daftar riwayat transaksi, diawali data dummy
    val riwayatTransaksi = mutableStateListOf<Transaksi>().apply {
        addAll(DataMockup.daftarTransaksiAwal)
    }

    private var counterIdTransaksi = mutableStateOf(1003)

    fun tambahKeKeranjang(produk: Produk, jumlah: Int = 1) {
        val existing = itemKeranjang.find { it.produk.id == produk.id }
        if (existing != null) {
            existing.jumlah += jumlah
        } else {
            itemKeranjang.add(ItemKeranjang(produk, jumlah))
        }
    }

    fun hapusDariKeranjang(produk: Produk) {
        itemKeranjang.removeAll { it.produk.id == produk.id }
    }

    fun totalHargaKeranjang(): Int {
        return itemKeranjang.sumOf { it.produk.harga * it.jumlah }
    }

    // Simulasi checkout: pindahkan isi keranjang jadi 1 transaksi baru di riwayat, lalu kosongkan keranjang
    fun checkout(tanggal: String) {
        if (itemKeranjang.isEmpty()) return

        val transaksiBaru = Transaksi(
            id = counterIdTransaksi.value,
            tanggal = tanggal,
            items = itemKeranjang.toList(),
            totalHarga = totalHargaKeranjang(),
            status = "Diproses"
        )
        counterIdTransaksi.value += 1

        riwayatTransaksi.add(0, transaksiBaru) // tambahkan di paling atas
        itemKeranjang.clear()
    }
}