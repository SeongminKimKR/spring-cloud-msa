package com.example.catalog_service.respository

import com.example.catalog_service.dto.ResponseCatalog
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnDefault
import java.io.Serializable
import java.util.Date

@Entity
@Table(name = "catalog")
class CatalogEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false, length = 120, unique = true)
    val productId: String,
    @Column(nullable = false)
    val productName: String,
    stock: Int,
    @Column(nullable = false)
    val unitPrice: Int,
    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    val createdAt: Date
) : Serializable {

    @Column(nullable = false)
    var stock: Int = stock
        protected  set

    fun updateStock(stock: Int) {
        this.stock = stock
    }

    fun toResponse() = ResponseCatalog(
        productId = productId,
        productName = productName,
        unitPrice = unitPrice,
        stock = stock,
        createdAt = createdAt,
    )
}
