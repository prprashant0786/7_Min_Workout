package com.example.a7minworkout

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a7minworkout.databinding.ActivityFinishBinding
import java.lang.Exception

class finishAct : AppCompatActivity() {

    private var binding:ActivityFinishBinding? = null

    private var player : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarexer)

        if (supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarexer?.setNavigationOnClickListener {
            onBackPressed()
            player?.stop()
        }

        try{
            val uri = Uri.parse("android.resource://com.example.a7minworkout/" + R.raw.apl_sound)

            player = MediaPlayer.create(applicationContext,uri)
            player?.isLooping = false
            player?.start()
        }
        catch (e : Exception){
            e.printStackTrace()
        }

        binding?.tvfinish?.setOnClickListener {
            val intent = Intent(this@finishAct,MainActivity::class.java)
            player?.stop()
            startActivity(intent)

        }

    }

    override fun onDestroy() {
        binding = null
        if (player!=null){
            player!!.stop()
        }
        super.onDestroy()
    }
}