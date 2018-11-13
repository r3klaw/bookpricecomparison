package com.calvinlow.bookpricecomparison

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "exchange_rate")
data class ExchangeRate(@Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long?,
                   @Column(name = "date", unique = true) val date: Date,
                   @Column(name = "from_currency", nullable = false) val from: String,
                   @Column(name = "to_currency", nullable = false) val to: String,
                   @Column(name = "rate", nullable = false) val rate: Double)