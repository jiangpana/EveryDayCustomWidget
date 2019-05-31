package com.jansir.widget.customwidget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jansir.widget.R

/**
 * author: jansir.
 * package: com.jansir.widget.
 * date: 2019/5/28.
 */
class CustomWidgetActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_widget)

    }
    companion object {
        fun open(context: Context){
            val intent= Intent(context, CustomWidgetActivity::class.java)
            context.startActivity(intent)
        }
    }
}
