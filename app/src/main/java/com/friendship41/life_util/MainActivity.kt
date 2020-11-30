package com.friendship41.life_util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.friendship41.life_util.common.log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_main_bottom_setting.setOnClickListener {
            log().info("bottom setting tv clicked")
            this.startActivity(Intent(this, MainSettingActivity::class.java))
            this.finish()
        }
    }
}
