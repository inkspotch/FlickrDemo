package com.flickerdemo.imageviewer.util

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class EndlessOnScrollListener(private val layoutManager: LinearLayoutManager, private var onLoadMore: ((Int) -> Unit)?) : RecyclerView.OnScrollListener() {
    constructor(layoutManager: LinearLayoutManager) : this(layoutManager, null)

    companion object {
        private const val THRESHOLD_COUNT = 10
    }

    private var currentPage = 1
    private var lastItemCount = 0
    private var isLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (isLoading && layoutManager.itemCount > lastItemCount) {
            lastItemCount = layoutManager.itemCount
            isLoading = false
        }

        val shouldLoadMore = layoutManager.findFirstVisibleItemPosition() + recyclerView.childCount >=
                layoutManager.itemCount - THRESHOLD_COUNT
        if (!isLoading && shouldLoadMore) {
            isLoading = true
            currentPage++

            onLoadMore?.invoke(currentPage)
        }
    }

    fun setOnLoadMore(onLoadMore: (Int) -> Unit) {
        this.onLoadMore = onLoadMore
    }
}