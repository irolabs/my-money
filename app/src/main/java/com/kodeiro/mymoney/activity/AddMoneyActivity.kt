package com.kodeiro.mymoney.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.kodeiro.mymoney.App
import com.kodeiro.mymoney.R
import kotlinx.android.synthetic.main.activity_add_money.*

class AddMoneyActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    View.OnClickListener {

    private var arrayAdapter: ArrayAdapter<String>? = null
    private var option = arrayOf("Pemasukan", "Pengeluaran")
    var status: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_money)

        //Setup Spinner
        arrayAdapter =
            ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, option)
        addspinner?.adapter = arrayAdapter
        addspinner?.onItemSelectedListener = this

        //setup button
        btnSimpan.setOnClickListener(this)

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var items: String = option[position]
        when (items) {
            "Pemasukan" -> status = "MASUK"
            "Pengeluaran" -> status = "KELUAR"
        }
        Log.e("_logStatus", status)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        var toast = "Pilih Tipe Transaksi"
        Toast.makeText(applicationContext, "$toast", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        if (status.isNullOrBlank() || edtJumlah.text.isNullOrBlank() || edtKeterangan.text.isNullOrBlank()) {
            var errmssg = "Isi Data Dengan Benar"
            Toast.makeText(applicationContext, "$errmssg", Toast.LENGTH_SHORT).show()
        } else {
            var id = App.db!!.insertData(
                status,
                edtJumlah.text.toString(),
                edtKeterangan.text.toString()
            )
            Log.e("_logId", id.toString())
            if (id > 0) {
                Toast.makeText(
                    applicationContext,
                    "Transaksi Berhasil Disimpan",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
}