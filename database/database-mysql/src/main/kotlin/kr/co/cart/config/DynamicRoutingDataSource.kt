package kr.co.cart.config

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager

internal class DynamicRoutingDataSource: AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey(): Any = when {
        TransactionSynchronizationManager.isCurrentTransactionReadOnly() -> READ
        else -> WRITE
    }


    companion object {
        const val READ = "read"
        const val WRITE = "write"
    }
}