package com.example.memorygame

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.random.Random

// Michelle Yun and Victoria Lei

var statsAdapter: StatsAdapter? = null
var difficulties = ArrayList<String>()
var gameStatList = ArrayList<GameStat>()
lateinit var sharedPreferences: SharedPreferences

class MainActivity : AppCompatActivity() {
    lateinit var startButton: Button
    lateinit var difficultyText: TextView
    lateinit var blue : ImageButton
    lateinit var green : ImageButton
    lateinit var pink : ImageButton
    lateinit var yellow : ImageButton
    lateinit var menuBar : Menu
    lateinit var randomOrder: List<Int>
    lateinit var userAnswers: ArrayList<Int>
    var easyLevel = true
    var buttonClicks = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.startButton)
        difficultyText = findViewById(R.id.difficultyText)
        blue = findViewById(R.id.blue)
        blue.setEnabled(false)
        green = findViewById(R.id.green)
        green.setEnabled(false)
        pink = findViewById(R.id.pink)
        pink.setEnabled(false)
        yellow = findViewById(R.id.yellow)
        yellow.setEnabled(false)

        difficulties.add("Easy")
        difficulties.add("Hard")

    }//onCreate

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.stats_menu, menu)
        if (menu != null) {
            menuBar = menu
        }
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

    fun playGame(view : View) {
        var numSquares = 4
        if (!easyLevel) numSquares = 8

        randomOrder = List(numSquares) { Random.nextInt(0, 4) }
        userAnswers = ArrayList(numSquares)
        //Toast.makeText(applicationContext, randomOrder.toString(), Toast.LENGTH_SHORT).show()

        // temporarily remove interactive elements
        view.setVisibility(View.GONE)
        menuBar.setGroupEnabled(0, false)

        // display flashing buttons
        var delayMillis : Long = 1000
        val handler = Handler(Looper.getMainLooper())
        for (i in 0 until numSquares) {
            when(randomOrder[i]){
                0 -> {
                    handler.postDelayed({
                        blue.setImageResource(R.drawable.bluedark) }, delayMillis)
                    delayMillis += 1000
                    handler.postDelayed({
                        blue.setImageResource(R.drawable.blue) }, delayMillis)
                }
                1 -> {
                    handler.postDelayed({
                        green.setImageResource(R.drawable.greendark) }, delayMillis)
                    delayMillis += 1000
                    handler.postDelayed({
                        green.setImageResource(R.drawable.green) }, delayMillis)
                }
                2 -> {
                    handler.postDelayed({
                        pink.setImageResource(R.drawable.pinkdark) }, delayMillis)
                    delayMillis += 1000
                    handler.postDelayed({
                        pink.setImageResource(R.drawable.pink) }, delayMillis)
                }
                3 -> {
                    handler.postDelayed({
                        yellow.setImageResource(R.drawable.yellowdark) }, delayMillis)
                    delayMillis += 1000
                    handler.postDelayed({
                        yellow.setImageResource(R.drawable.yellow) }, delayMillis)
                }
            }// when
            delayMillis += 1000
        } // for

        // enable user buttons
        blue.setEnabled(true)
        green.setEnabled(true)
        pink.setEnabled(true)
        yellow.setEnabled(true)

    } // playGame

    fun addClick(view: View) {
        val tag : Int = view.tag.toString().toInt()
        userAnswers.add(tag)

        // evaluate results
        var size: Int = 0
        if (easyLevel) size = 4
        else size = 8
        if (userAnswers.size==size) {
            evaluateClicks()
        }

    } // addClick

    fun evaluateClicks(){
        var won : Boolean = false

        for (i in 0 until randomOrder.count()) {
            if (randomOrder.get(i)!=userAnswers.get(i)) {
                won = false
                break
            }
            won = true
        }
        addStat(won, easyLevel)
        userAnswers = ArrayList<Int>()

        // toast results to user
        var result = ""
        if (won) result = "Correct!"
        else result = "Incorrect!"
        Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()

        // bring back interactive elements
        startButton.text = "Play Again"
        startButton.setVisibility(View.VISIBLE)
        menuBar.setGroupEnabled(0, true)

        // disable user buttons
        blue.setEnabled(false)
        green.setEnabled(false)
        pink.setEnabled(false)
        yellow.setEnabled(false)

    } // evaluateClicks

    fun addStat(won : Boolean, easy : Boolean){
        sharedPreferences = applicationContext.getSharedPreferences(
            "com.example.memorygame", Context.MODE_PRIVATE)

        if (easy){
            var easyWon: Int = sharedPreferences.getInt("easyWon", 0)
            var easyTotal: Int = sharedPreferences.getInt("easyTotal", 0)

            easyTotal++
            if (won) {
                easyWon++
            }

            sharedPreferences.edit().putInt("easyWon", easyWon).apply()
            sharedPreferences.edit().putInt("easyTotal", easyTotal).apply()
        } else {
            var hardWon: Int = sharedPreferences.getInt("hardWon", 0)
            var hardTotal: Int = sharedPreferences.getInt("hardTotal", 0)

            hardTotal++
            if (won) {
                hardWon++
            }

            sharedPreferences.edit().putInt("hardWon", hardWon).apply()
            sharedPreferences.edit().putInt("hardTotal", hardTotal).apply()
        }//if else
    }//addStat

}//MainActivity