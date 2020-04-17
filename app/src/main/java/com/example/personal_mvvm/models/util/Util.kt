package com.example.personal_mvvm.models.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.personal_mvvm.R


//spinner for image
fun getProgressDrawable(context: Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}
//IMAGE WITH GLIDE (made on a bg Thread)
fun ImageView.loadImage(uri:String?,progressDrawable: CircularProgressDrawable){
    //defining options variable
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round) // if error loading image -> default image
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

//databinding loading image
@BindingAdapter("android:imageUrl")
fun loadImage (view:ImageView,url:String?){
    view.loadImage(url, getProgressDrawable(view.context))

}

