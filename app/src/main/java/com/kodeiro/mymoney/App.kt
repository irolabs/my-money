package com.kodeiro.mymoney

import android.app.Application
import com.kodeiro.mymoney.data.db.DatabaseHelper

class App:Application() {

    companion object{
        var db: DatabaseHelper? = null
    }

    override fun onCreate() {
        super.onCreate()

        db = DatabaseHelper(this)

    }


}