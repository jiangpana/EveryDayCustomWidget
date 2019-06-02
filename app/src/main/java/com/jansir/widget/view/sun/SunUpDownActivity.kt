package com.jansir.widget.view.sun

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.jansir.widget.R
import com.jansir.widget.view.clock.ClockActivity
import com.jansir.widget.view.rain.RainActivity
import com.jansir.widget.view.taji.TaiJiActivity
import kotlinx.android.synthetic.main.activity_custom_view.*
import kotlinx.android.synthetic.main.activity_sun_up_down.*

/**
 * author: jansir.
 * package: com.jansir.widget.
 * date: 2019/5/28.
 */
class SunUpDownActivity: AppCompatActivity() {


    private var mCurrentTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sun_up_down)
        initView()

    }

    private fun initView() {
        mCurrentTime = "15:40"
        mBtnStart?.setOnClickListener {
            mSunView?.setTimes("05:10", "18:40", mCurrentTime!!)
            mBtnStart.text = "当前时间：" + mCurrentTime
        }
    }

    companion object {
        fun open(context:Context){
            val intent=Intent(context, SunUpDownActivity::class.java)
            context.startActivity(intent)
        }
    }
}
