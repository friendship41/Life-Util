package com.friendship41.life_util

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.friendship41.life_util.common.getFile
import com.friendship41.life_util.common.getFileNameSet
import com.friendship41.life_util.common.log
import com.friendship41.life_util.common.saveFile
import com.friendship41.life_util.data.Restaurant
import com.friendship41.life_util.service.getRandomRestaurant
import com.jayway.jsonpath.JsonPath
import kotlinx.android.synthetic.main.activity_main.*

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
        val restaurantMap = JsonPath.parse(getFile(this.applicationContext, ROOM_NAME)).json<HashMap<String, Restaurant>>()

        btn_random_lunch_go.setOnClickListener {
            val selectedRandomRestaurant = getRandomRestaurant(restaurantMap)
            tv_random_restaurant_name.setText(selectedRandomRestaurant.name, TextView.BufferType.NORMAL)
        }

    }
}
