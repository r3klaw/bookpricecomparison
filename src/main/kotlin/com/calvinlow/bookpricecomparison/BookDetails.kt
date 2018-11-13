package com.calvinlow.bookpricecomparison

import com.calvinlow.bookpricecomparison.goodreadsmodel.Book

data class BookDetails(var book: Book, var new: ArrayList<BookPrice>)
