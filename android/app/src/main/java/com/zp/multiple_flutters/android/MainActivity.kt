package com.zp.multiple_flutters.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zp.multiple_flutters.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            // TODO:
        }
    }
}