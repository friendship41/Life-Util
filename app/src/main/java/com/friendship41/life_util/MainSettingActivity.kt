package com.friendship41.life_util

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_setting.*

class MainSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_setting)

        tv_main_bottom_lunch.setOnClickListener {
            this.startActivity(Intent(this, MainActivity::class.java))
        }

        btn_main_setting_lunch.setOnClickListener {
            this.startActivity(Intent(this, LunchListActivity::class.java))
        }
    }
}
