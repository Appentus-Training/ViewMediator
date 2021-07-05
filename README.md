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
 
