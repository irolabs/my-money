package com.kodeiro.mymoney.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.kodeiro.mymoney.App
import com.kodeiro.mymoney.R
import com.kodeiro.mymoney.adapter.MoneyAdapter.Companion.EXTRA_TRANSAKSI
import com.kodeiro.mymoney.data.model.ModelKas
import com.kodeiro.mymoney.utils.Converter
import kotlinx.android.synthetic.main.activity_update.*
import java.util.*

class UpdateActivity : AppCompatActivity(),
    AdapterView.OnItemSelectedListener, View.OnClickListener {

    var day = 0
    var month = 0
    var year = 0

    private var arrayAdapter: ArrayAdapter<String>? = null
    private var option = arrayOf("Pemasukan", "Pengeluaran")
    var status: String? = null
    var updateTanggal: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)


        val itemCashFlow = intent.getParcelableExtra<ModelKas>(EXTRA_TRANSAKSI)
        edtUpdateKet.setText(itemCashFlow.keterangan)
        edtUpdateJml.setText(itemCashFlow.jumlah.toString())
        edtUpdateTgl.setText(Converter.dateFormat(itemCashFlow.tanggal.toString()))


        //Setup Spinner
        arrayAdapter =
            ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, option)
        updateSpinner?.adapter = arrayAdapter
        updateSpinner?.onItemSelectedListener = this

        pickDate()

        btnSimpanUpdate.setOnClickListener(this)



    }


    private fun pickDate() {
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        edtUpdateTgl.setOnClickListener {
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                updateTanggal = "$dayOfMonth-$month-$year"
                edtUpdateTgl.setText(updateTanggal)
            },year,month,day)
            dpd.show()
        }
    }



    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var items = option[position]
        when (items) {
            "Pemasukan" -> status = "MASUK"
            "Pengeluaran" -> status = "KELUAR"
        }
        Log.e("_logStatus", status)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        val toast = "Pilih Tipe Transaksi"
        Toast.makeText(applicationContext, toast, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        if (status.isNullOrBlank() || edtUpdateJml.text.isNullOrBlank() || edtUpdateKet.text.isNullOrBlank()|| edtUpdateTgl.text.isNullOrBlank()) {
            var errmssg = "Isi Data Dengan Benar"
            Toast.makeText(applicationContext, "$errmssg", Toast.LENGTH_SHORT).show()
        } else {
            val itemCashFlow = intent.getParcelableExtra<ModelKas>(EXTRA_TRANSAKSI)
            App.db!!.updateData(itemCashFlow.transaksi_id,status.toString(),edtUpdateJml.text.toString().toLong(),edtUpdateKet.text.toString(),updateTanggal.toString() )
            var mssg = "Isi Data Dengan Benar"
            Toast.makeText(applicationContext, "$mssg", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}