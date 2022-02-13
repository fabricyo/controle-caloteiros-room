package com.fabricyo.quandodeve.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Debt(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var nome: String,
    var ultimaMod: String,
    var valorUltimaMod: Int,
    var valorDebt: Int,
    var color: String
) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented e nem irei")
    }
}