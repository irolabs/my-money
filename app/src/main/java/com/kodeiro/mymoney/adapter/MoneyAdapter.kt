package com.kodeiro.mymoney.adapter



import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kodeiro.mymoney.R
import com.kodeiro.mymoney.activity.UpdateActivity
import com.kodeiro.mymoney.data.Constant
import com.kodeiro.mymoney.data.model.ModelKas
import com.kodeiro.mymoney.utils.Converter
import kotlinx.android.synthetic.main.item_reveneustream.view.*

class MoneyAdapter(
    private val modelKas: ArrayList<ModelKas>
) : RecyclerView.Adapter<MoneyAdapter.MoneyViewHolder>() {


    companion object{
        const val EXTRA_TRANSAKSI = "extra_transaksi"
    }

    fun del(position: Int){
        Constant.transaksi_id = modelKas[position].transaksi_id
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_reveneustream, parent, false)
        return MoneyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoneyViewHolder, position: Int) {
        holder.bind(modelKas[position])
    }

    override fun getItemCount(): Int = modelKas.size

    inner class MoneyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        fun bind(money: ModelKas) {
            with(itemView) {
                tvreveneu.text = money.jumlah.toString()
                status.text = money.status
                keterangan.text = money.keterangan
                tanggal.text = money.tanggal.toString()
                when (money.status) {
                    "MASUK" -> status.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.bluePrimary
                        )
                    )
                    "KELUAR" -> status.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.redPrimary
                        )
                    )
                }
                itemView.setOnClickListener(this@MoneyViewHolder)
            }
        }


        override fun onClick(v: View?) {
            val context = itemView.context
            val selectedCashflow: ModelKas = modelKas[adapterPosition]
            val intentUpdate = Intent(context, UpdateActivity::class.java)
            intentUpdate.putExtra(EXTRA_TRANSAKSI,selectedCashflow)
            context.startActivity(intentUpdate)

        }





    }


}