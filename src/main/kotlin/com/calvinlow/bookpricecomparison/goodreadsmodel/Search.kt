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

import java.io.Serializable
import java.util.ArrayList
import javax.xml.bind.annotation.*

@XmlRootElement(name = "search")
@XmlAccessorType(XmlAccessType.NONE)
class Search : Serializable {

    @XmlElement(name = "query")
    var query: String? = null

    @XmlElement(name = "results-start")
    var resultsStart: Int = 0

    @XmlElement(name = "results-end")
    var resultsEnd: Int = 0

    @XmlElement(name = "total-results")
    var totalResults: Int = 0

    @XmlElement(name = "source")
    var source: String? = null

    @XmlElement(name = "query-time-seconds")
    var queryTime: Float = 0.toFloat()

    @XmlElementWrapper(name = "results")
    @XmlElement(name = "work")
    var results: ArrayList<Work>? = null

    companion object {
        private const val serialVersionUID = 0L
    }

    override fun toString(): String {
        return "Search(query=$query, resultsStart=$resultsStart, resultsEnd=$resultsEnd, totalResults=$totalResults, source=$source, queryTime=$queryTime, results=$results)"
    }

}
