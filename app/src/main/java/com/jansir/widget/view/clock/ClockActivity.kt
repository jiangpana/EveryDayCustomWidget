package com.jansir.widget.view.clock

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jansir.widget.R
import kotlinx.android.synthetic.main.activity_clock.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * author: jansir.
 * package: com.jansir.widget.
 * date: 2019/5/28.
 */
class ClockActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock)
    }

    companion object {
        fun open(context:Context){
            val intent=Intent(context,ClockActivity::class.java)
            context.startActivity(intent)
        }
    }
}
