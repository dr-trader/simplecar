package ru.donspb.simplecar

interface IMainView {
    fun showCar(x: Int, y: Int, car: CarModel)
    fun makeAnimation(speed: Long, dimen: Float, type: String)
    fun startAnimationSequence()
}