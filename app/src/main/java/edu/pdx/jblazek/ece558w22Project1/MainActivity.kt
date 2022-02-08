package edu.pdx.jblazek.ece558w22Project1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import edu.pdx.jblazek.ece558w22Project1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = "Main Activity"

    private lateinit var binding:ActivityMainBinding    // reference to view binding object
    private var calcWhat = 3                            // value to calculate (0 = ohms, 1 = volts, 2 = amps, > 2 invalid

    // Lambdas to implement Ohms Law
    val calcResistance: (Double, Double)->Double  = {volts: Double, amps: Double -> volts / amps}
    val calcVoltage: (Double, Double)->Double = {amps: Double, ohms: Double -> amps * ohms}
    val calcCurrent: (Double, Double)->Double = {volts: Double, ohms: Double -> volts / ohms}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // handle the Radio button group
        // initial selection is calculate resistance
        binding.funcRadioGrp.check(R.id.ohmsRadioBtn)
        calcWhat = 0
        binding.op1Label.text = getString(R.string.opLabel_volts)
        binding.op2Label.text = getString(R.string.opLabel_amps)

        // Radio gruop listener - changes operand labels and what to calculate
        binding.funcRadioGrp.setOnCheckedChangeListener { groupId, checkedID ->
            when(checkedID) {
                R.id.ohmsRadioBtn -> {
                    calcWhat = 0
                    binding.op1Label.text = getString(R.string.opLabel_volts)
                    binding.op2Label.text = getString(R.string.opLabel_amps)
                    binding.op1Value.setText("")
                    binding.op2Value.setText("")
                }
                R.id.voltsRadioBtn -> {
                    calcWhat = 1
                    binding.op1Label.text = getString(R.string.opLabel_amps)
                    binding.op2Label.text = getString(R.string.opLabel_ohms)
                    binding.op1Value.setText("")
                    binding.op2Value.setText("")
                }
                R.id.ampsRadioBtn -> {
                    calcWhat = 2
                    binding.op1Label.text = getString(R.string.opLabel_volts)
                    binding.op2Label.text = getString(R.string.opLabel_ohms)
                    binding.op1Value.setText("")
                    binding.op2Value.setText("")
                }
            }
        }

        // Calculate result listener
        binding.calcBtn.setOnClickListener {
            val op1String = binding.op1Value.text.toString().trim()
            val op2String = binding.op2Value.text.toString().trim()

            // operands must not be empty or blank and have a value > 0.0
            val op1Valid = (op1String.isNotBlank() && op1String.isNotEmpty() && op1String.toDouble() > 0.0)
            val op2Valid = (op2String.isNotBlank() && op2String.isNotEmpty() && op2String.toDouble() > 0.0)
            var result = 0.0
            Log.d(TAG, "*CALCULATE*")

            if (op1Valid && op2Valid) {
                result = when (calcWhat) {
                    0 -> calcResistance(op1String.toDouble(), op2String.toDouble())
                    1 -> calcVoltage(op1String.toDouble(), op2String.toDouble())
                    2 -> calcCurrent(op1String.toDouble(), op2String.toDouble())
                    else -> -1.0    // illegal value but should never get here
                }
                binding.resultText.setText(result.toString())

                val funcString = when(calcWhat) {
                    0 -> "(R = V/I)"
                    1 -> "(V = I/R)"
                    2 -> "(A = V/R)"
                    else -> "Error"
                }
                Log.d(TAG, "$funcString: First Operand: $op1String, Second Operand: $op2String,  Calculated result: $result" )
            }
            else {
                binding.resultText.setText("")
                binding.op1Value.setText("")
                binding.op2Value.setText("")
                Toast.makeText(this, getString(R.string.invalid_operand_message), Toast.LENGTH_SHORT).show()
            }
        }
    }
}