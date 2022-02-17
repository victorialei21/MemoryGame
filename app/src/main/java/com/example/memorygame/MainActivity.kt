package com.example.memorygame

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import java.util.*
import kotlin.random.Random

//Michelle Yun and Victoria Lei

class MainActivity : AppCompatActivity() {
    lateinit var startButton: Button
    lateinit var difficultyText: TextView
    lateinit var blue : ImageButton
    lateinit var green : ImageButton
    lateinit var pink : ImageButton
    lateinit var yellow : ImageButton
    var easyLevel = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.startButton)
        difficultyText = findViewById(R.id.difficultyText)
        blue = findViewById(R.id.blue)
        green = findViewById(R.id.green)
        pink = findViewById(R.id.pink)
        yellow = findViewById(R.id.yellow)
    }//onCreate

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.stats_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }//onCreateOptionsMenu

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        if (item.itemId == R.id.viewStats) {
            val intent = Intent(applicationContext, StatsActivity::class.java)
            startActivity(intent)
            return true
        } else if(item.itemId == R.id.difficulty){
            chooseDifficulty()
        }
        return false
    }// onOptionsItemSelected

    fun chooseDifficulty(){
        AlertDialog.Builder(this)
            .setTitle("Choose Difficulty Level")
            .setPositiveButton("Hard") { dialogInterface: DialogInterface, i: Int ->
                easyLevel = false
                difficultyText.text = "HARD"
            }
            .setNegativeButton("Easy"){ dialogInterface: DialogInterface, i: Int ->
                easyLevel = true
                difficultyText.text = "EASY"
            }
            .show()
    }//chooseDifficulty

    fun playGame(view : View){
        var numSquares = 4
        //player must memorize 8 squares if difficulty level is hard
        if(!easyLevel){
            numSquares = 8
        }

        val randomValues = List(numSquares) { Random.nextInt(0, 4) }
        println(randomValues)

        for(i in 0 until numSquares){
            val runnable = Runnable {
                val handler = Handler(Looper.getMainLooper())
                blue.setImageResource(R.drawable.bluedark)
                handler.postDelayed({
                    blue.setImageResource(R.drawable.blue) }, 2000)
            }
            val outerHandler = Handler(Looper.getMainLooper())
            outerHandler.postDelayed(runnable, 1000)
            println("i: $i")
//            when(randomValues[i]){
//                0 -> {
//                    blue.setImageResource(R.drawable.bluedark)
//                    handler.postDelayed({
//                        blue.setImageResource(R.drawable.blue) }, 1000)
//                    }
//                1 -> {
//                    green.setImageResource(R.drawable.greendark)
//                    handler.postDelayed({
//                        green.setImageResource(R.drawable.green) }, 1000)
//                    }
//                2 -> {
//                    pink.setImageResource(R.drawable.pinkdark)
//                    handler.postDelayed({
//                        pink.setImageResource(R.drawable.pink) }, 1000)
//                    }
//                3 -> {
//                    yellow.setImageResource(R.drawable.yellowdark)
//                    handler.postDelayed({
//                        yellow.setImageResource(R.drawable.yellow) }, 1000)
//                    }
//                }//when
        }//for loop

    }//playGame

    fun tileColor() {

    }

    class colorChange : TimerTask() {
        public
        override fun run() {
            val handler = Handler(Looper.getMainLooper())
        }

    }//colorChange

    fun evaluateClicks(){

    }

}//MainActivity