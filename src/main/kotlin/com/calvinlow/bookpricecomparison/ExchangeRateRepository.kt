package com.calvinlow.bookpricecomparison

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExchangeRateRepository : JpaRepository<ExchangeRate, Long>