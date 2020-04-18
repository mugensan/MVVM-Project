package com.example.personal_mvvm

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.personal_mvvm.di.DaggerViewModelComponent
import com.example.personal_mvvm.di.diapp.AppModule
import com.example.personal_mvvm.models.animal.AnimalApiService
import com.example.personal_mvvm.models.util.SharedPreferencesHelper
import com.example.personal_mvvm.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

class ListViewModelTest {
    //defining rule
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var animalService: AnimalApiService

    @Mock
    lateinit var prefs:SharedPreferencesHelper

    val application = Mockito.mock(Application::class.java)
    var listViewModel = ListViewModel(application)

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        DaggerViewModelComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModuleTest(animalService))
            .prefsModule(PrefsModuleTest(prefs))
            .build()
            .inject(listViewModel)
    }

    //schedulers working on dif thread (new and main)
    @Before
    fun setupRxSchedulers(){
        val immediate = object :Scheduler(){
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() },true)
            }
        }

        //creating thread with immediate object
        RxJavaPlugins.setInitNewThreadSchedulerHandler { schedular -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{schedular -> immediate}
    }
}