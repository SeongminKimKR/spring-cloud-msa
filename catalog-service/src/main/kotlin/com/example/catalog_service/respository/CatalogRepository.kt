package com.example.catalog_service.respository

import org.springframework.data.jpa.repository.JpaRepository

interface CatalogRepository : JpaRepository<CatalogEntity, Long> {
    fun findByProductId(productId: String): CatalogEntity?
}
