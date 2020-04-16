package com.example.personal_mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.personal_mvvm.R
import com.example.personal_mvvm.models.animal.Animal
import com.example.personal_mvvm.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val listAdapter = AnimalListAdapter(arrayListOf())


    private val animalListDataObserver = Observer<List<Animal>> { list ->
        //if the list is not null perform code block, lambda expression
        list?.let {
            rv_animal_list.visibility = View.VISIBLE
            listAdapter.updateAnimalList(it)
        }
    }

    private val loadingLiveDataObserver = Observer<Boolean> { isLoading ->
        pb_loading_view.visibility = if (isLoading) View.VISIBLE else View.GONE
        if(isLoading){
            tv_list_error.visibility = View.GONE
            rv_animal_list.visibility = View.GONE
        }
    }

    private val loadErrorLiveDataObserver = Observer<Boolean> { isError ->
        tv_list_error.visibility = if(isError) View.VISIBLE else View.GONE
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //store the list model and attaches it to the frag
        //THIS IS WHERE WE ATTACH OUR MODEL -> MVVM STYLE!!! 1 VM PER VIEW AND 1 VIEW PER VM
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        //observe the liveData  (ej: MutableLiveData -> viewModel class)
        viewModel.animals.observe(this, animalListDataObserver)
        viewModel.loading.observe(this, loadingLiveDataObserver)
        viewModel.loadError.observe(this, loadErrorLiveDataObserver)

        viewModel.refresh()

        rv_animal_list.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }
        //fixing the bug of the refresh layout (hide spinner)
        refresh_layout.setOnRefreshListener {
            rv_animal_list.visibility = View.GONE
            tv_list_error.visibility = View.VISIBLE
            //hide spinner
            viewModel.refresh()
            refresh_layout.isRefreshing = false
        }

        //test btn to move between fragments
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        btn_detail.setOnClickListener{
//            val action = ListFragmentDirections.actionGoToDetail()
//            Navigation.findNavController(it).navigate(action)
//        }
    }
}
