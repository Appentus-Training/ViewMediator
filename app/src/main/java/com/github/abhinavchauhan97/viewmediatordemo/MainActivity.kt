package com.github.abhinavchauhan97.viewmediatordemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.github.abhinavchauhan97.viewmediator.ClicksMediator
import com.github.abhinavchauhan97.viewmediator.ViewMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mediator = findViewById<ViewMediator>(R.id.mediator)
        mediator.clicksMediator = object : ClicksMediator{
            override fun doWithOtherViews(otherViews: List<View>) {
                otherViews.forEach {
                    val textView = it as TextView
                    textView.setTextColor(Color.RED)
                }
            }

            override fun doWithClickedView(clickedView: View) {
                    val textView = clickedView as TextView
                    textView.setTextColor(Color.GREEN)
            }

        }
    }
}