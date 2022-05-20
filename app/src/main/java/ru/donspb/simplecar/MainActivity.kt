package ru.donspb.simplecar

import android.animation.Animator
import android.animation.AnimatorSet
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

const val CAR_NOT_PLACED = 0
const val CAR_PLACED = 1
const val CAR_IN_PROGRESS = 2
const val CAR_FINISHED = 3

class MainActivity : AppCompatActivity(), IMainView {

    private val presenter: Presenter = Presenter(this)
    private lateinit var binding: ActivityMainBinding
    private lateinit var layout: RelativeLayout
    private var isCarPlaced = 0
    private var carImageView: ImageView? = null
    private val animationsList: MutableList<ObjectAnimator> = mutableListOf()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layout = binding.loRelative
        setMaxCoords()

        layout.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                when (isCarPlaced) {
                    CAR_NOT_PLACED, CAR_FINISHED -> {
                        presenter.setTouchPoint(motionEvent.x.toInt(), motionEvent.y.toInt())
                        isCarPlaced = CAR_PLACED
                    }
                    CAR_PLACED -> {
                        presenter.startRide()
                        isCarPlaced = CAR_IN_PROGRESS
                    }
                    else -> {
                        //TODO: Car in progress, pls wait
                    }
                }
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

    override fun makeAnimation(speed: Long, dimen: Float, type: String) {
        val animator = ObjectAnimator.ofFloat(carImageView, type, dimen)
        animator.duration = speed
//        animator.start()
        animationsList.add(animator)
    }

    override fun startAnimationSequence() {
        val animSet = AnimatorSet()
        animSet.playSequentially(animationsList as List<Animator>?)
        animSet.start()
        isCarPlaced = CAR_FINISHED
    }
}