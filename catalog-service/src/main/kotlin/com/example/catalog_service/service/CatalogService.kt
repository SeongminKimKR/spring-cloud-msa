package com.example.catalog_service.service

import com.example.catalog_service.dto.ResponseCatalog

interface CatalogService {
    fun getAllCatalogs(): List<ResponseCatalog>
}
