package com.kodeiro.mymoney.data.model

import android.os.Parcel
import android.os.Parcelable

data class ModelKas(
    val transaksi_id: Int,
    val jumlah:Long,
    val status: String?,
    val keterangan: String?,
    val tanggal: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(transaksi_id)
        parcel.writeLong(jumlah)
        parcel.writeString(status)
        parcel.writeString(keterangan)
        parcel.writeString(tanggal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelKas> {
        override fun createFromParcel(parcel: Parcel): ModelKas {
            return ModelKas(parcel)
        }

        override fun newArray(size: Int): Array<ModelKas?> {
            return arrayOfNulls(size)
        }
    }
}