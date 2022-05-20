package ru.donspb.simplecar

import kotlin.math.abs
import kotlin.random.Random

const val MIN_POINTS_NUMBER = 2
const val MAX_POINTS_NUMBER = 7
const val MOVE_SPEED_MS_PIXEL = 10L
const val ROTATION_SPEED_MS_90 = 1500L
const val TOP_ROTATION_ANGLE = 0
const val BOTTOM_ROTATION_ANGLE = 180
const val RIGHT_ROTATION_ANGLE = 90
const val LEFT_ROTATION_ANGLE = 270
const val MOVE_ROTATION = "rotation"
const val MOVE_TRANS_X = "translationX"
const val MOVE_TRANS_Y = "translationY"


class Presenter(private val mainView: IMainView) {
    private var maxX: Int = 0
    private var maxY: Int = 0
    private var carX: Int = 0
    private var carY: Int = 0
    private var carRotation = TOP_ROTATION_ANGLE

    private val carsList = arrayListOf(
        CarModel("SUV", 150, 300, R.drawable.suv),
        CarModel("Sport", 120, 240, R.drawable.sport),
        CarModel("Roadster", 100, 200, R.drawable.roadster)
    )

    private var selectedCar = carsList[Random.nextInt(0, carsList.size - 1)]

    fun setMaxCoords(width: Int, height: Int) {
        maxX = width
        maxY = height
    }

    fun setTouchPoint(x: Int, y: Int) {
        val halfCarX = selectedCar.width / 2
        val halfCarY = selectedCar.height / 2
        if (x > halfCarX && x < maxX - halfCarX && y > halfCarY && y < maxY - halfCarY ) {
            carX = x
            carY = y
            mainView.showCar(x - halfCarX, y - halfCarY, selectedCar)
        }
    }

    fun startRide() {
        val lastPoint = Random.nextInt(MIN_POINTS_NUMBER, MAX_POINTS_NUMBER)

        for (i in 0..lastPoint) {
            var newX: Int
            var newY: Int
            if (i == lastPoint) {
                newX = maxX
                newY = 0
            }
            else {
                newX = Random.nextInt(maxX - (selectedCar.width / 2))
                newY = Random.nextInt(maxY - (selectedCar.height / 2))
            }
            val firstDirection = Random.nextBoolean()           // false - x direction, true - y direction
            moveCar(firstDirection, newX, newY)
            moveCar(!firstDirection, newX, newY)
        }
        mainView.startAnimationSequence()
    }

    private fun moveCar(direction: Boolean, newX: Int, newY: Int) {
        val oldAngle = carRotation
        var distance: Int
        if (direction) {
            distance = (newY - carY)
            if (distance < 0) carRotation = TOP_ROTATION_ANGLE
            else if (distance > 0) carRotation = BOTTOM_ROTATION_ANGLE
            mainView.makeAnimation(ROTATION_SPEED_MS_90, (carRotation - oldAngle).toFloat(), MOVE_ROTATION)
            mainView.makeAnimation(abs(MOVE_SPEED_MS_PIXEL * distance),
                distance.toFloat(),
                MOVE_TRANS_Y)
            carY = newY
        }
        else {
            distance = (newX - carX)
            if (distance > 0) carRotation = RIGHT_ROTATION_ANGLE
            else if (distance < 0) carRotation = LEFT_ROTATION_ANGLE
            mainView.makeAnimation(ROTATION_SPEED_MS_90, (carRotation - oldAngle).toFloat(), MOVE_ROTATION)
            mainView.makeAnimation(abs(MOVE_SPEED_MS_PIXEL * distance),
                distance.toFloat(),
                MOVE_TRANS_X)
            carX = newX
        }
    }
}