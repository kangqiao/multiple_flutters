package com.zp.multiple_flutters.android

import android.app.Activity
import androidx.lifecycle.Observer
import com.zp.multiple_flutters.android.models.CounterViewModel
import io.flutter.FlutterInjector
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel


/**
 * This interface represents the notifications an EngineBindings may be receiving from the Flutter
 * instance.
 *
 * What methods this interface has depends on the messages that are sent over the EngineBinding's
 * channel in `main.dart`.  Messages that interact with the DataModel are handled automatically
 * by the EngineBindings.
 *
 * @see main.dart for what messages are getting sent from Flutter.
 */
interface EngineBindingsDelegate {
    fun onNext()
}

/**
 * This binds a FlutterEngine instance with the DataModel and a channel for communicating with that
 * engine.
 *
 * Messages involving the DataModel are handled by the EngineBindings, other messages are forwarded
 * to the EngineBindingsDelegate.
 *
 * @see main.dart for what messages are getting sent from Flutter.
 */
class EngineBindings(activity: Activity, delegate: EngineBindingsDelegate, entrypoint: String, initialRoute: String? = null) :
    NFSharedDataModelObserver,
    MethodChannel.MethodCallHandler {

    val channel: MethodChannel
    val engine: FlutterEngine
    val delegate: EngineBindingsDelegate

    init {
        val app = activity.applicationContext as MyApp
        // This has to be lazy to avoid creation before the FlutterEngineGroup.
        val dartEntrypoint = DartExecutor.DartEntrypoint(
            FlutterInjector.instance().flutterLoader().findAppBundlePath(), entrypoint
        )
        engine = app.engines.createAndRunEngine(activity, dartEntrypoint, initialRoute)
        this.delegate = delegate
        channel = MethodChannel(engine.dartExecutor.binaryMessenger, "com.zp.multiple_flutters/counter")
    }

    fun attach() {
        NFSharedDataModel.addObserver(this)
        channel.invokeMethod("setCount", NFSharedDataModel.counter)
        channel.setMethodCallHandler(this)
    }

    fun detach() {
        engine.destroy()
        NFSharedDataModel.removeObserver(this)
        channel.setMethodCallHandler(null)
    }

    /**
     * Native侧变更counter后的通知回调
     * (注: 所有EngineBindings绑定的channel都会收到此通知)
     */
    override fun onCountUpdate(newCount: Int) {
        channel.invokeMethod("setCount", newCount)
    }

    /**
     * 当前EngineBindings创建的FlutterEngine所关联的channel, 收到来自Flutter侧的method回调
     */
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "getCount" -> {
                result.success(NFSharedDataModel.counter)
            }

            "incrementCount" -> {
                NFSharedDataModel.incrementCount()
                result.success(null)
            }

            "next" -> {
                this.delegate.onNext()
                result.success(null)
            }

            else -> {
                result.notImplemented()
            }
        }
    }
}