//===============================================================================
// Copyright (c) 2010 Adam C Jones
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
//===============================================================================

package com.calvinlow.bookpricecomparison.goodreadsmodel

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name="work")
@XmlAccessorType(XmlAccessType.NONE)
class Work : Serializable {

    @XmlElement(name="id")
    var id: Int? = null

    @XmlElement(name="books_count")
    var booksCount: Int? = null

    @XmlElement(name = "original_title")
    var originalTitle: String? = null

    @XmlElement(name="original_publication_day")
    var originalPublicationDay: Int = 0

    @XmlElement(name="original_publication_month")
    var originalPublicationMonth: Int = 0

    @XmlElement(name="original_publication_year")
    var originalPublicationYear: Int = 0

    @XmlElement(name="ratings_sum")
    var ratingsSum: Int = 0

    @XmlElement(name="ratings_count")
    var ratingsCount: Int = 0

    @XmlElement(name="text_reviews_count")
    var textReviewsCount: Int = 0

    @XmlElement(name="average_rating")
    var averageRating: Float? = null

    @XmlElement(name = "media_type")
    var mediaType: String? = null

    @XmlElement(name="best_book")
    var bestBook: BestBook? = null

    companion object {
        private const val serialVersionUID = 0L
    }

    override fun toString(): String {
        return "Work(id=$id, booksCount=$booksCount, originalTitle=$originalTitle, originalPublicationDay=$originalPublicationDay, originalPublicationMonth=$originalPublicationMonth, originalPublicationYear=$originalPublicationYear, ratingsSum=$ratingsSum, ratingsCount=$ratingsCount, textReviewsCount=$textReviewsCount, averageRating=$averageRating, mediaType=$mediaType, bestBook=$bestBook)"
    }

}
