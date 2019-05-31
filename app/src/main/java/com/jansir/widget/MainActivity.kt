package com.jansir.widget

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import com.jansir.widget.customwidget.CustomWidgetActivity
import com.jansir.widget.view.CustomViewActivity
import com.jansir.widget.viewgroup.CustomViewGroupActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBtnView.viewTreeObserver.addOnPreDrawListener {
            LaunchTime.stopRecord()
            true }
        mBtnView.setOnClickListener {
            CustomViewActivity.open(this)
        }
        mBtnViewGroup.setOnClickListener {
            CustomViewGroupActivity.open(this)
        }
        mBtnWidget.setOnClickListener {
            CustomWidgetActivity.open(this)
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
