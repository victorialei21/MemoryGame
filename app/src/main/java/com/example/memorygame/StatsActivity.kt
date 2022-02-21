package com.example.memorygame

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

class StatsActivity : AppCompatActivity() {
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        listView = findViewById(R.id.statsList)
        putData()
    }//onCreate

    fun putData(){
        sharedPreferences = applicationContext.getSharedPreferences(
            "com.example.memorygame", Context.MODE_PRIVATE)

        var easyWon: Int = sharedPreferences.getInt("easyWon", 0)
        var easyTotal: Int = sharedPreferences.getInt("easyTotal", 0)
        var hardWon: Int = sharedPreferences.getInt("hardWon", 0)
        var hardTotal: Int = sharedPreferences.getInt("hardTotal", 0)

        // calculate win rates
        val easyRate = ((easyWon.toDouble()/easyTotal.toDouble())*100).toInt()
        val hardRate = ((hardWon.toDouble()/hardTotal.toDouble())*100).toInt()

        // display results
        gameStatList.clear()
        gameStatList.add(GameStat("Easy", easyRate))
        gameStatList.add(GameStat("Hard", hardRate))

        statsAdapter = StatsAdapter(this, gameStatList)
        listView.adapter = statsAdapter

    } // putData

    fun returnToMain(view : View) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

}//StatsActivity

class GameStat(diff: String, winP: Int){
    var difficulty = diff
    var winPercent = winP
}//GameStat

class StatsAdapter(private val context: Context, private val arrayList: java.util.ArrayList<GameStat>) : BaseAdapter() {
    private lateinit var diffLevel : TextView
    private lateinit var winPercent : TextView

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var convertView = p1
        convertView = LayoutInflater.from(context).inflate(R.layout.stat_item, p2, false)

        diffLevel = convertView.findViewById(R.id.diffLevel)
        winPercent = convertView.findViewById(R.id.winPercent)

        diffLevel.text = arrayList[p0].difficulty
        winPercent.text = arrayList[p0].winPercent.toString() + "%"

        return convertView
    }//getView

}//StatsAdapter