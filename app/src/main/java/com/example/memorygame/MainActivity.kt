package com.example.memorygame

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
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

var statsAdapter: StatsAdapter? = null
var difficulties = ArrayList<String>()
var easyWon : Int = 0
var easyTotal : Int = 0
var hardWon : Int = 0
var hardTotal : Int = 0
var winPercents = ArrayList<String>()
var gameStatList = ArrayList<GameStat>()
lateinit var sharedPreferences: SharedPreferences

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

        difficulties.add("Easy")
        difficulties.add("Hard")

        winPercents.add("0")
        winPercents.add("0")
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
//            val runnable = Runnable {
//                val handler = Handler(Looper.getMainLooper())
//                blue.setImageResource(R.drawable.bluedark)
//                handler.postDelayed({
//                    blue.setImageResource(R.drawable.blue) }, 2000)
//            }
//            val outerHandler = Handler(Looper.getMainLooper())
//            outerHandler.postDelayed(runnable, 1000)
//            println("i: $i")
            when(randomValues[i]){
                0 -> {
                    val handler = Handler(Looper.getMainLooper())
                    blue.setImageResource(R.drawable.bluedark)
                    handler.postDelayed({
                        blue.setImageResource(R.drawable.blue) }, 1000)
                    }
                1 -> {
                    val handler = Handler(Looper.getMainLooper())
                    green.setImageResource(R.drawable.greendark)
                    handler.postDelayed({
                        green.setImageResource(R.drawable.green) }, 1000)
                    }
                2 -> {
                    val handler = Handler(Looper.getMainLooper())
                    pink.setImageResource(R.drawable.pinkdark)
                    handler.postDelayed({
                        pink.setImageResource(R.drawable.pink) }, 1000)
                    }
                3 -> {
                    val handler = Handler(Looper.getMainLooper())
                    yellow.setImageResource(R.drawable.yellowdark)
                    handler.postDelayed({
                        yellow.setImageResource(R.drawable.yellow) }, 1000)
                    }
                }//when
        }//for loop
        evaluateClicks()
    }//playGame

//    class colorChange : TimerTask() {
//        public
//        override fun run() {
//            val handler = Handler(Looper.getMainLooper())
//        }
//
//    }//colorChange

    fun evaluateClicks(){
        var won = false
        //logic for evaluating if user won the round
        addStat(won, easyLevel)
    }

    fun addStat(won : Boolean, easy : Boolean){
        if(easy){
            easyTotal++
            if(won) {
                easyWon++
            }
            winPercents[0] = ((easyWon.toDouble()/easyTotal.toDouble())*100).toInt().toString()
        } else {
            hardTotal++
            if(won) {
                hardWon++
            }
            winPercents[1] = ((hardWon.toDouble()/hardTotal.toDouble())*100).toInt().toString()
        }//if else

        sharedPreferences = applicationContext.getSharedPreferences(
            "com.example.memorygame", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(
            "difficulties", ObjectSerializer.serialize(difficulties))
            .apply()
        sharedPreferences.edit().putInt("easyWon", easyWon).apply()
        sharedPreferences.edit().putInt("easyTotal", easyTotal).apply()
        sharedPreferences.edit().putInt("hardWon", hardWon).apply()
        sharedPreferences.edit().putInt("hardTotal", hardTotal).apply()
        sharedPreferences.edit().putString(
            "winPercents", ObjectSerializer.serialize(winPercents))
            .apply()
    }//addStat

}//MainActivity