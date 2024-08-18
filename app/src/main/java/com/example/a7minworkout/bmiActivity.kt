package com.example.a7minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class bmiActivity : AppCompatActivity() {

    var binding : ActivityBmiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarexer)
        if (supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate Your BMI"
        }
        binding?.toolbarexer?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btncalcul?.setOnClickListener {
        if (validateMetricUnits()){

            val heightValue: Float = binding?.etht?.text.toString().toFloat() / 100

            val weightValue: Float = binding?.etwt?.text.toString().toFloat()

            val bmi = weightValue / (heightValue * heightValue)

            displayBMIResult(bmi)
        }
            else{
                Toast.makeText(this,
                    "Bhai Data to De uske bina kase Btao",Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun displayBMIResult(bmi : Float){

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        //Use to set the result layout visible
        binding?.llDisplay?.visibility = View.VISIBLE

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.tvgetbmi?.text = bmiValue // Value is set to TextView
        binding?.tvgettype?.text = bmiLabel // Label is set to TextView
        binding?.tvgetdes?.text = bmiDescription // Description is set to TextView
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding?.etht?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etwt?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}