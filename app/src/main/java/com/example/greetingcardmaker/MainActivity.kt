package com.example.greetingcardmaker

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.io.*
import android.graphics.Color
import android.widget.ImageView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var main: View
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val birthday = Birthday()
        val anniversary = Anniversary()
        val wedding = Wedding()
        val prev: Button = findViewById(R.id.prevBtn)
        val next: Button = findViewById(R.id.nextBtn)
        val share: Button = findViewById(R.id.shareBtn)
        imageView = findViewById(R.id.imageView)
        main = findViewById(R.id.fragment)

        val fragments = listOf<Fragment>(birthday, anniversary, wedding)
        var counter = 0;

        share.setOnClickListener {
            val b: Bitmap = Screenshot.takeScreenshotOfRootView(imageView)
            imageView.setImageBitmap(b)
            main.setBackgroundColor(Color.parseColor("#999999"))
        }



        prev.setOnClickListener() {

            supportFragmentManager.beginTransaction().apply {

                if (counter == 0)
                    Toast.makeText(applicationContext, "No more cards", Toast.LENGTH_LONG).show()
                else
                    counter--;

                replace(R.id.fragment, fragments[counter])
                commit()
            }
        }
        next.setOnClickListener() {

            supportFragmentManager.beginTransaction().apply {

                if (counter == 2)
                    Toast.makeText(applicationContext, "No more cards", Toast.LENGTH_LONG).show()
                else
                    counter++;

                replace(R.id.fragment, fragments[counter])
                commit()
            }
        }
    }
    companion object Screenshot {
        private fun takeScreenshot(view: View): Bitmap {
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache(true)
            val b = Bitmap.createBitmap(view.drawingCache)
            view.isDrawingCacheEnabled = false
            return b
        }
        fun takeScreenshotOfRootView(v: View): Bitmap {
            return takeScreenshot(v.rootView)
        }
    }
}