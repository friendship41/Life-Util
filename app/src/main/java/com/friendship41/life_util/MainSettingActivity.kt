package com.friendship41.life_util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_setting.*
import java.util.logging.Logger

class MainSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_setting)

        btn_main_setting_lunch.setOnClickListener {
            this.startActivity(Intent(this, LunchListActivity::class.java))
        }
    }
}
