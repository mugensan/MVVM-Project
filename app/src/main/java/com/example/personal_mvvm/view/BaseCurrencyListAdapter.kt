//package com.example.personal_mvvm.view
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.personal_mvvm.R
////import com.example.personal_mvvm.models.revolutmodel.BaseModel
////import com.example.personal_mvvm.models.revolutmodel.Rates
//import kotlinx.android.synthetic.main.items_animal.view.*
//
//class BaseCurrencyListAdapter(private val baseCurrencyList:ArrayList<BaseModel>):RecyclerView.Adapter<BaseCurrencyListAdapter.RatesViewHolder>(){
//
//    class RatesViewHolder(var view: View):RecyclerView.ViewHolder(view)
//
//    fun updateBaseCurrencyList(newBaseCurrencyList:List<BaseModel>){
//        baseCurrencyList.clear()
//        baseCurrencyList.addAll(newBaseCurrencyList)
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
//        val inflater= LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.items_animal,parent,false)
//        return RatesViewHolder(view)
//    }
//
//    override fun getItemCount()= baseCurrencyList.size
//
//    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
//        holder.view.tv_animal_name.text = baseCurrencyList[position].toString()
//    }
//}
//
//
