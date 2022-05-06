package ru.donspb.simplecar

interface IMainView {
    fun showCar(x: Int, y: Int, car: CarModel)
    fun rotateCar(angle: Float, speed: Long)
    fun moveCarHorizontally(speed: Long, toX: Float)
    fun moveCarVertically(speed: Long, toY: Float)
}