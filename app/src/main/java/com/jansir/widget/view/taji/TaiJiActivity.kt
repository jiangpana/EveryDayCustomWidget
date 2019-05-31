package com.jansir.widget.view.taji

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jansir.widget.R
import kotlinx.android.synthetic.main.activity_taiji.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/**
 * author: jansir.
 * package: com.jansir.widget.
 * date: 2019/5/28.
 */
class TaiJiActivity: AppCompatActivity() {

    private lateinit var scheduleExecutorService:ScheduledExecutorService
    private  var scheduleFuture:ScheduledFuture<*>? = null
    private var degrees: Float = 0f

    private val runnable= Runnable {
        degrees += 1f
        mTaijiView.degrees=degrees
        if (degrees==360f){
            degrees= 0f;
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taiji)
        scheduleExecutorService=Executors.newSingleThreadScheduledExecutor();
        scheduleFuture=scheduleExecutorService.scheduleWithFixedDelay(runnable,300,10,TimeUnit.MILLISECONDS)
    }

    override fun onDestroy() {
        super.onDestroy()
        if ( !scheduleFuture?.isCancelled!!){
            scheduleFuture?.cancel(false)
            scheduleFuture = null
        }
    }

    companion object {
        fun open(context:Context){
            val intent=Intent(context,TaiJiActivity::class.java)
            context.startActivity(intent)
        }
    }
}
