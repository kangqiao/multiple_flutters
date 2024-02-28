package com.zp.multiple_flutters.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.zp.multiple_flutters.android.databinding.ActivityMainBinding
import com.zp.multiple_flutters.android.models.CounterViewModel
import io.flutter.embedding.android.FlutterActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  val counterViewModel: CounterViewModel by lazy {
        getAppViewModel()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.launchButton.setOnClickListener {
            startActivity(
                FlutterActivity
                    .withCachedEngine(ENGINE_ID)
                    .build(this)
            )
        }

        counterViewModel.count.observe(this, Observer {
            binding.counterLabel.text = "Current count: " + it
        })

    }
}