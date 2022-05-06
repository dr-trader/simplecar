package ru.donspb.simplecar

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.content.res.AppCompatResources
import ru.donspb.simplecar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), IMainView {

    private val presenter: Presenter = Presenter(this)
    private lateinit var binding: ActivityMainBinding
    private lateinit var layout: RelativeLayout
    private var isCarPlaced = false
    private var carImageView: ImageView? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layout = binding.loRelative
        setMaxCoords()

        layout.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                if (!isCarPlaced) {
                    presenter.setTouchPoint(motionEvent.x.toInt(), motionEvent.y.toInt())
                    isCarPlaced = true
                }
                else presenter.startRide()
            }
            true
        }
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

    private fun setMaxCoords() {
        val metrics = this.resources.displayMetrics
        presenter.setMaxCoords(metrics.widthPixels, metrics.heightPixels)
    }

    override fun showCar(x: Int, y: Int, car: CarModel) {
        val layoutParams = RelativeLayout.LayoutParams(car.width, car.height)
        layoutParams.setMargins(x,y,0,0)
        carImageView = ImageView(this)
        carImageView?.layoutParams = layoutParams
        carImageView?.setImageDrawable(car.modelImg?.let {
            AppCompatResources.getDrawable(this, it)
        })
        (layout as ViewGroup).addView(carImageView)
    }

    override fun rotateCar(angle: Float, speed: Long) {
        ObjectAnimator.ofFloat(carImageView!!, "rotation", angle).apply {
            duration = speed
            start()
        }
    }

    override fun moveCarHorizontally(speed: Long, toX: Float) {
        ObjectAnimator.ofFloat(carImageView!!, "translationX", toX).apply {
            duration = speed
            start()
        }
    }

    override fun moveCarVertically(speed: Long, toY: Float) {
        ObjectAnimator.ofFloat(carImageView!!, "translationY", toY).apply {
            duration = speed
            start()
        }
    }
}