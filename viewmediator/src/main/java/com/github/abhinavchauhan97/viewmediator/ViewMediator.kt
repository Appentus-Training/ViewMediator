package com.github.abhinavchauhan97.viewmediator


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import java.lang.IllegalStateException
import java.util.*

class ViewMediator(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private lateinit var stringIds: List<String>
    private val referencedViews = LinkedList<View>()
    var clicksMediator: ClicksMediator? = null

    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.ViewMediator, 0, 0)
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
            if(id == 0) {
                throw IllegalArgumentException("there is not view with id $it")
            }
            if(id == this.id){
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
                    clicksMediator?.doWithClickedView(it) // do what should be done with clicked view
                    val otherViews = referencedViews.filterNot { view -> view == it } // get other views
                    otherViews.forEach { _ -> clicksMediator?.doWithOtherViews(otherViews) } // do what should be done with other views
                    return@setOnTouchListener false
                }
                return@setOnTouchListener true
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(0,0) // this view always have zero size
    }

    override fun onDraw(canvas: Canvas?) {
        // optimize drawing by not calling super since this view has no size
    }

}