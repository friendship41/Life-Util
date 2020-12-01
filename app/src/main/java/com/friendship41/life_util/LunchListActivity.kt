package com.friendship41.life_util

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.friendship41.life_util.common.getFile
import com.friendship41.life_util.common.getFileNameSet
import com.friendship41.life_util.common.log
import com.friendship41.life_util.common.saveFile
import com.jayway.jsonpath.JsonPath
import kotlinx.android.synthetic.main.activity_lunch_list.*

const val ROOM_NAME = "room_temp"

class LunchListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lunch_list)

        tv_lunch_list_plus.setOnClickListener {
            this.startActivity(Intent(this, AddLunchActivity::class.java))
        }

        tv_lunch_list_cancel.setOnClickListener {
            this.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!getFileNameSet(this.applicationContext).contains(ROOM_NAME)) {
            saveFile(this.applicationContext, ROOM_NAME, "{}")
        }
        val lunchMap = JsonPath.parse(getFile(this.applicationContext, ROOM_NAME)).json<HashMap<String, Any>>()

        val adaptedList = lunchMap.map { it.key }.toMutableList()
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            adaptedList)
        lv_lunch_list.adapter = adapter

        lv_lunch_list.setOnItemClickListener { adapterView, _, position, _ ->
            log().info(lunchMap[adapterView.getItemAtPosition(position)].toString())
        }

        lv_lunch_list.setOnItemLongClickListener { adapterView, _, position, _ ->
            val selectedRestaurantName = adapterView.getItemAtPosition(position) as String
            AlertDialog.Builder(this)
                .setTitle(selectedRestaurantName)
                .setMessage("정말 삭제하시겠습니까?")
                .setPositiveButton("네") { _, _ ->
                    lunchMap.remove(selectedRestaurantName)
                    saveFile(this.applicationContext, ROOM_NAME, JsonPath.parse(lunchMap).jsonString())
                    adaptedList.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
                .setNegativeButton("아니오") { _, _ ->
                    log().info("아니오 클릭")
                }
                .show()

            false
        }
    }
}
