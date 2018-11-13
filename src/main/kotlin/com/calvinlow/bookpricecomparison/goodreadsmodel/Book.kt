package com.calvinlow.bookpricecomparison.goodreadsmodel

import com.calvinlow.bookpricecomparison.BookPrice
import com.fasterxml.jackson.annotation.JsonView
import java.util.ArrayList
import javax.xml.bind.annotation.*

@XmlRootElement(name = "book")
@XmlAccessorType(XmlAccessType.NONE)
class Book {

    @JsonView(BasicView::class)
    @XmlElement(name = "id")
    val id: String? = null

    @JsonView(BasicView::class)
    @XmlElement(name = "title")
    val title: String? = null

    @JsonView(BasicView::class)
    @XmlElement(name = "isbn")
    val isbn: String? = null

    @JsonView(BasicView::class)
    @XmlElement(name = "isbn13")
    val isbn13: String? = null

    @XmlElement(name = "country_code")
    val countryCode: String? = null

    @JsonView(BasicView::class)
    @XmlElement(name = "image_url")
    var imageUrl: String? = null

    @JsonView(BasicView::class)
    @XmlElement(name = "small_image_url")
    var smallImageUrl: String? = null

    @JsonView(BasicView::class)
    @XmlElement(name = "publication_year")
    val publicationYear: Int? = null

    @JsonView(BasicView::class)
    @XmlElement(name = "publication_month")
    val publicationMonth: Int? = null

    @JsonView(BasicView::class)
    @XmlElement(name = "publication_day")
    val publicationDay: Int? = null

    @JsonView(BasicView::class)
    @XmlElement(name = "publisher")
    val publisher: String? = null

    @XmlElement(name = "language_code")
    val languageCode: String? = null

    @XmlElement(name = "is_ebook")
    val isEbook: Boolean? = null

    @JsonView(BasicView::class)
    @XmlElement(name = "description")
    var description: String? = null

    @XmlElement(name = "work")
    val work: Work? = null

    @JsonView(BasicView::class)
    @XmlElement(name="average_rating")
    var averageRating: Float? = null

    @JsonView(BasicView::class)
    @XmlElement(name="num_pages")
    var numPages: Int? = null

    @XmlElement(name = "format")
    var format: String? = null

    @XmlElement(name = "ratings_count")
    var ratingsCount: Int? = null

    @XmlElement(name = "text_reviews_count")
    var textReviewsCount: Int? = null

    @JsonView(BasicView::class)
    @XmlElement(name="link")
    var link: String? = null

    @JsonView(BasicView::class)
    @XmlElementWrapper(name = "authors")
    @XmlElement(name = "author")
    var authors: List<Author> = ArrayList()

    @JsonView(BasicView::class)
    var bookPrices: ArrayList<BookPrice> = ArrayList()

    interface BasicView

    override fun toString(): String {
        return "Book(id=$id, title=$title, work=$work, authors=$authors)"
    }


}
