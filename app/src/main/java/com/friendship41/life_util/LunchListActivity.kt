package com.friendship41.life_util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_lunch_list.*

const val REQUEST_CODE_ADD_LUNCH = 101
const val RESULT_CODE_ADD_LUNCH_SUCCESS = 201

class LunchListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lunch_list)

        tv_lunch_list_plus.setOnClickListener {
            this.startActivityForResult(Intent(this, AddLunchActivity::class.java), REQUEST_CODE_ADD_LUNCH)
        }

        tv_lunch_list_cancel.setOnClickListener {
            this.finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_ADD_LUNCH -> {
                // TODO: 파일에 아이템 추가
            }
        }
    }
}
