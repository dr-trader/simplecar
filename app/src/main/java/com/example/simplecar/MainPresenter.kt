package com.example.simplecar

class MainPresenter(val viewMain: IMainView) {

    fun start() {
        viewMain.setSize()
    }
}