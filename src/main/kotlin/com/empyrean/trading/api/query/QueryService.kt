package com.empyrean.trading.api.query

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.util.*


@Service
class QueryService(private val jdbcTemplate: JdbcTemplate) {

    fun getTrades(): List<Trade> {
        val sql = "SELECT id, trade_date, trade_by, currency, trade_amount FROM TRADE LIMIT 10;"
        return jdbcTemplate.query(sql)
        { rs, _ ->
            getTradeInstance(rs)
        }.toList()
    }

    fun getTrade(id: UUID): Trade? {
        val sql = "SELECT id, trade_date, trade_by, currency, trade_amount FROM TRADE WHERE id = '$id'"
        return jdbcTemplate.queryForObject(sql)
        { rs, _ ->
            getTradeInstance(rs)
        }
    }

    private fun getTradeInstance(rs: ResultSet): Trade {
        return Trade(UUID.fromString(rs.getString("id")),
                rs.getDate("trade_date"),
                rs.getString("trade_by"),
                rs.getString("currency"),
                rs.getDouble("trade_amount"))
    }
}


data class Trade(
        val id: UUID,
        val tradeDate: Date,
        val tradeBy: String,
        val currency: String,
        val amount: Double
)
