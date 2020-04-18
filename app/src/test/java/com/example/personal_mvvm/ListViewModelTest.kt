package com.example.personal_mvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.Executor

class ListViewModelTest {
    //defining rule
    @get:Rule
    var rule = InstantTaskExecutorRule()

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