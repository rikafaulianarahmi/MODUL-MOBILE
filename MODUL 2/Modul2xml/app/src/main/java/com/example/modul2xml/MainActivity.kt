package com.example.modul2xml

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textfield.TextInputEditText
import java.util.Locale
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var etBillAmount: TextInputEditText
    private lateinit var spinnerTipPercentage: AutoCompleteTextView
    private lateinit var switchRoundUp: MaterialSwitch
    private lateinit var tvTipResult: TextView

    private var currentPercentage = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etBillAmount = findViewById(R.id.etBillAmount)
        spinnerTipPercentage = findViewById(R.id.spinnerTipPercentage)
        switchRoundUp = findViewById(R.id.switchRoundUp)
        tvTipResult = findViewById(R.id.tvTipResult)

        setupDropdown()
        setupListeners()
    }

    private fun setupDropdown() {
        val options = arrayOf("15%", "18%", "20%")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, options)
        spinnerTipPercentage.setAdapter(adapter)

        spinnerTipPercentage.setOnItemClickListener { _, _, position, _ ->
            currentPercentage = when (position) {
                0 -> 15
                1 -> 18
                2 -> 20
                else -> 15
            }
            calculateTip()
        }
    }

    private fun setupListeners() {
        etBillAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateTip()
            }
        })

        switchRoundUp.setOnCheckedChangeListener { _, _ ->
            calculateTip()
        }
    }

    private fun calculateTip() {
        val amountStr = etBillAmount.text.toString()
        val amount = amountStr.toDoubleOrNull() ?: 0.0

        var tip = amount * (currentPercentage / 100.0)

        if (switchRoundUp.isChecked) {
            tip = ceil(tip)
        }

        tvTipResult.text = String.format(Locale.US, "Tip Amount: $%.2f", tip)
    }
}