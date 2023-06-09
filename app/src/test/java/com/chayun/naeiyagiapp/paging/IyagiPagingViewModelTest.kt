package com.chayun.naeiyagiapp.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.chayun.naeiyagiapp.DataDummy
import com.chayun.naeiyagiapp.MainDispatcherRule
import com.chayun.naeiyagiapp.adapter.IyagiListAdapter
import com.chayun.naeiyagiapp.data.response.ListIyagiItem
import com.chayun.naeiyagiapp.database.paging.IyagiPagingViewModel
import com.chayun.naeiyagiapp.database.repository.IyagiPagingRepository
import com.chayun.naeiyagiapp.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class IyagiPagingViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()
    @Mock
    private lateinit var iyagiPagingRepository: IyagiPagingRepository

    @Test
    fun `when Get Iyagi Should Not Null and Return Data`() = runTest {
        val dummyIyagi = DataDummy.generateDummyIyagiResponse()
        val data: PagingData<ListIyagiItem> = IyagiPagingSource.snapshot(dummyIyagi)
        val expectedIyagi = MutableLiveData<PagingData<ListIyagiItem>>()
        expectedIyagi.value = data
        Mockito.`when`(iyagiPagingRepository.getIyagiPaging()).thenReturn(expectedIyagi)

        val iyagiPagingViewModel = IyagiPagingViewModel(iyagiPagingRepository)
        val actualIyagi: PagingData<ListIyagiItem> = iyagiPagingViewModel.iyagiPaging.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = IyagiListAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualIyagi)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyIyagi.size, differ.snapshot().size)
        Assert.assertEquals(dummyIyagi[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get Iyagi Empty Should Return No Data`() = runTest {
        val data: PagingData<ListIyagiItem> = PagingData.from(emptyList())
        val expectedIyagi = MutableLiveData<PagingData<ListIyagiItem>>()
        expectedIyagi.value = data
        Mockito.`when`(iyagiPagingRepository.getIyagiPaging()).thenReturn(expectedIyagi)

        val iyagiPagingViewModel = IyagiPagingViewModel(iyagiPagingRepository)
        val actualIyagi: PagingData<ListIyagiItem> = iyagiPagingViewModel.iyagiPaging.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = IyagiListAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualIyagi)

        Assert.assertEquals(0, differ.snapshot().size)
    }
}

class IyagiPagingSource : PagingSource<Int, LiveData<List<ListIyagiItem>>>() {
    companion object {
        fun snapshot(items: List<ListIyagiItem>): PagingData<ListIyagiItem> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListIyagiItem>>>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListIyagiItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}