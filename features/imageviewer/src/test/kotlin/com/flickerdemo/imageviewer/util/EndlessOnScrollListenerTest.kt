package com.flickerdemo.imageviewer.util

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.flickerdemo.imageviewer.mock
import com.flickerdemo.imageviewer.whenCalled
import org.junit.Assert.assertEquals
import org.junit.Test

class EndlessOnScrollListenerTest {
    @Test
    fun should_load_next_page_at_bottom_of_the_items() {
        // Arrange
        val layoutManager = GridLayoutManager::class.mock()
        whenCalled(layoutManager.itemCount).thenReturn(50)
        whenCalled(layoutManager.findFirstVisibleItemPosition()).thenReturn(45)

        val recyclerView = RecyclerView::class.mock()
        whenCalled(recyclerView.childCount).thenReturn(5)

        val scrollListener = EndlessOnScrollListener(layoutManager)

        var currentPage = 1
        scrollListener.setOnLoadMore({ page ->
            currentPage = page
        })

        // Act
        scrollListener.onScrolled(recyclerView, 0, 0)

        // Assert
        assertEquals(2, currentPage)
    }

    @Test
    fun should_not_load_next_page_if_already_loading() {
        val layoutManager = GridLayoutManager::class.mock()
        whenCalled(layoutManager.itemCount).thenReturn(50)
        whenCalled(layoutManager.findFirstVisibleItemPosition()).thenReturn(45)

        val recyclerView = RecyclerView::class.mock()
        whenCalled(recyclerView.childCount).thenReturn(5)

        val scrollListener = EndlessOnScrollListener(layoutManager)

        var wasCalledCount = 0
        scrollListener.setOnLoadMore({
            wasCalledCount++
        })

        // Act
        scrollListener.onScrolled(recyclerView, 0, 0)
        scrollListener.onScrolled(recyclerView, 0, 0)

        // Assert
        assertEquals(1, wasCalledCount)
    }

    @Test
    fun should_allow_loading_after_item_count_increases() {
        val layoutManager = GridLayoutManager::class.mock()
        whenCalled(layoutManager.itemCount).thenReturn(50)
        whenCalled(layoutManager.findFirstVisibleItemPosition()).thenReturn(45)

        val recyclerView = RecyclerView::class.mock()
        whenCalled(recyclerView.childCount).thenReturn(5)

        val scrollListener = EndlessOnScrollListener(layoutManager)

        var wasCalledCount = 0
        scrollListener.setOnLoadMore({
            wasCalledCount++
        })

        scrollListener.onScrolled(recyclerView, 0, 0)
        scrollListener.onScrolled(recyclerView, 0, 0)
        assertEquals("Guard assert", 1, wasCalledCount)

        whenCalled(layoutManager.itemCount).thenReturn(90)
        whenCalled(layoutManager.findFirstVisibleItemPosition()).thenReturn(85)

        // Act
        scrollListener.onScrolled(recyclerView, 0, 0)

        // Assert
        assertEquals(2, wasCalledCount)
    }
}