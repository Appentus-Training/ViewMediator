package com.github.abhinavchauhan97.viewmediator


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.collection.CircularArray
import java.lang.IllegalStateException
import java.util.*

class ViewMediator(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private lateinit var stringIds: List<String>
    private val referencedViews = LinkedList<View>()
    private val clickedViews: Array<View?>
    var clicksMediator: ClicksMediator? = null
    var canSelect = 1

    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.ViewMediator, 0, 0)
        canSelect = ta.getInt(R.styleable.ViewMediator_vm_canSelect, 1)
        clickedViews = Array(canSelect) { null }
        val allIdsString = ta.getString(R.styleable.ViewMediator_vm_reference_ids)
        if (allIdsString != null) {
            extractStringIds(allIdsString)
        }
        ta.recycle()
    }

    private fun extractStringIds(allIdsString: String) {
        stringIds = allIdsString.split(",")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        extractReferencedViews()
        setupViews()
    }

    private fun extractReferencedViews() {
        stringIds.forEach {

            val id = resources.getIdentifier(it, "id", context.packageName)
            if (id == 0) {
                throw IllegalArgumentException("there is not view with id $it")
            }
            if (id == this.id) {
                throw  IllegalStateException("ViewMediator can not have reference id of its own")
            }
            val view = findViewInParent(id)
            referencedViews.add(view)
        }
    }

    private fun findViewInParent(id: Int) = (parent as ViewGroup).findViewById<View>(id)

    @SuppressLint("ClickableViewAccessibility")
    private fun setupViews() {
        referencedViews.forEach {
            it.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) { // detect the click event
                    if (fromClickedViews(it)) { // if this view was selected and now re clicked
                        clicksMediator?.onSelectedViewReClick(it)
                        removeFromClickedViews(it)
                        return@setOnTouchListener false
                    }
                    storeInClickedViews(it)
                    performActionOnClickedViews()
                    performActionOnOtherViews()
                    return@setOnTouchListener false
                }
                return@setOnTouchListener true
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(0, 0) // this view always have zero size
    }

    override fun onDraw(canvas: Canvas?) {
        // optimize drawing by not calling super since this view has no size
    }

    private fun storeInClickedViews(view: View) {
        if (canSelect == 1) {
            clickedViews[0] = view
            return
        }
        var limitReached = true
        for (i in clickedViews.indices) {
            if (clickedViews[i] == null) {
                clickedViews[i] = view
                limitReached = false
                break
            }
        }
        if (limitReached) {
            clicksMediator?.onLimitReached()
        }
    }

    private fun performActionOnClickedViews() {
        clickedViews.forEach { clickedView ->
            if (clickedView != null) {
                clicksMediator?.doWithClickedView(clickedView) // do what should be done with clicked views
            }
        }
    }

    private fun performActionOnOtherViews() {
        val otherViews = notSelectedViews()
        otherViews.forEach { _ -> clicksMediator?.doWithOtherViews(otherViews) } // do what should be done with other views
    }

    private fun fromClickedViews(view: View) = clickedViews.contains(view)

    private fun removeFromClickedViews(view: View) {
        for(i in clickedViews.indices){
            if (clickedViews[i] == view) {
                clickedViews[i] = null
            }
        }
    }

    private fun notSelectedViews() = referencedViews.filterNot { view -> fromClickedViews(view) }

}