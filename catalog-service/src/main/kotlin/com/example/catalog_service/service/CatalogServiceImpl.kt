package com.example.catalog_service.service

import com.example.catalog_service.dto.ResponseCatalog
import com.example.catalog_service.respository.CatalogRepository
import org.springframework.stereotype.Service

@Service
class CatalogServiceImpl(
    private val catalogRepository: CatalogRepository
) : CatalogService {
    //private val logger = LoggerFactory.getLogger(CatalogServiceImpl::class.java)

    override fun getAllCatalogs(): List<ResponseCatalog> {
        return catalogRepository
            .findAll()
            .map { it.toResponse() }
    }
}
