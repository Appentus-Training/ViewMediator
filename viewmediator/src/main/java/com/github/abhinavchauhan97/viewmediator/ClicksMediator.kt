package com.github.abhinavchauhan97.viewmediator

import android.view.View

interface ClicksMediator {
    fun doWithOtherViews(otherViews: List<View>)
    fun doWithClickedView(clickedView:View)
}