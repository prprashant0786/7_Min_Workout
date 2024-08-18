package com.example.a7minworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minworkout.databinding.ActivityExerciseBinding
import com.example.a7minworkout.databinding.DialogCustomBackButtonBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding : ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exersiseTimer: CountDownTimer? = null
    private var exersiseProgress = 0

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var exerciseidx : Int = -1;

    private var restTime : Long = 11; // TODO: Make it 11
    private var ExerciseTime : Long = 30; // TODO: Make it 30

    private var tts : TextToSpeech? = null

    private var player : MediaPlayer? = null


    private var exerciseAdapter : ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarexer)

        if (supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarexer?.setNavigationOnClickListener {
            customDialogeOpen()
        }

        tts = TextToSpeech(this,this)


        exerciseList = Constant.defaultExerciseList()
        setUpResttimer()

        setupExerciseStatusRecyclerView()
        
    }

    private fun setupExerciseStatusRecyclerView(){
        binding?.rvExerStatus?.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)

        binding?.rvExerStatus?.adapter = exerciseAdapter
    }

    private fun setUpResttimer(){

        try{
            val uri = Uri.parse("android.resource://com.example.a7minworkout/" + R.raw.press_start)

            player = MediaPlayer.create(applicationContext,uri)
            player?.isLooping = false
            player?.start()
        }
        catch (e :Exception){
            e.printStackTrace()
        }
        binding?.flprogress?.visibility = View.VISIBLE
        binding?.tvtittle?.visibility = View.VISIBLE

        binding?.flExerprogress?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.GONE
        binding?.tvExerciseName?.visibility = View.GONE
        if (restTimer!=null){
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.tvtittle?.text = "Next Exercise ${exerciseList?.get(exerciseidx+1)?.getName()}"


        setRestProgressbar()
    }
    private fun setUpExerciseTimer(){
        binding?.flprogress?.visibility = View.INVISIBLE
        binding?.tvtittle?.visibility = View.INVISIBLE

        binding?.flExerprogress?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE


        if (exersiseTimer!=null){
            exersiseTimer?.cancel()
            exersiseProgress = 0
        }



        binding?.tvExerciseName?.text = exerciseList?.get(exerciseidx)?.getName()
        binding?.ivImage?.setImageResource(exerciseList!![exerciseidx].getImage())
        speekout("START ${exerciseList?.get(exerciseidx)?.getName()}")
        setExersiseProgress()

    }

    private fun setRestProgressbar(){

        binding?.progressbar?.progress = restProgress
        // To implement CountDown
        restTimer = object : CountDownTimer(restTime*1000,1000){// totaltime , after that time we do
            override fun onTick(millisUntilFinished: Long) {
                restProgress++;
                binding?.progressbar?.progress = 11 - restProgress
                binding?.tvTimer?.text =  (11 - restProgress).toString()
            }

            override fun onFinish() {
                exerciseidx++

                exerciseList!!.get(exerciseidx).setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setUpExerciseTimer()
            }

        }.start()
    }


    private fun setExersiseProgress(){

        binding?.Exerprogressbar?.progress = exersiseProgress

        exersiseTimer = object : CountDownTimer(ExerciseTime*1000,1000){// totaltime , after that time we do
        override fun onTick(millisUntilFinished: Long) {
            exersiseProgress++;
            binding?.Exerprogressbar?.progress = 30 - exersiseProgress
            binding?.tvExerTimer?.text = (30 - exersiseProgress).toString()
        }

            override fun onFinish() {

                exerciseList!!.get(exerciseidx).setIsSelected(false)
                exerciseList!!.get(exerciseidx).setIsCompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()

                if (exerciseidx < exerciseList?.size!! - 1){
                    setUpResttimer()
                }
                else {
                    val intent = Intent(this@ExerciseActivity,finishAct::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (restTimer!=null){
            restTimer?.cancel()
            restProgress = 0
            binding?.flprogress?.visibility = View.VISIBLE

        }
        if (exersiseTimer!=null){
            exersiseTimer?.cancel()
            exersiseProgress = 0
        }

        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if (player!=null){
            player!!.stop()
        }

        binding = null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.US)


            if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Log.e("TTS","LANG SPECIFIED IS NOT SUPPORTED")
            }
        }
        else Log.e("TTS","Initialization Failed")
    }

    private fun speekout(text : String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onBackPressed() {
        customDialogeOpen()
    }

    private fun customDialogeOpen(){
        val custoDialog = Dialog(this)
        val DialogBinding = DialogCustomBackButtonBinding.inflate(layoutInflater)
        custoDialog.setContentView(DialogBinding.root)

        custoDialog.setCanceledOnTouchOutside(false)

        DialogBinding.btYes.setOnClickListener {
            this@ExerciseActivity.finish()
            custoDialog.dismiss()
        }
        DialogBinding.btNo.setOnClickListener {
            custoDialog.dismiss()
        }

        custoDialog.show()
    }
}