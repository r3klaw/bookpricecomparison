package com.calvinlow.bookpricecomparison.goodreadsmodel

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "Request")
class Request {

    @XmlElement(name = "authentication")
    val authentication: Boolean? = null

    @XmlElement(name = "key")
    val key: String? = null

    @XmlElement(name = "method")
    val method: String? = null

    override fun toString(): String {
        return "Request(authentication=$authentication, key=$key, method=$method)"
    }

}