package com.calvinlow.bookpricecomparison

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.apache.http.client.methods.HttpGet
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import java.util.*


@Component
class ExchangeRateCronScheduler {

    val ACCESS_KEY = "e2b00f4dc6c22b492a991cf9c04f7f32"
    val BASE_URL = "http://apilayer.net/api/"
    val ENDPOINT = "live"

    val SOURCE = "USD"
    val TARGET: String
        get() = "EUR"
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