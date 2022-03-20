package com.mwping.android.plugin.sampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.perf.metrics.AddTrace

class MainActivity : AppCompatActivity() {

    @AddTrace(name = "MainActivity_onCreate")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}