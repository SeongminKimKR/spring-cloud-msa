package com.example.catalog_service.domain

import java.io.Serializable

class Catalog(
    val productId: String,
    val qty: Int,
    val unitPrice: Int,
    val totalPrice: Int,
    val orderId: String,
    val userId: String,
) : Serializable
