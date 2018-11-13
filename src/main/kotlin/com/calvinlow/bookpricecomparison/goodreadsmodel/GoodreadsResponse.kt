package com.calvinlow.bookpricecomparison.goodreadsmodel

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement


@XmlRootElement(name="GoodreadsResponse")
@XmlAccessorType(XmlAccessType.NONE)
class GoodreadsResponse {

    @XmlElement(name = "Request")
    val request: Request? = null

    @XmlElement(name = "search")
    var search: Search? = null

    @XmlElement(name = "book")
    var book: Book? = null

    override fun toString(): String {
        return "GoodreadsResponse(request=$request, search=$search, book=$book)"
    }

}