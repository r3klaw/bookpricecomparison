package com.calvinlow.bookpricecomparison

import com.calvinlow.bookpricecomparison.goodreadsmodel.GoodreadsResponse
import org.springframework.web.bind.annotation.RestController
import java.io.StringReader
import javax.xml.bind.JAXBContext
import com.calvinlow.bookpricecomparison.goodreadsmodel.Book
import com.calvinlow.bookpricecomparison.goodreadsmodel.Work
import com.google.common.collect.Lists
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate


@RestController
class ScrappingService {

    val BOOKURVE_URL = "http://www.bookurve.com/"

    val MPH_URL = "http://www.mphonline.com/books/nsearch.aspx?do=detail&pcode="

    val OPENTROLLEY_URL = "http://opentrolley.com.my/Book_Detail.aspx?EAN="

    val BOOKXCESS_URL = "http://www.bookxcessonline.com/index.php?route=product/search&search="

    val GOODREADS_DEVELOPER_KEY = "YOUR_GOODREADS_DEVELOPER_KEY"

    val GOODREADS_URL = "https://www.goodreads.com/search/index.xml?key=$GOODREADS_DEVELOPER_KEY"

    fun goodreads(query: String, page: Int): MutableList<MutableList<Book>> {

        val restTemplate = RestTemplate()
        val xmlStr: String? = restTemplate.getForObject("$GOODREADS_URL&q=$query&page=$page", String::class.java)

        val jaxbContext = JAXBContext.newInstance(GoodreadsResponse::class.java)
        val unmarshaller = jaxbContext.createUnmarshaller()
        val goodreadsResponse: GoodreadsResponse = unmarshaller.unmarshal(StringReader(xmlStr)) as GoodreadsResponse

        val bookList: ArrayList<Book> = bookListSearchById(goodreadsResponse.search?.results!!).filter {
            it.isEbook == false && !it.isbn.isNullOrBlank() && !it.isbn13.isNullOrBlank()
        } as ArrayList<Book>

        bookList.map {
            it.description = Jsoup.parse(it.description).text()
            it.imageUrl = it.imageUrl?.trim()
            it.smallImageUrl = it.smallImageUrl?.trim()
        }

        bookList.sortBy { it.title }

        val bookListList: MutableList<MutableList<Book>> = Lists.partition(bookList, 4)

        return bookListList
    }

    fun bookListSearchById(results: ArrayList<Work>): ArrayList<Book> {

        val threadList: ArrayList<Thread> = arrayListOf()
        val bookList: ArrayList<Book> = arrayListOf()

        for (work in results) {
            val thread = Thread({

                val book = bookSearchById(work.bestBook?.id!!)
                book?.let {
                    bookList.add(it)
                }

            })
            threadList.add(thread)
            thread.start()
        }

        threadList.forEach {
            it.join()
        }

        return bookList
    }

    fun bookSearchById(id: Int): Book? {
        val restTemplate = RestTemplate()
        val entity: ResponseEntity<String> = restTemplate.getForEntity("https://www.goodreads.com/book/show/$id.xml?key=$GOODREADS_DEVELOPER_KEY", String::class.java)

        val jaxbContext = JAXBContext.newInstance(GoodreadsResponse::class.java)
        val unmarshaller = jaxbContext.createUnmarshaller()
        val goodReadResponse: GoodreadsResponse = unmarshaller.unmarshal(StringReader(entity.body)) as GoodreadsResponse
        println(goodReadResponse.book)

        return goodReadResponse.book
    }

    fun bookPriceList(isbn: String): ArrayList<BookPrice>? {

        val priceList: ArrayList<BookPrice> = arrayListOf()

        val bookurveThread = Thread({
            // Bookurve
            val bookurveBookPrice = bookurve(isbn)
            bookurveBookPrice?.let {
                priceList.add(it)
            }
        })
        bookurveThread.start()


        val mphOnlineThread = Thread({
            // MPHOnline
            val mphPrice = mph(isbn)
            mphPrice?.let {
                priceList.add(it)
            }
        })
        mphOnlineThread.start()


        val openTrolleyThread = Thread({
            // OpenTrolley
            val openTrolleyBookPrice = openTrolley(isbn)
            openTrolleyBookPrice?.let {
                priceList.add(it)
            }
        })
        openTrolleyThread.start()


        val bookDepositoryThread = Thread({
            // BookDepository
            val bookDepositoryBookPrice = bookDepository(isbn)
            bookDepositoryBookPrice?.let {
                priceList.add(it)
            }
        })
        bookDepositoryThread.start()


        val bookXcessThread = Thread({
            // BookXcess
            val bookXcessBookPrice = bookXcess(isbn)
            bookXcessBookPrice?.let {
                priceList.add(it)
            }
        })
        bookXcessThread.start()


        bookurveThread.join()
        mphOnlineThread.join()
        openTrolleyThread.join()
        bookDepositoryThread.join()
        bookXcessThread.join()

        return priceList
    }

