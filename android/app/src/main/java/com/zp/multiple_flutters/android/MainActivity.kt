package com.zp.multiple_flutters.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.zp.multiple_flutters.android.databinding.ActivityMainBinding
import com.zp.multiple_flutters.android.models.CounterViewModel
import io.flutter.embedding.android.FlutterActivity

class MainActivity : AppCompatActivity(), NFSharedDataModelObserver {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAdd.setOnClickListener {
            NFSharedDataModel.incrementCount()
        }
        binding.btnOpenSingle.setOnClickListener {
            startActivity(
                Intent(this, SingleFlutterActivity::class.java)
            )
        }
        binding.btnOpenDouble.setOnClickListener {
            startActivity(
                Intent(this, DoubleFlutterActivity::class.java)
            )
        }
        binding.count.text = NFSharedDataModel.counter.toString()
        NFSharedDataModel.addObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        NFSharedDataModel.removeObserver(this)
    }

    override fun onCountUpdate(newCount: Int) {
        binding.count.text = newCount.toString()
    }
}