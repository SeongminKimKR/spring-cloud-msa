package com.example.catalog_service.controller

import com.example.catalog_service.dto.ResponseCatalog
import com.example.catalog_service.service.CatalogService
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/catalog-service")
class CatalogController (
    private val catalogService: CatalogService,
    private val env: Environment,
){
    @GetMapping("/health_check")
    fun status() = String.format("It's Working in Catalog Service on PORT %s"
        , env.getProperty("local.server.port"))

    @GetMapping("/catalogs")
    fun getCatalogs(): ResponseEntity<List<ResponseCatalog>> {
        val response = catalogService.getAllCatalogs()
        return ResponseEntity(response, HttpStatus.OK)
    }
}
