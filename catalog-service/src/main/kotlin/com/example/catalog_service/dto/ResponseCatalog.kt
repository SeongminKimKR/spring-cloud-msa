package com.example.catalog_service.dto

import java.util.Date

data class ResponseCatalog(
    val productId: String,
    val productName: String,
    val unitPrice: Int,
    val stock: Int,
    val createdAt: Date
)
