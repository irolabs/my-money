package com.kodeiro.mymoney.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.kodeiro.mymoney.data.Constant
import com.kodeiro.mymoney.data.model.ModelKas

class DatabaseHelper : SQLiteOpenHelper {

    companion object {
        val DBName = "irolabsMoney"
        val DBVersion = 1
        val tableName = "transaksi"
        val transaksiId = "transaksi_id"
        val status = "status"
        val jumlah = "jumlah"
        val keterangan = "keterangan"
        val tanggal = "tanggal"
    }


    //pembuatan variabel global untuk class
    var context: Context? = null
    var db: SQLiteDatabase

    //pembuatan create tabel database
    private val createTable =
        "CREATE TABLE $tableName($transaksiId INTEGER PRIMARY KEY AUTOINCREMENT " +
                ",$status TEXT, $jumlah TEXT,$keterangan TEXT, $tanggal DATETIME DEFAULT CURRENT_DATE)"


    constructor(context: Context) : super(context, DBName, null, DBVersion) {
        this.context = context
        db = this.writableDatabase
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $tableName")
    }

    fun insertData(sts: String, jml: String, ket: String): Long {
        val values = ContentValues()
        values.put(status, sts)
        values.put(jumlah, jml)
        values.put(keterangan, ket)


        //mengambil transaksi id
        val transaksi_id = db.insert(tableName, null, values)
        //db.close()
        return transaksi_id
    }

    fun getData(): List<ModelKas> {
        val cashflow = ArrayList<ModelKas>()

        //pilih semua kolom dari nama tabel yang diurutkan dari yang paling akhir berdasarkan transaksi id
        val strSql = "SELECT * FROM $tableName ORDER BY $transaksiId DESC"

        //membuat cursor untuk membaca index
        val cursor: Cursor = db.rawQuery(strSql, null)
        cursor.moveToFirst()

        //dihitung dari 0 sampai data yang diambil dari index berdasarkan variabel strsql
        for (i in 0 until cursor.count) {
            cursor.moveToPosition(i)
            cashflow.add(
                ModelKas(
                    cursor.getInt(cursor.getColumnIndex(transaksiId)),
                    cursor.getLong(cursor.getColumnIndex(jumlah)),
                    cursor.getString(cursor.getColumnIndex(status)),
                    cursor.getString(cursor.getColumnIndex(keterangan)),
                    cursor.getString(cursor.getColumnIndex(tanggal))
                )
            )
        }

        return cashflow
    }

    fun getTotalSaldo() {
        val strSql =
            "SELECT SUM($jumlah) AS total, " +
                    "(SELECT SUM($jumlah) FROM $tableName WHERE $status = 'MASUK') AS pemasukan, " +
                    "(SELECT SUM($jumlah) FROM $tableName WHERE $status = 'KELUAR') AS pengeluaran " +
                    "FROM $tableName"

        val cursor: Cursor = db.rawQuery(strSql,null)
        cursor.moveToFirst()

        Constant.pemasukan = cursor.getLong(cursor.getColumnIndex("pemasukan"))
        Constant.pengeluaran = cursor.getLong(cursor.getColumnIndex("pengeluaran"))

    }

    fun updateData(id:Int,sts:String,jml:Long,ket:String,tgl: String ) {
        val values = ContentValues()
        values.put(status,sts)
        values.put(jumlah, jml)
        values.put(keterangan, ket)
        values.put(tanggal, tgl)

        db.update(tableName,values,"$transaksiId='$id'",null)
    }

    fun deleteData(id:Int) {
        db.delete(tableName,"$transaksiId=$id",null)
    }


}