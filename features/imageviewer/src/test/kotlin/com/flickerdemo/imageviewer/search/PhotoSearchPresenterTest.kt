package com.flickerdemo.imageviewer.search

import com.flickerdemo.imageviewer.api.SearchService
import com.flickerdemo.imageviewer.api.output.PagedResults
import com.flickerdemo.imageviewer.api.output.Photo
import com.flickerdemo.imageviewer.api.output.PhotoResults
import okhttp3.Request
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.stubbing.OngoingStubbing
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.reflect.KClass

class PhotoSearchPresenterTest {

    val searchText = "search text"
    val photos: Array<Photo> = arrayOf(Photo("title", 0, "server", "id1", "secret"))

    @Test
    fun should_search_with_search_text_onCreate() {
        // Arrange
        val view = PhotoSearchPresenter.PhotoSearchView::class.mock()
        val searchService = SearchService::class.mock()

        whenCalled(searchService.search(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
                .thenReturn(mock(Call::class.java) as Call<PhotoResults>)

        val presenter = PhotoSearchPresenter(view, searchService, searchText)

        // Act
        presenter.onCreate()

        // Assert
        verify(searchService).search(searchText)
    }

    @Test
    fun should_show_progress_onCreate() {
        // Arrange
        val view = PhotoSearchPresenter.PhotoSearchView::class.mock()
        val searchService = getSuccessfulService()

        val presenter = PhotoSearchPresenter(view, searchService, searchText)

        // Act
        presenter.onCreate()

        // Assert
        verify(view).showProgressBar(true)
    }

    @Test
    fun should_display_photo_results() {
        val view = PhotoSearchPresenter.PhotoSearchView::class.mock()
        val searchService = getSuccessfulService()

        val presenter = PhotoSearchPresenter(view, searchService, searchText)

        // Act
        presenter.onCreate()

        // Assert
        verify(view).show(photos)
    }

    @Test
    fun should_hide_progress_bar_on_success() {
        val view = PhotoSearchPresenter.PhotoSearchView::class.mock()
        val searchService = getSuccessfulService()

        val presenter = PhotoSearchPresenter(view, searchService, searchText)

        // Act
        presenter.onCreate()

        // Assert
        verify(view).showProgressBar(false)
    }

    @Test
    fun should_show_error_on_network_level_failures() {
        // Arrange
        val view = PhotoSearchPresenter.PhotoSearchView::class.mock()
        val searchService = getFailureService()

        val presenter = PhotoSearchPresenter(view, searchService, searchText)

        // Act
        presenter.onCreate()

        // Assert
        verify(view).showError()
    }


    @Test
    fun should_hide_progress_bar_on_recreating() {
        // Arrange
        val view = PhotoSearchPresenter.PhotoSearchView::class.mock()
        val searchService = getFailureService()

        val presenter = PhotoSearchPresenter(view, searchService, searchText)

        // Act
        presenter.onCreate(true)

        // Assert
        verify(view).showProgressBar(false)
    }

    private fun getSuccessfulService(): SearchService {
        val searchService = SearchService::class.mock()
        val response = PhotoResults(PagedResults(1, 1, photos))

        whenCalled(searchService.search(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
                .thenReturn(ImmediateCall(response))

        return searchService
    }

    private fun getFailureService(): SearchService {
        val searchService = SearchService::class.mock()
        whenCalled(searchService.search(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
                .thenReturn(ImmediateCall(IOException("network failure")))

        return searchService
    }

    fun <T : Any> KClass<T>.mock(): T = Mockito.mock(this.java)

    fun <T> whenCalled(methodCall: T): OngoingStubbing<T> = Mockito.`when`(methodCall)!!

    class ImmediateCall<T>(val responseBody: T?, val throwable: Throwable?) : Call<T> {
        constructor(responseBody: T) : this(responseBody, null)
        constructor(throwable: Throwable) : this(null, throwable)

        override fun enqueue(callback: Callback<T>) {
            when {
                responseBody != null -> callback.onResponse(this, Response.success(responseBody))
                throwable != null -> callback.onFailure(this, throwable)
                else -> throw RuntimeException("Supply a responseBody or throwable")
            }
        }

        override fun clone(): Call<T> = throw UnsupportedOperationException()

        override fun request(): Request = throw UnsupportedOperationException()

        override fun cancel() = throw UnsupportedOperationException()

        override fun execute(): Response<T> = throw UnsupportedOperationException()

        override fun isExecuted(): Boolean = throw UnsupportedOperationException()

        override fun isCanceled(): Boolean = throw UnsupportedOperationException()
    }
}