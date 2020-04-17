package com.example.personal_mvvm.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.personal_mvvm.R
import com.example.personal_mvvm.databinding.ItemsAnimalBinding
import com.example.personal_mvvm.models.animal.Animal
import com.example.personal_mvvm.models.util.getProgressDrawable
import com.example.personal_mvvm.models.util.loadImage
import kotlinx.android.synthetic.main.items_animal.view.*

class AnimalListAdapter (private val animalList:ArrayList<Animal>): RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(),AnimalClickListener{
    class AnimalViewHolder(var view: ItemsAnimalBinding):RecyclerView.ViewHolder(view.root)


    fun updateAnimalList(newAnimalList:List<Animal>){
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemsAnimalBinding>(inflater,R.layout.items_animal,parent,false)
        return  AnimalViewHolder(view)
    }

    override fun getItemCount()= animalList.size


    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animal = animalList[position]
        holder.view.listener = this


    }

    //retreiving the animal from the tag in xml
    override fun onClick(v: View) {
        for(animal in animalList){
            if(v.tag == animal.name){
                val action = ListFragmentDirections.actionGoToDetail(animal)
                Navigation.findNavController(v).navigate(action)
            }
        }
    }
}