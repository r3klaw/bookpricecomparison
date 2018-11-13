package com.calvinlow.bookpricecomparison

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.apache.http.client.methods.HttpGet
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.*
import javax.xml.transform.stream.StreamSource


@Component
class ExchangeRateCronScheduler {

    val ACCESS_KEY = "7b46a89922488ab1fa0adf90cfd4349e"
    val BASE_URL = "http://apilayer.net/api/"
    val ENDPOINT = "live"

    val SOURCE = "USD"
    val TARGET = "MYR"
    var httpClient = HttpClients.createDefault()!!

    @Autowired
    private val exchangeRateRepository: ExchangeRateRepository? = null

    @Scheduled(fixedDelay = 360000)
    fun retrieveLatestCurrency() {
//        val exchangeRate: ExchangeRate? = requestCurrency(SOURCE)
//        exchangeRate?.let {
//            exchangeRateRepository?.let {
//                exchangeRateRepository.save(exchangeRate)
//            }
//        }
    }

    fun requestCurrency(source: String): ExchangeRate? {
        val get = HttpGet("$BASE_URL$ENDPOINT?access_key=$ACCESS_KEY&source=$source&currencies=$TARGET")

        val response = httpClient.execute(get)
        val entity = response.entity

        val exchangeRates = JSONObject(EntityUtils.toString(entity))

        println(exchangeRates.toString())

        val timeStampDate = Date((exchangeRates.getLong("timestamp") * 1000))

        // Get USDMYR
        val rate = exchangeRates.getJSONObject("quotes").getDouble("$SOURCE$TARGET")

        response.close()

        return ExchangeRate(null, timeStampDate, source, TARGET, rate)
    }

}