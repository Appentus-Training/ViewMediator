# ViewMediator


simple library to deliver events between the views , currently only click event is supported

<h1> Why </h1>

**To convice you for using this library or similar approach let me show you some code , that i wrote few months ago**

__first see the ui__

![](https://github.com/AbhinavChauhan97/ViewMediator/blob/master/Screenshot%20(70).png)
![](https://github.com/AbhinavChauhan97/ViewMediator/blob/master/Screenshot%20(71).png)

I hope you got  that user can select on laguage when he click on some laguage then some colors changes of selected langaue and also of other languges of they were selected previosly to show them unselected , now see the code to achive that , and it is the best i could do , i could be worse with little less care 

      rlEnglish.setOnClickListener {
            makeSelection(0,true)
        }
        rlFrench.setOnClickListener {
            makeSelection(1,true)
        }
        rlSpanish.setOnClickListener {
            makeSelection(2,true)
        }
        
        
        private fun makeSelection(index: Int,isClicked:Boolean) {
        when (index) {
            0 -> {
                rlEnglish.background = ContextCompat.getDrawable(this, R.drawable.btn_shape)
                rlFrench.background = ContextCompat.getDrawable(this, R.drawable.shape_uncheck)
                rlSpanish.background = ContextCompat.getDrawable(this, R.drawable.shape_uncheck)
                tvEnglish.setTextColor(ContextCompat.getColor(this, R.color.intro_white))
                tvFrench.setTextColor(ContextCompat.getColor(this, R.color.black))
                tvSpanish.setTextColor(ContextCompat.getColor(this, R.color.black))
                ivCheck_english.isVisible = true
                ivCheck_French.isVisible = false
                ivCheck_Spanish.isVisible = false
                language = tvEnglish.text.toString()
            }

            1 -> {
                rlEnglish.background = ContextCompat.getDrawable(this, R.drawable.shape_uncheck)
                rlFrench.background = ContextCompat.getDrawable(this, R.drawable.btn_shape)
                rlSpanish.background = ContextCompat.getDrawable(this, R.drawable.shape_uncheck)
                tvFrench.setTextColor(ContextCompat.getColor(this, R.color.intro_white))
                tvEnglish.setTextColor(ContextCompat.getColor(this, R.color.black))
                tvSpanish.setTextColor(ContextCompat.getColor(this, R.color.black))
                ivCheck_english.isVisible = false
                ivCheck_French.isVisible = true
                ivCheck_Spanish.isVisible = false
                language =  tvFrench.text.toString()
            }
            2 -> {
                rlEnglish.background = ContextCompat.getDrawable(this, R.drawable.shape_uncheck)
                rlFrench.background = ContextCompat.getDrawable(this, R.drawable.shape_uncheck)
                rlSpanish.background = ContextCompat.getDrawable(this, R.drawable.btn_shape)
                tvSpanish.setTextColor(ContextCompat.getColor(this, R.color.intro_white))
                tvEnglish.setTextColor(ContextCompat.getColor(this, R.color.black))
                tvFrench.setTextColor(ContextCompat.getColor(this, R.color.black))
                ivCheck_english.isVisible = false
                ivCheck_French.isVisible = false
                ivCheck_Spanish.isVisible = true
                language =  tvSpanish.text.toString()
            }

        }

    }
    
    
 **Does above code works?**  <i>YES</i> 
 **Then What is the problem ?**
 
 <i> Well there are many problems </i> 
 
 -It looks horrrible 
 
 
 -It is not closed for modificaion (If tomorrow we want to add another language this code is going to break , first of all another branch for `when(index)` will be introduced   in the `makeSelection()` method. And all exising braches  will have some more lines 
 
 
 -This kind of UI is very common when user make some selection , once or more in almost all apps so we shouldn't be doing this everwhere , we should find a better solution to apply everywhere
 
 <h3> Lesson </h3> 
 
  Clients love changes , in above code if we think we are ever going to have those there languages then it is silly because change is inevitable , it is very easy for clients to say now it don't want that but that , do this instead of that , put that thing in the app etc ,  as responsible developers it is also our responsibility to safeguard ourselves from these kind of situations , the only solution is write  [SOLID](https://en.wikipedia.org/wiki/SOLID) code , [This](https://github.com/AbhinavChauhan97/ValidationNotifierEditText) library also helps in writing  [SOLID](https://en.wikipedia.org/wiki/SOLID) code, check out
  
  __But now let us solve above problem for all and let me introduce__    _*View Mediator*_

<h1> Usage </h1>


    implementation 'com.github.AbhinavChauhan97:ViewMediator:1.0.0'

 <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <com.github.abhinavchauhan97.viewmediator.ViewMediator
        android:id="@+id/mediator"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:vm_reference_ids="textview1,textview2,textview3,textview4"/>

    <TextView
        android:id="@+id/textview1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="TextView1"
        android:textSize="30sp"
        android:layout_margin="20dp"/>

    <TextView
        android:id="@+id/textview2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="TextView2"
        android:textSize="30sp"
        android:layout_margin="20dp"/>

    <TextView
        android:id="@+id/textview3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="TextView3"
        android:textSize="30sp"
        android:layout_margin="20dp"/>

    <TextView
        android:id="@+id/textview4"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="TextView4"
        android:textSize="30sp"
        android:layout_margin="20dp"/>

</LinearLayout>



In above xml pay attention to 
      
        
    <com.github.abhinavchauhan97.viewmediator.ViewMediator
        android:id="@+id/mediator"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:vm_reference_ids="textview1,textview2,textview3,textview4"/>
        
 this is the key , the ViewMediator can reference any number of views from the same view group in which it is present and now whenever any of those view is clicked you get    that view and other views in a callback  , just do the following 
 
           val mediator = findViewById<ViewMediator>(R.id.mediator) // get reference to view mediator
        
        mediator.clicksMediator = object : ClicksMediator{  // and setup the callback 
            override fun doWithOtherViews(otherViews: List<View>) { // here you get all the views except the clicked/selected view
                otherViews.forEach { 
                    val textView = it as TextView   // since you know that there are only textviews in the layout you can safley type cast , if you have more types of views you can do an `is` check or check by `id` and then type cast
                    textView.setTextColor(Color.RED)
                }
            }
            
            override fun doWithClickedView(clickedView: View) { // here you get the clicked/selected
                    val textView = clickedView as TextView
                    textView.setTextColor(Color.GREEN)
            }
        }
    }
    
  ![](https://github.com/AbhinavChauhan97/ViewMediator/blob/master/ezgif.com-gif-maker%20(1).gif)
    
That above pattern greatly simplyfies the situatation where you have to select one option from many , normally you will setup a click listener on all of them and whenere a view is cliked you manually update that view and other views , the problem with that approach is that all views have to know about other views **Violation of** [Principle of Least Knowledge](https://en.wikipedia.org/wiki/Law_of_Demeter) you can not add or remove views in the layout wihtout breaking exsiting code, means the code is never closed for modification (a violation of [Open Closed Principle](https://en.wikipedia.org/wiki/Open%E2%80%93closed_principle) )  


**With ViewMediator views do not have to know about each other you can add/remove any number of views just in the layout and everything will work the same as it were before  and you don't have to touch existing code for that.**

 Just add the id of new view  `app:vm_reference_ids="textview1,textview2,textview3,textview4,newView1,newView2"`
 
 <h2> Default Selection </h2>
 
 add  `app:vm_default_reference_ids="textview2,textview3"` to select some view by default means when the group of refereced views appear on screen the views with the given    ids are selected by default appying the selected  unselected behaviour by given implementation of `ClicksMediator` 
 
     
     
 
 <h1> MultiSelect Support </h1> 
 
 In a group of views many times you don't just want to select only one , you may want to give option to select two , or there or any number , now you want to limit the selection for that number of views so that when user has selected that many options for example 2 you want don't want to let them select more they only can select any other option if they select an already selected option by click on it again ,The ViewMediator  also supports that
 
**just one more attribute for that**  `app:vm_canSelect="2"` by the default value is 1 for the value of one we do normal selection 


       
    <com.github.abhinavchauhan97.viewmediator.ViewMediator
        android:id="@+id/mediator"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:vm_canSelect="2"
        app:vm_reference_ids="textview1,textview2,textview3,textview4"/>
        
**now user can select two text views and can re-click selected textview to make it unselected and than can can select some other 

**You also get callbacks for this** 


         mediator.clicksMediator = object : ClicksMediator{  // and setup the callback
            override fun doWithOtherViews(otherViews: List<View>) { // here you get all the views except the clicked/selected view
                otherViews.forEach {
                    val textView = it as TextView
                    textView.setTextColor(Color.RED)
                }
            }

            override fun doWithClickedView(clickedView: View) { // here you get the clicked/selected
                    val textView = clickedView as TextView
                    textView.setTextColor(Color.GREEN)
            }

            override fun onLimitReached() { 
               Toast.makeText(this@MainActivity,"can not select more than 2 options",Toast.LENGTH_LONG).show()
                // when the limit is reached this callback is called , in this example we have app:vm_canSelect="2" so when user clicks third view this callback will be called may be you want to show a message saying you cannot select more that two options , you do it here
            }

            override fun onSelectedViewReClick(reClickedView: View) {
                val textView = reClickedView as TextView
                textView.setTextColor(Color.RED)         
                // this callback is called when already selected view is clicked again to make it unselected
                // here you can make it look like it a unselected view should look like
            }
        }
        
 ![](https://github.com/AbhinavChauhan97/ViewMediator/blob/master/ezgif.com-gif-maker.gif)  
 
 __Version 1.0.5__   ( 10-july-2021) 
 
 * programmatically add views to `ViewMediator` using  `addView(View)` and `addDefaultSelectedView(View)` methods
