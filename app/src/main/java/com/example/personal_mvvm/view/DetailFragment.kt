package com.example.personal_mvvm.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.personal_mvvm.R
import com.example.personal_mvvm.models.animal.Animal
import com.example.personal_mvvm.models.util.getProgressDrawable
import com.example.personal_mvvm.models.util.loadImage
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.items_animal.*
import kotlinx.android.synthetic.main.items_animal.iv_animal_image
import kotlinx.android.synthetic.main.items_animal.tv_animal_name

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    var animal: Animal? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //retreiving animal info
        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }

        //checking if image has context
        context?.let {
            iv_animal_image.loadImage(animal?.imageUrl, getProgressDrawable(it))
        }

        //populating the view
        tv_animal_name.text = animal?.name
        tv_animal_location.text = animal?.location
        tv_animal_lifeSpan.text = animal?.lifeSpan
        tv_animal_diet.text = animal?.diet

        animal?.imageUrl?.let {
            setupBackgroudColor(it)
        }

    }

    //function to set the background in regards to the image spectrum
    private fun setupBackgroudColor(url:String){
        //use Glide latest functionality april 2020
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>(){
                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    //using palette library
                    Palette.from(resource)
                        .generate(){palette: Palette? ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?:0 // if color is null
                            animal_layout.setBackgroundColor(intColor)
                    }
                }

            })
    }
}

