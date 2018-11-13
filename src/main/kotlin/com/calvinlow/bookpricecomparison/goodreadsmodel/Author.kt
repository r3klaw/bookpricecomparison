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

import com.fasterxml.jackson.annotation.JsonView
import java.io.Serializable
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "author")
@XmlAccessorType(XmlAccessType.NONE)
class Author : Serializable {

    @JsonView(Book.BasicView::class)
    @XmlElement(name = "id")
    var id: Int = 0

    @JsonView(Book.BasicView::class)
    @XmlElement(name = "name")
    var name: String? = null

    @JsonView(Book.BasicView::class)
    @XmlElement(name = "image_url")
    var imageUrl: String? = null

    @JsonView(Book.BasicView::class)
    @XmlElement(name = "small_image_url")
    var smallImageUrl: String? = null

    @XmlElement(name = "link")
    var link: String? = null

    @XmlElement(name = "average_rating")
    var averageRating: Float = 0.toFloat()

    @XmlElement(name = "ratings_count")
    var ratingsCount: Int = 0

    @XmlElement(name = "text_reviews_count")
    var textReviewsCount: Int = 0

//    var booksStart: Int = 0
//    var booksEnd: Int = 0
//    var booksTotal: Int = 0
//    var fansCount: Int = 0
//    var about: String? = null
//    var influences: String? = null
//    var worksCount: Int = 0
//    var gender: String? = null
//    var hometown: String? = null
//    var bornAt: String? = null
//    var diedAt: String? = null
//    var userId: String? = null

    companion object {
        private const val serialVersionUID = 0L
    }

    override fun toString(): String {
        return "Author(id=$id, name=$name, imageUrl=$imageUrl, smallImageUrl=$smallImageUrl)"
    }


}
