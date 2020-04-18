package com.example.personal_mvvm.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.personal_mvvm.R
import com.example.personal_mvvm.databinding.FragmentDetailBinding
import com.example.personal_mvvm.models.animal.Animal
import com.example.personal_mvvm.models.animal.AnimalPalette
import com.example.personal_mvvm.models.util.getProgressDrawable
import com.example.personal_mvvm.models.util.loadImage
import com.example.personal_mvvm.view.DetailFragmentDirections.actionGoToList

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    var animal: Animal? = null
    private lateinit var dataBinding: FragmentDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //dataBinding -Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //retreiving animal info
        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }


        animal?.imageUrl?.let {
            setupBackgroudColor(it)
        }

        //binding info from layout data name
        dataBinding.animal = animal
    }

    //function to set the background in regards to the image spectrum
    private fun setupBackgroudColor(url: String) {
        //use Glide latest functionality april 2020
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    //using palette library
                    Palette.from(resource)
                        .generate() { palette: Palette? ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0 // if color is null
                            //created a new data class in model + variable in xml
                            dataBinding.palette = AnimalPalette(intColor)
                        }
                }

            })
    }
}

