package com.example.benja.poebrowser

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.benja.poebrowser.tasks.UpdateNotifierTask

class MainActivity : AppCompatActivity() {

    var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handler = Handler()
    }

    override fun onStart() {
        super.onStart()
        val runnable = updateNotify()
        runnable.run()
    }

    fun updateNotify(): Runnable {
        return Runnable {
            val notifier = UpdateNotifierTask(this)
            try {
                notifier.execute()
            } finally {
                handler!!.postDelayed(updateNotify(), 1000 * 120)
            }
        }
    }
}
