package com.kodeiro.mymoney.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Converter {

    companion object{
        fun dateFormat(date: String) : String{
            var format = SimpleDateFormat("yyyy-mm-dd")
            var newDate: Date? = null

            try {
                newDate = format.parse(date)
            }catch (e:ParseException){}

            format = SimpleDateFormat("dd-mm-yyyy")

            return format.format(newDate)
        }

        fun currencyFormat(currency:String):String{
            var currencyFormat = NumberFormat.getInstance(Locale.GERMANY)
            return currencyFormat.format(currency.toLong())
        }




    }

}