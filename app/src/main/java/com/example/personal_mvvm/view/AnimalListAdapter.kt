package com.example.personal_mvvm.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.personal_mvvm.R
import com.example.personal_mvvm.models.animal.Animal
import com.example.personal_mvvm.models.util.getProgressDrawable
import com.example.personal_mvvm.models.util.loadImage
import kotlinx.android.synthetic.main.items_animal.view.*

class AnimalListAdapter (private val animalList:ArrayList<Animal>): RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(){
    class AnimalViewHolder(var view: View):RecyclerView.ViewHolder(view)


    fun updateAnimalList(newAnimalList:List<Animal>){
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.items_animal,parent,false)
        return  AnimalViewHolder(view)
    }

    override fun getItemCount()= animalList.size


    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.tv_animal_name.text = animalList[position].name
        //image
        holder.view.iv_animal_image.loadImage(animalList[position].imageUrl,
            getProgressDrawable(holder.view.context))

        //parcelables
        holder.view.item_animal_layout.setOnClickListener {
            val action = ListFragmentDirections.actionGoToDetail(animalList[position])

            Navigation.findNavController(holder.view).navigate(action)
        }
    }
}