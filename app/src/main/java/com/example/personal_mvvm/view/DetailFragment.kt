package com.example.personal_mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        tv_animal_name.text = animal?.name
        tv_animal_location.text = animal?.location
        tv_animal_lifeSpan.text = animal?.lifeSpan
        tv_animal_diet.text = animal?.diet
    }
}