    fun bookurve(isbn: String): BookPrice? {
        val doc: Document = Jsoup.connect("${BOOKURVE_URL}search?keyword=$isbn").get()
        println("Bookurve - ${doc.location()}")
        val anchorElements: Elements = doc.getElementsByTag("a")
        if (anchorElements.isNotEmpty()) {
            val elementList = anchorElements.filter {
                it.attr("href").contains(isbn) && it.attr("href").contains("book")
            }
            if (elementList.isNotEmpty()) {
                val bookElement: Element = elementList[0]
                val price = bookurveDetailPrice(bookElement.attr("href"))
                price?.let {
                    return price
                }
            }
        }
        return null
    }

    private fun bookurveDetailPrice(href: String): BookPrice? {
        val link = "$BOOKURVE_URL$href"
        val doc = Jsoup.connect(link).get()

        val element: Element? = doc.select("div.item_price_container_selected").single()

        element?.let {
            val priceElement = it.select("div.price").single()
            if (priceElement.text().contains("RM", true)) {
                val price = priceElement.text().replace("RM", "", true).trim().toDouble()
                return BookPrice("Bookurve", price, link, "http://www.bookurve.com/img/logo_original.png")
            }
        }
        return null
    }


    fun openTrolley(isbn: String): BookPrice? {
        val link = "$OPENTROLLEY_URL$isbn"
        val doc = Jsoup.connect(link).get()
        println("OpenTrolley - ${doc.location()}")

        if (!doc.location().equals("http://opentrolley.com.my/Home.aspx")) {
            //Check no result
            val noResult = doc.select(".no-result-description")

            if (noResult.isEmpty()) {
                val priceStr = doc.select("div.price-after-disc").single().text()
                val price = stringToPrice(priceStr)
                return BookPrice("OpenTrolley", price, link, "http://opentrolley.com.my/Images/logotransparent.png")
            }
        }

        return null
    }

    fun bookDepository(isbn: String): BookPrice? {
        val link = googleSearchResult(isbn, "www.bookdepository.com")
        link?.let {
            val doc = Jsoup.connect(link).get()
            println("Book Depository - ${doc.location()}")
            val priceStr = doc.select("span.sale-price")
            if (priceStr.isNotEmpty()) {
                val price = stringToPrice(priceStr[0].text())
                return BookPrice("BookDepository", price, doc.location(), "https://d3ogvdx946i4sr.cloudfront.net/assets/v2.9.9/img/logo.svg")
            }

            return null
        }

        return null
    }

    fun googleSearchResult(isbn: String, domain: String): String? {
        val link = "https://www.google.com/search?q=$isbn%20site:$domain"
        val doc = Jsoup.connect(link).get()
        val resultElements = doc.select("h3.r a")
        if (resultElements.isNotEmpty()) {
            return resultElements[0].attr("href")
        }
        return null
    }

    fun bookXcess(isbn: String): BookPrice? {
        val link = "$BOOKXCESS_URL$isbn"
        val doc = Jsoup.connect(link).get()
        println("BookXcess - ${doc.location()}")
        val products = doc.select("div.product-list")
        if (products.isNotEmpty()) {
            val productElem = products[0].child(0)
            val productLink = productElem.select("div.name a").attr("href")
            val priceStr = productElem.select("span.price-new").text()
            val price = stringToPrice(priceStr)
            return BookPrice("bookXcess", price, productLink, "http://www.bookxcessonline.com/image/data/logo/bookxcess-logo.jpg")
        }
        return null
    }

    fun mph(isbn: String): BookPrice? {
        val link = "$MPH_URL$isbn"
        val doc: Document = Jsoup.connect(link).get()
        println("MPHOnline - ${doc.location()}")

        val productLink = doc.location()
        if (productLink.contains("nsearchdetails.aspx")) {
            val onlinePriceElems = doc.select("span.bookprice1")
            var price : Double = if (onlinePriceElems.isNotEmpty()) {
                val onlinePrice = onlinePriceElems[0]
                stringToPrice(onlinePrice.text())
            } else {
                val listPrice = doc.select("b:contains(List price :)").single().parent().text()
                stringToPrice(listPrice)
            }
            return BookPrice("MPHOnline", price, productLink, "http://www.mphonline.com/img/header/mphonline.png")
        }


        return null
    }

    fun stringToPrice(priceString: String): Double = priceString.replace(Regex("[^0-9.]"), "").toDouble()


}