# ViewMediator
simple library to deliver events between the views , currently only click event is supported

# Usage 

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


