package com.example.knowyourleaders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.knowyourleaders.R

class LeadersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaders)

        val headingDetail : TextView = findViewById(R.id.heading)
        val mainDetail : TextView = findViewById(R.id.leaders)
        val imageLeaders :ImageView = findViewById(R.id.image_heading)

        val bundle: Bundle?= intent.extras
        val heading = bundle!!.getString("heading")
        val imageId = bundle.getInt("imageId", 0)
        val leaders = bundle.getString("leaders")

        headingDetail.text = heading
        mainDetail.text = leaders
        imageLeaders.setImageResource(imageId)
    }

}