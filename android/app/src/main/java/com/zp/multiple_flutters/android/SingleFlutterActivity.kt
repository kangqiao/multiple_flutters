package com.zp.multiple_flutters.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine


/**
 * Created by ZhaoPan on 2024/3/4
 */
class SingleFlutterActivity : FlutterActivity(), EngineBindingsDelegate {

    private val engineBindings: EngineBindings by lazy {
        EngineBindings(activity = this, delegate = this, entrypoint = "bizModule02")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        engineBindings.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        engineBindings.detach()
    }

    override fun provideFlutterEngine(context: Context): FlutterEngine {
        return engineBindings.engine
    }

    override fun onNext() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}