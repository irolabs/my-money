package com.kodeiro.mymoney.activity


import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kodeiro.mymoney.App
import com.kodeiro.mymoney.R
import com.kodeiro.mymoney.SwipeToDelete
import com.kodeiro.mymoney.adapter.MoneyAdapter
import com.kodeiro.mymoney.data.Constant
import com.kodeiro.mymoney.data.model.ModelKas
import com.kodeiro.mymoney.utils.Converter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    //Animasi FloatingAction Button
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_close_animation
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.to_bottom_anim
        )
    }

    //variabel
    private var clicked = false
    var cashflow: ArrayList<ModelKas> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Glide.with(this)
            .load(R.drawable.profile)
            .into(profile_image)

        fabAdd.setOnClickListener {
            addOnButtonClicked()
        }




        //Btn Penambahan Data
        btnCreate.setOnClickListener {
            val intentAdd = Intent(this@MainActivity, AddMoneyActivity::class.java)
            startActivity(intentAdd)
        }

    }

    override fun onResume() {
        super.onResume()
        getData()
        getTotal()
    }

    fun getData() {
        cashflow.clear()
        rvCashflow.adapter = null
        rvCashflow.setHasFixedSize(true)
        cashflow.addAll(App.db!!.getData())
        showRecyclerCashflow()
    }

    private fun showRecyclerCashflow() {
        rvCashflow.layoutManager = LinearLayoutManager(this)
        val listCashflowAdapter = MoneyAdapter(cashflow)
        rvCashflow.adapter = listCashflowAdapter
        //SwipeToDelete
        val item = object :SwipeToDelete(this,0,ItemTouchHelper.LEFT){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)
                listCashflowAdapter.del(viewHolder.adapterPosition)
                alertDialog()
            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(rvCashflow)
    }

    private fun getTotal() {
        App.db!!.getTotalSaldo()
        income.text = Converter.currencyFormat(Constant.pemasukan.toString())
        outcome.text = Converter.currencyFormat(Constant.pengeluaran.toString())

        val currentSaldo =
            Converter.currencyFormat((Constant.pemasukan - Constant.pengeluaran).toString())
        saldo.text = "Rp $currentSaldo"
        mymoney.text = currentSaldo


    }

    private fun addOnButtonClicked() {
        setClicked(clicked)
        clicked = !clicked
    }

    private fun setClicked(clicked: Boolean) {
        if (!clicked) {
            btnCreate.visibility = View.VISIBLE
            btnCreate.isClickable = true
            fabAdd.startAnimation(rotateOpen)
            btnCreate.startAnimation(fromBottom)
        } else {
            btnCreate.visibility = View.INVISIBLE
            btnCreate.isClickable = false
            fabAdd.startAnimation(rotateClose)
            btnCreate.startAnimation(toBottom)
        }
    }

    fun alertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfrimasi Penghapusan")
        builder.setMessage("Apakah Anda Yakin Menghapus Data Ini ?")
        builder.setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
            App.db!!.deleteData(Constant.transaksi_id!!)
            var mssg = "Data Berhasil Dihapus"
            Toast.makeText(applicationContext, mssg, Toast.LENGTH_SHORT).show()
            getData();getTotal()
            dialog.dismiss()
        })
        builder.setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        builder.show()
    }


}