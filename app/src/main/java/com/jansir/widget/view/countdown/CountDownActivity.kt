package com.jansir.widget.view.countdown

import android.app.ProgressDialog.show
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import com.jansir.widget.R
import com.jansir.widget.utils.ToastUtil
import com.jansir.widget.view.clock.ClockActivity
import com.jansir.widget.view.rain.RainActivity
import com.jansir.widget.view.sun.SunUpDownActivity
import com.jansir.widget.view.taji.TaiJiActivity
import kotlinx.android.synthetic.main.activity_countdown.*
import kotlinx.android.synthetic.main.activity_custom_view.*
import java.util.*
import kotlin.concurrent.timer

/**
 * author: jansir.
 * package: com.jansir.widget.
 * date: 2019/5/28.
 */
class CountDownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown)
        mBtnStart.setOnClickListener {
            if (mCountDown.time == 0) {
                ToastUtil.showToast(this,"请拖动选择时间！")
            } else {
                mBtnStart.isClickable=false
                val minute=mCountDown.time;
                mCountDown.isStartCountDown(true)
                val timer=object :CountDownTimer((minute* 60 * 1000).toLong(),1000){
                    override fun onFinish() {
                        mCountDown.setTimeText("00:00")
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        val timeText=TimeUtil.formatTime(millisUntilFinished)
                        mCountDown.setTimeText(timeText)
                    }

                }
                timer.start()
            }
        }
    }

    companion object {
        fun open(context: Context) {
            val intent = Intent(context, CountDownActivity::class.java)
            context.startActivity(intent)
        }
    }
}
