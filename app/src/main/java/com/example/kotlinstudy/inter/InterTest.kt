package com.example.kotlinstudy.inter

class InterTest {
    fun interTest(){
        val inter = object :FloatViewLifecycleListener{
            override fun onBackToDesktop() {
                TODO("Not yet implemented")
            }

            override fun onShow() {
                TODO("Not yet implemented")
            }

            override fun onHide() {
                TODO("Not yet implemented")
            }

            override fun onDestroy() {
                TODO("Not yet implemented")
            }
        }
    }
}