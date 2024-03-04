package com.zp.multiple_flutters.android

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.zp.multiple_flutters.android.databinding.ActivityDoubleFlutterBinding
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.engine.FlutterEngineCache


/**
 * Created by ZhaoPan on 2024/3/4
 */
class DoubleFlutterActivity : FragmentActivity(), EngineBindingsDelegate {
    private companion object {
        var engineCounter = 1
    }

    private val topEngineId: String
    private val bottomEngineId: String

    init {
        topEngineId = engineCounter++.toString()
        bottomEngineId = engineCounter++.toString()
    }

    private lateinit var bindings: ActivityDoubleFlutterBinding
    private val topBindings: EngineBindings by lazy {
        EngineBindings(activity = this, delegate = this, entrypoint = "topMain")
    }
    private val bottomBindings: EngineBindings by lazy {
        EngineBindings(activity = this, delegate = this, entrypoint = "bottomMain")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityDoubleFlutterBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        bindings.topContainer.tag = topEngineId
        FlutterEngineCache.getInstance().put(topEngineId, topBindings.engine)
        val topFragment = FlutterFragment.withCachedEngine(topEngineId).build<FlutterFragment>()
        supportFragmentManager.beginTransaction().add(R.id.topContainer, topFragment).commit()
        topBindings.attach()

        bindings.bottomContainer.tag = bottomEngineId
        FlutterEngineCache.getInstance().put(bottomEngineId, bottomBindings.engine)
        val bottomFragment = FlutterFragment.withCachedEngine(bottomEngineId).build<FlutterFragment>()
        supportFragmentManager.beginTransaction().add(R.id.bottomContainer, bottomFragment).commit()
        bottomBindings.attach()
    }

    override fun onDestroy() {
        // remove flutter engine from EngineCache
        FlutterEngineCache.getInstance().remove(topEngineId)
        FlutterEngineCache.getInstance().remove(bottomEngineId)

        super.onDestroy()

        bottomBindings.detach()
        topBindings.detach()
    }

    /**
     * 来自Flutter侧的回调
     * 注: 来自TopBindings或bottomBindings关联的引擎
     */
    override fun onNext() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}