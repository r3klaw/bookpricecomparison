package com.r3klaw.bookpricecomparison

import com.r3klaw.bookpricecomparison.goodreadsmodel.Book

data class BookDetails(var book: Book, var new: ArrayList<BookPrice>)
