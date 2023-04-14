package com.example.poilabschallange.presentation.detail.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.poilabschallange.R

class DetailActivity : AppCompatActivity() {

    companion object{
        const val POPULATION = "population"
        const val AREA = "area"
        const val CURRENCY = "currency"
        const val FLAG_URL = "flagUrl"
        const val FLAG_DESC = "flagDesc"
        const val COUNTRY_NAME = "name"
        const val CAPITAL_NAME = "capital"
        const val INDEPENDENT = "independent"
        const val PHONE = "phone"
        const val REGION = "region"
        const val LANGUAGE = "languages"
        const val LAT = "lat"
        const val LON = "long"
        const val BORDERS = "borders"
        const val TIME_ZONE = "timeZone"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}