package com.tom.kata.coroutines.parser

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tom.kata.coroutines.parser.ui.ResultsAdapter
import com.tom.kata.coroutines.parser.ui.WrapContentLinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var resultsAdapter: ResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRecyclerView()
        val model = setViewModel(savedInstanceState)
        setCallbacks(model)
    }

    private fun setRecyclerView() {
        resultsAdapter = ResultsAdapter()
        WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            .let { layout ->
            recyclerView.apply {
                layoutManager = layout
                adapter = resultsAdapter
                isDrawingCacheEnabled = true
                drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                hasFixedSize()
                setItemViewCacheSize(20)
            }
        }
    }

    private fun setViewModel(savedInstanceState: Bundle?): MainViewModel {
        val lifecycleOwner = this

        return MainViewModel.create(this).apply {
            states.observe(lifecycleOwner, render())

            if (savedInstanceState == null) {
                load()
            }
        }
    }

    private fun setCallbacks(model: MainViewModel) {
        retry.setOnClickListener { model.load() }
    }

    private fun render() = Observer<MainViewModel.State> { state ->
        when (state) {
            is MainViewModel.State.Success -> {
                View.GONE.let {
                    error.visibility = it
                    retry.visibility = it
                    progress.visibility = it
                }
                recyclerView.visibility = View.VISIBLE
                resultsAdapter.addAll(state.result)
            }
            is MainViewModel.State.Loading -> {
                View.GONE.let {
                    error.visibility = it
                    retry.visibility = it
                    recyclerView.visibility = it
                }
                progress.visibility = View.VISIBLE
            }
            is MainViewModel.State.Error -> {
                View.GONE.let {
                    progress.visibility = it
                    recyclerView.visibility = it
                }
                View.VISIBLE.let {
                    error.visibility = it
                    retry.visibility = it
                }
                error.text = state.throwable.message
            }
        }
    }
}