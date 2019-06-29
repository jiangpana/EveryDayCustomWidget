package com.jansir.widget.viewgroup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jansir.widget.R
import com.jansir.widget.viewgroup.loadView.WBLoadIngActivity
import com.jansir.widget.viewgroup.slidemenu.SlidingMenuActivity
import kotlinx.android.synthetic.main.activity_custom_viewgroup.*

/**
 * author: jansir.
 * package: com.jansir.widget.
 * date: 2019/5/28.
 */
class CustomViewGroupActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_viewgroup)
        mBtnWBLoading.setOnClickListener {
            WBLoadIngActivity.open(this)
        }
        mBtnSlidingMenu.setOnClickListener {
            SlidingMenuActivity.open(this)
        }
    }

    companion object {
        fun open(context: Context){
            val intent= Intent(context, CustomViewGroupActivity::class.java)
            context.startActivity(intent)
        }
    }
}
