package com.friendship41.life_util

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.friendship41.life_util.common.*
import com.friendship41.life_util.service.EmptyRestaurantMapException
import com.friendship41.life_util.service.getRandomRestaurant
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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

    override fun onResume() {
        super.onResume()
        if (!getFileNameSet(this.applicationContext).contains(ROOM_NAME)) {
            saveFile(this.applicationContext, ROOM_NAME, "{}")
        }
        val restaurantMap = getRestaurantMapFromFile(this.applicationContext, ROOM_NAME)
        log().info("점심 리스트 >> ${Json.encodeToString(restaurantMap)}")

        btn_random_lunch_go.setOnClickListener {
            try {
                val selectedRandomRestaurant = getRandomRestaurant(restaurantMap)
                tv_random_restaurant_name.setText(selectedRandomRestaurant.name, TextView.BufferType.NORMAL)
            } catch (e: EmptyRestaurantMapException) {
                log().info("등록된 식당이 없음...")
                Toast.makeText(this.applicationContext, e.message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

    }
}
