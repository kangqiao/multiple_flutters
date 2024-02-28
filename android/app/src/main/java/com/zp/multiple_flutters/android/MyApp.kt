package com.zp.multiple_flutters.android

import androidx.multidex.MultiDexApplication
import com.zp.multiple_flutters.android.models.CounterViewModel
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel


/**
 * Created by ZhaoPan on 2024/2/27
 */

const val ENGINE_ID = "1"

class MyApp : MultiDexApplication() {
    companion object {
        private lateinit var _myApp: MyApp
        fun getInstance(): MyApp {return  _myApp}
    }

    private  val counterViewModel: CounterViewModel by lazy {
        AppScopeVMProvider.getAppScopeVM(CounterViewModel::class.java)
    }
    private lateinit var channel: MethodChannel

    override fun onCreate() {
        super.onCreate()
        _myApp = this
        // Instantiate a FlutterEngine.
        val flutterEngine = FlutterEngine(this)

        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache
            .getInstance()
            .put(ENGINE_ID, flutterEngine)

        channel = MethodChannel(flutterEngine.dartExecutor, "com.zp.multiple_flutters/counter")

        channel.setMethodCallHandler { call, _ ->
            when (call.method) {
                "incrementCounter" -> {
                    counterViewModel.increment()
                    reportCounter()
                }

                "requestCounter" -> {
                    reportCounter()
                }
            }
        }
    }

    private fun reportCounter() {
        channel.invokeMethod("reportCounter", counterViewModel.count.value)
    }
}