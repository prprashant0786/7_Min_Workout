package com.example.a7minworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.a7minworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//        val startbtn : FrameLayout = findViewById(R.id.fl_star)


        binding?.flstar?.setOnClickListener {
            val intent  = Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.flbmi?.setOnClickListener {
            val intent = Intent(this,bmiActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null //always do this to save from memory leak
    }
}