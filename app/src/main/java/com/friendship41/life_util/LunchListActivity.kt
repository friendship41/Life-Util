package com.friendship41.life_util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.friendship41.life_util.common.getFile
import com.friendship41.life_util.common.getFileNameSet
import com.friendship41.life_util.common.log
import com.friendship41.life_util.common.saveFile
import com.jayway.jsonpath.JsonPath
import kotlinx.android.synthetic.main.activity_lunch_list.*
import java.io.File

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

        lv_lunch_list.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            lunchMap.map { it.key }.toTypedArray())

        lv_lunch_list.setOnItemClickListener { adapterView, _, position, _ ->
            log().info(lunchMap[adapterView.getItemAtPosition(position)].toString())
        }

//        lv_lunch_list.setOnItemLongClickListener { adapterView, view, position, id ->
//            AlertDialog.B
//        }
    }
}
