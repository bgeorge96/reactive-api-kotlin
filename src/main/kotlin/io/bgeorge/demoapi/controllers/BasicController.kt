package io.bgeorge.demoapi.controllers

import io.bgeorge.demoapi.entities.DemoEntity
import io.bgeorge.demoapi.repos.DemoRepo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class BasicController(val demoRepo: DemoRepo) {
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): Flux<DemoEntity> {
        val demoEntityArrayList = arrayListOf<DemoEntity>()
        return demoRepo.findAll()
    }
}
