package io.github.alexbogovich.springhateoas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.hateoas.PagedResources
import org.springframework.hateoas.Resource
import org.springframework.hateoas.core.Relation
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class SpringHateoasApplication

fun main(args: Array<String>) {
	runApplication<SpringHateoasApplication>(*args)
}

@Relation(value = "config", collectionRelation = "configs")
data class Config(val domain: String)

data class ConfigCriteria(val domain: String, val from: Int?)

@RestController
@ExposesResourceFor(Config::class)
class ConfigController {
	@GetMapping
	fun getConfigs(criteria: ConfigCriteria, pageable: Pageable, assembler: PagedResourcesAssembler<Config>): HttpEntity<PagedResources<Resource<Config>>> {
		val list = listOf(Config("test.com"))
		val toResource = assembler.toResource(PageImpl(list, pageable, 1000))
		return ResponseEntity(toResource, HttpStatus.OK)
	}
}
