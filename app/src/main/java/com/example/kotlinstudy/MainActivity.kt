package com.example.kotlinstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kotlinstudy.view.ClickSpanTextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("helloWorld")

        text_hello_world.setOnClickListener {
            if (text_hello_world.mClickHandled){
                // 若已处理则直接返回
                text_hello_world.mClickHandled = false;
                return@setOnClickListener
            }
            Toast.makeText(MainActivity@ this, "你点击了TextView", Toast.LENGTH_SHORT).show()
        }

        text_hello_world.setTextClickListener(object : ClickSpanTextView.TextClickListener {
            override fun onTextClick() {
                text_hello_world.mClickHandled = true
                Toast.makeText(this@MainActivity, "hello world", Toast.LENGTH_SHORT).show()
            }
        })
    }
}