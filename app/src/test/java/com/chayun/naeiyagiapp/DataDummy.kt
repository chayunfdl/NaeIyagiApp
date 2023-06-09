package com.chayun.naeiyagiapp

import com.chayun.naeiyagiapp.data.response.ListIyagiItem

object DataDummy {
    fun generateDummyIyagiResponse(): List<ListIyagiItem> {
        val items: MutableList<ListIyagiItem> = arrayListOf()
        for (i in 0..100) {
            val quote = ListIyagiItem(
                i.toString(),
                "url $i",
                "name + $i",
                "description $i",
                0.0,
                0.0
            )
            items.add(quote)
        }
        return items
    }
}