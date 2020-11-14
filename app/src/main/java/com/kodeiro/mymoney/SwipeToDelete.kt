package com.kodeiro.mymoney

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kodeiro.mymoney.data.Constant
import com.kodeiro.mymoney.data.model.ModelKas


abstract class SwipeToDelete (context: Context, dragDir:Int, swipeDir:Int):ItemTouchHelper.SimpleCallback(dragDir,swipeDir){

    var cashflow: ArrayList<ModelKas> = arrayListOf()
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //Constant.transaksi_id = cashflow[viewHolder.adapterPosition].transaksi_id
    }


}