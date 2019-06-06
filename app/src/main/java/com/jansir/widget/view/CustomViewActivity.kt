package com.jansir.widget.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.jansir.widget.R
import com.jansir.widget.view.clock.ClockActivity
import com.jansir.widget.view.countdown.CountDownActivity
import com.jansir.widget.view.rain.RainActivity
import com.jansir.widget.view.sun.SunUpDownActivity
import com.jansir.widget.view.taji.TaiJiActivity
import kotlinx.android.synthetic.main.activity_custom_view.*

/**
 * author: jansir.
 * package: com.jansir.widget.
 * date: 2019/5/28.
 */
class CustomViewActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)
        mBtnClock.setOnClickListener {
            ClockActivity.open(this)
        }
        mBtnTaiJi.setOnClickListener {
            TaiJiActivity.open(this)
        }
        mBtnRain.setOnClickListener {
            RainActivity.open(this)
        }
        mBtnSun.setOnClickListener {
            SunUpDownActivity.open(this)
        }
        mBtnCountDowm.setOnClickListener {
            CountDownActivity.open(this)
        }

    }

    companion object {
        fun open(context:Context){
            val intent=Intent(context, CustomViewActivity::class.java)
            context.startActivity(intent)
        }
    }
}
