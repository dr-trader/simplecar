package ru.donspb.simplecar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.donspb.simplecar.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnTouchListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var layout: ViewGroup
    private lateinit var imageView: ImageView
    private var xCoord: Int? = null
    private var yCoord: Int? = null
    private var carsList: List<CarModel> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carsList = listOf(
            CarModel("SUV", 150, 300, AppCompatResources.getDrawable(this,
                R.drawable.suv)),
            CarModel("Sport", 120, 240, AppCompatResources.getDrawable(this,
                R.drawable.sport)),
            CarModel("Roadster", 100, 200, AppCompatResources.getDrawable(this,
                R.drawable.roadster))
        )

        layout = binding.loRelative
        imageView = binding.ivModel

        imageView.maxWidth = 100

        val layoutWidth = layout.width
        val layoutHeight = layout.height

        val animation = TranslateAnimation(0f, layoutWidth.toFloat(), 0f, layoutHeight.toFloat())
        animation.duration = 1000
        animation.fillAfter = true
        imageView.layoutParams = RelativeLayout.LayoutParams(100, 200)
        imageView.startAnimation(animation)


//        imageView.setOnTouchListener(this)

//        carChooser()
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        val x: Int? = event?.getRawX()?.toInt()
        val y: Int? = event?.getRawY()?.toInt()

        when(event?.action?.and(MotionEvent.ACTION_MASK)) {
            MotionEvent.ACTION_DOWN -> {
                val lparams = view?.layoutParams as RelativeLayout.LayoutParams
                if ((x != null) && (y != null)) {
                    xCoord = x - lparams.leftMargin
                    yCoord = y - lparams.topMargin
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val lparams = view?.layoutParams as RelativeLayout.LayoutParams
                if ((x != null) && y != null) {
                    lparams.leftMargin = x - xCoord!!
                    lparams.topMargin = y - yCoord!!
                    lparams.rightMargin = -250
                    lparams.bottomMargin = -250
                    view.layoutParams = lparams
                }
            }
            else -> { }
        }
        return true
    }

//    fun carChooser() {
//        val rg = RadioGroup(this)
//        rg.orientation = RadioGroup.HORIZONTAL
//        for (i in carsList.indices) {
//            val rb = RadioButton(this)
//            rb.id = View.generateViewId()
//            rb.setCompoundDrawablesRelative(null, carsList[i].modelImg, null, null)
//            rg.addView(rb)
//        }
//
//        MaterialAlertDialogBuilder(this)
//            .setTitle("Choose your car")
//            .setPositiveButton("Ok") { dialog, wich -> }
//            .setView(rg)
//            .show()
//    }

//    fun wayRandomizer() {
//        val displayMetrics = display.getMetrics()
//        val pointsNum = Random.nextInt(0,9)
//        for (i in 0..pointsNum) {
//
//        }
//    }
}