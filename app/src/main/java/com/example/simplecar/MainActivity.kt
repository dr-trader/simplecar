package com.example.simplecar

import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.simplecar.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), IMainView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var layout: RelativeLayout
    private lateinit var imageView: ImageView
    private val presenter: MainPresenter = MainPresenter(this)
    private var widthScreen: Int? = null
    private var heightScreen: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        layout = binding.mainLayout
        imageView = binding.movingImageview

        imageView.setImageResource(R.drawable.roadster)

//        val vto: ViewTreeObserver = layout.viewTreeObserver
//        vto.addOnPreDrawListener(object: ViewTreeObserver.OnPreDrawListener {
//            override fun onPreDraw(): Boolean {
//                layout.viewTreeObserver.removeOnPreDrawListener(this)
//                widthScreen = layout.width
//                heightScreen = layout.height
//                return true
//            }
//        })

        presenter.start()

        imageView.layoutParams = RelativeLayout.LayoutParams(100,200)
        imageView.x = 0f
        imageView.y = heightScreen?.toFloat() ?: 0f
    }

    override fun setSize() {
        widthScreen = layout.width
        heightScreen = layout.height
    }

    fun onStartAnimation() {

    }
}