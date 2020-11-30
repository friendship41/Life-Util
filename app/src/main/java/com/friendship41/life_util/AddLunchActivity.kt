package com.friendship41.life_util

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.friendship41.life_util.common.log
import kotlinx.android.synthetic.main.activity_add_lunch.*

class AddLunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lunch)

        tv_add_lunch_ok.setOnClickListener {
            val restaurantName: String = edt_restaurant_name.text.toString()
            if (restaurantName.isEmpty()) {
                log().info("식당명이 없음...")
                Toast.makeText(this.applicationContext, "식당명을 입력해야 합니다.", Toast.LENGTH_SHORT)
                edt_restaurant_name.requestFocus()
                return@setOnClickListener
            }
            val checkedPreference = findViewById<RadioButton>(rdg_preference.checkedRadioButtonId)
            log().info("add lunch restaurant name=$restaurantName, checkedPreference=${checkedPreference.text}")

            this.intent.putExtra("restaurantName", restaurantName)
            this.intent.putExtra("checkedPreference", checkedPreference.text.toString())
            this.setResult(RESULT_CODE_ADD_LUNCH_SUCCESS, this.intent)
            finish()
        }

        tv_add_lunch_cancel.setOnClickListener {
            finish()
        }
    }
}
