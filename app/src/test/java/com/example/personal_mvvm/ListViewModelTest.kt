package com.example.personal_mvvm

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.personal_mvvm.di.DaggerViewModelComponent
import com.example.personal_mvvm.di.diapp.AppModule
import com.example.personal_mvvm.models.animal.Animal
import com.example.personal_mvvm.models.animal.AnimalApiService
import com.example.personal_mvvm.models.animal.ApiKey
import com.example.personal_mvvm.models.util.SharedPreferencesHelper
import com.example.personal_mvvm.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Callable
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
    var listViewModel = ListViewModel(application,true)

    //key for test
    private val key = "Test key"



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
    //if this is not made we cannot test function that uses RxJava
    @Before
    fun setupRxSchedulers(){
        val immediate = object :Scheduler(){
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() },true)
            }
        }

        //creating thread with immediate object
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler: Callable<Scheduler> -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{scheduler: Callable<Scheduler> -> immediate }
    }


    //this is where we make the tests
    //if api call return key then list
    @Test
    fun getAnimalSuccess(){
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)
        //creating a test animal
        val animal = Animal("bat",null,null,null,null,null,null)
        val animalList = listOf(animal)

        //mocking the single
        val testSingle = Single.just(animalList)
        Mockito.`when`(animalService.getAnimal(key)).thenReturn(testSingle)

        listViewModel.refresh()

        //checking if the response is adequate
        Assert.assertEquals(1,listViewModel.animals.value?.size)
        //checking if no errors
        Assert.assertEquals(false,listViewModel.loadError.value)
        Assert.assertEquals(false,listViewModel.loading.value)
    }

    //testing api path
    @Test
    fun getAnimalFailure(){
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)
        //providing the onError branch
        val testSingle = Single.error<List<Animal>>(Throwable())
        val keySingle = Single.just(ApiKey("Ok!",key))

        Mockito.`when`(animalService.getAnimal(key)).thenReturn(testSingle)
        Mockito.`when`(animalService.getApiKey()).thenReturn(keySingle)

        listViewModel.refresh()

        //Asserting if the error branch replies correctly (false,null,true)
        Assert.assertEquals(false,listViewModel.loading.value)
        Assert.assertEquals(null,listViewModel.animals.value)
        Assert.assertEquals(true,listViewModel.loadError.value)
    }

    @Test
    fun getKeySuccess(){
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)
        val apiKey = ApiKey("Ok",key)
        val keySingle = Single.just(apiKey)

        Mockito.`when`(animalService.getApiKey()).thenReturn(keySingle)

        //creating a test animal - need a success
        val animal = Animal("bat",null,null,null,null,null,null)
        val animalList = listOf(animal)

        val testSingle = Single.just(animalList)
        Mockito.`when`(animalService.getAnimal(key)).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(1,listViewModel.animals.value?.size)
        Assert.assertEquals(false,listViewModel.loadError.value)
        Assert.assertEquals(false,listViewModel.loading.value)

    }

    @Test
    fun getKeyFailure(){
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)
        val keySingle = Single.error<ApiKey>(Throwable())

        Mockito.`when`(animalService.getApiKey()).thenReturn(keySingle)

        listViewModel.refresh()

        //null,false,true
        Assert.assertEquals(null,listViewModel.animals.value?.size)
        Assert.assertEquals(true,listViewModel.loadError.value)
        Assert.assertEquals(false,listViewModel.loading.value)

    }
}