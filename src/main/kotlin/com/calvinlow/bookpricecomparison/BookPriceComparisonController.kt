package com.calvinlow.bookpricecomparison

import com.calvinlow.bookpricecomparison.goodreadsmodel.Book
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class BookPriceComparisonController {

    @GetMapping("/")
    fun index(model: Model): String {
        return "Index"
    }

    @GetMapping("/bookSearch")
    fun bookSearch(@RequestParam("query") query: String, @RequestParam("page", defaultValue = "1") page: Int, model: Model): String {
//        val modelAndView: ModelAndView = ModelAndView("redirect:Search")
//        redirectAttributes.addFlashAttribute("bookList", )
        val scrappingService: ScrappingService = ScrappingService()
        model.addAttribute("bookListList", scrappingService.goodreads(query, page))

        return "BookSearch"
    }

    @PostMapping("/priceComparison")
    @ResponseBody
    @JsonView(Book.BasicView::class)
    fun priceComparison(@RequestBody obj: Map<String, Any>): ResponseEntity<Book> {
        println(obj)
        val scrappingService = ScrappingService()
        var book: Book? = null
        val bookThread = Thread({
            book = scrappingService.bookSearchById(obj["id"].toString().toInt())
        })
        bookThread.start()

        var priceList: ArrayList<BookPrice>? = null
        val priceThread = Thread({
            priceList = scrappingService.bookPriceList(obj["isbn"].toString())
        })
        priceThread.start()

        bookThread.join()
        priceThread.join()

        book?.let { bookIt ->
            priceList?.let {
                bookIt.bookPrices.addAll(it)
                return ResponseEntity.ok(book)
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null)
    }

}