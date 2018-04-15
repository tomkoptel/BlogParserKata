package com.tom.kata.coroutines.parser

import android.arch.lifecycle.*
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.tom.kata.coroutines.parser.data.ApiService
import com.tom.kata.coroutines.parser.domain.ParseResult
import com.tom.kata.coroutines.parser.domain.allNthCharacters
import com.tom.kata.coroutines.parser.domain.firstNthCharacter
import com.tom.kata.coroutines.parser.domain.wordFrequencies
import com.tom.kata.coroutines.parser.ui.UiEntity
import com.tom.kata.coroutines.parser.ui.UiEntityMapper
import com.tom.kata.coroutines.parser.utils.CoroutinesProvider
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

/**
 * Core logic of application that relies on [ViewModel] to retain long running task across screen rotations and
 * caches/exposes result with [LiveData] instance. The API relies on Kotlin coroutines to offload to the work to the
 * worker threads.
 */
class MainViewModel(
    private val apiService: ApiService,
    private val coroutinesProvider: CoroutinesProvider,
    private val uiEntityMapper: UiEntityMapper
) : ViewModel() {
    /**
     * Parent job is the same as composite subscription from RxJava. Allows to kill running job if no long required.
     * In our case it can be that user left activity.
     */
    private val lifecycleJob = Job()
    private val mutableState = MutableLiveData<State>().apply { value =
            State.Loading
    }
    val states: LiveData<State> = mutableState

    fun load() {
        mutableState.value = State.Loading
        coroutinesProvider.launch(UI, parent = lifecycleJob) {
            try {
                // Start all 3 requests immediately.
                val firstRequest = apiService.blogPost()
                val secondRequest = apiService.blogPost()
                val thirdRequest = apiService.blogPost()

                // Wait for 3 responses
                val response1 = firstRequest.await().charStream()
                val response2 = secondRequest.await().charStream()
                val response3 = thirdRequest.await().charStream()

                // Offload mapping to the background
                val result = async(parent = lifecycleJob) {
                    val firstCharacter = ParseResult.Character.mapToList(response1.firstNthCharacter(9))
                    val every10Character = ParseResult.Characters.mapToList(response2.allNthCharacters(9))
                    val words = ParseResult.Word.mapToList(response3.wordFrequencies())
                    val results = firstCharacter.plus(every10Character).plus(words)

                    uiEntityMapper.mapTo(results)
                }.await()

                mutableState.value = State.Success(result)
            } catch (error: Exception) {
                mutableState.value = State.Error(error)
            }
        }
    }

    override fun onCleared() {
        lifecycleJob.cancel()
    }

    /**
     * Represents page primary states.
     */
    sealed class State {
        object Loading : State()
        class Success(val result: List<UiEntity>) : State()
        class Error(val throwable: Throwable) : State()
    }

    /**
     * Factory api to encapsulate out creation of view model.
     */
    companion object {
        fun create(context: Context) =
            ViewModelProviders.of(context as AppCompatActivity,
                Factory(context)
            )
                .get(MainViewModel::class.java)
    }

    private class Factory(private val context: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                val component = ParserApp.component(context)
                val apiService = ApiService.Factory.create(component.okHttpClient())
                val uiEntityMapper = UiEntityMapper(context)
                return MainViewModel(
                    apiService,
                    component.coroutinesProvider,
                    uiEntityMapper
                ) as T
            }
            throw IllegalArgumentException("Unknown $modelClass class ")
        }
    }
}