package com.example.changeofclroftextview
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private var contentView by Delegates.notNull<Int>()
    private var colorIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val colorTextView = findViewById<TextView?>(R.id.colorTextView)
        val changeColorButton = findViewById<Button?>(R.id.changeColorButton)
        val colors = intArrayOf(
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.DKGRAY,
            Color.CYAN,
            Color.MAGENTA
        )
        changeColorButton?.setOnClickListener {
            colorTextView?.setBackgroundColor(colors[colorIndex])
            colorIndex = (colorIndex + 1) % colors.size
        }
    }
}