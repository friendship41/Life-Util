package com.friendship41.life_util

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.friendship41.life_util.common.getFileNameSet
import com.friendship41.life_util.common.getPeopleListFromFile
import com.friendship41.life_util.common.log
import com.friendship41.life_util.common.saveFile
import com.friendship41.life_util.data.PeopleFile
import kotlinx.android.synthetic.main.activity_pay.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PeopleListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_list)

        tv_people_list_cancel.setOnClickListener {
            finish()
        }

        tv_people_list_plus.setOnClickListener {
            this.startActivity(Intent(this, AddPeopleActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        val fileNameSet = getFileNameSet(this.applicationContext)
        if (!fileNameSet.contains(PEOPLE_FILE_NAME)) {
            saveFile(this.applicationContext, PEOPLE_FILE_NAME, "{\"peopleList\":[]}")
        }

        val personList = getPeopleListFromFile(this.applicationContext, PEOPLE_FILE_NAME)
        val adaptedList = personList.map { it.name }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            adaptedList)
        lv_people_list.adapter = adapter



        lv_people_list.setOnItemClickListener { _, _, position, _ ->
            log().info(personList[position].toString())
        }


        lv_people_list.setOnItemLongClickListener { adapterView, _, position, _ ->
            val selectedName = adapterView.getItemAtPosition(position) as String
            AlertDialog.Builder(this)
                .setTitle(selectedName)
                .setMessage("정말 삭제하시겠습니까?")
                .setPositiveButton("네") { _, _ ->
                    personList.removeAt(position)
                    saveFile(this.applicationContext, PEOPLE_FILE_NAME, Json.encodeToString(PeopleFile(personList)))
                    (adaptedList as ArrayList).removeAt(position)
                    adapter.notifyDataSetChanged()
                    log().info("after delete >> ${Json.encodeToString(PeopleFile(personList))}")
                }
                .setNegativeButton("아니오") { _, _ ->
                    log().info("아니오 클릭")
                }
                .show()

            false
        }
    }
}
