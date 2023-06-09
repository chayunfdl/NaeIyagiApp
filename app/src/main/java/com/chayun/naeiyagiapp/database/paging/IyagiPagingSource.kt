package com.chayun.naeiyagiapp.database.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chayun.naeiyagiapp.data.api.APIService
import com.chayun.naeiyagiapp.data.model.UserPreferences
import com.chayun.naeiyagiapp.data.response.ListIyagiItem
import kotlinx.coroutines.flow.first

class IyagiPagingSource(private val apiService: APIService, private val preference: UserPreferences) : PagingSource<Int, ListIyagiItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListIyagiItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val tokenUserPaging = preference.getUser().first().token
            val responseData = apiService.getIyagiPaging(tokenUserPaging, page, params.loadSize)

            LoadResult.Page(
                data = responseData.listIyagi,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.listIyagi.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListIyagiItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}