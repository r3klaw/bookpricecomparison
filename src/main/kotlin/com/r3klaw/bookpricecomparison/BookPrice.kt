package com.r3klaw.bookpricecomparison

import com.r3klaw.bookpricecomparison.goodreadsmodel.Book
import com.fasterxml.jackson.annotation.JsonView

data class BookPrice(@JsonView(Book.BasicView::class) val store: String, @JsonView(Book.BasicView::class) val price: Double, @JsonView(Book.BasicView::class) val link: String, @JsonView(Book.BasicView::class) val imageLink: String) {
    override fun toString(): String {
        return "BookPrice(store='$store', price=$price, link='$link')"
    }
}