# ViewMediator
simple library to deliver events between the views , currently only click event is supported

<h1> Usage </h1>

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
    
That above pattern greatly simplyfies the situatation where you have to select one option from many , normally you will setup a click listener on all of them and whenere a view is cliked you manually update that view and other views , the problem with that approach is that all views have to know about other views you can not add or remove views in the layout wihtout breaking exsiting code, means the code is never closed for modification (a violation of [SOLID](https://en.wikipedia.org/wiki/SOLID) )  


**With ViewMediator views do not have to know about each other you can add/remove any number of views just in the layout and everything will work the same as if were before  and you don't have to touch existing code for that.**

 Just add the id of new view  `app:vm_reference_ids="textview1,textview2,textview3,textview4,newView1,newView2"`
 
 
 <h1> MultiSelect Support </h1> 
 
 In a group of views many times you don't just want to select only one , you may want to give option to select two , or there or any number , now you want to limit the selection for that number of views so that when your has selected that many options for example 2 you want don't want to let them select more they only can select any other option if the de-select an already selected option by click on it again , fortunately ViewMediator  also supports this
 
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
