package com.example.modul1xml

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ivDice1: ImageView = findViewById(R.id.ivDice1)
        val ivDice2: ImageView = findViewById(R.id.ivDice2)
        val btnRoll: Button = findViewById(R.id.btnRoll)

        btnRoll.setOnClickListener {
            val diceValue1 = (1..6).random()
            val diceValue2 = (1..6).random()

            ivDice1.setImageResource(getDiceImage(diceValue1))
            ivDice2.setImageResource(getDiceImage(diceValue2))

            if (diceValue1 == diceValue2) {
                Toast.makeText(this, "Selamat, anda dapat dadu double!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Anda belum beruntung!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getDiceImage(value: Int): Int {
        return when (value) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.dice_0
        }
    }
}