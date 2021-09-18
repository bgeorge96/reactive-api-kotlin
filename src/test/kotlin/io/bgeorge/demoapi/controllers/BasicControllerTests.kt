package io.bgeorge.demoapi.controllers

import io.bgeorge.demoapi.entities.DemoEntity
import io.bgeorge.demoapi.repos.DemoRepo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import java.util.UUID

@ExtendWith(SpringExtension::class)
@WebFluxTest
class BasicControllerTests(
    @Autowired val client: WebTestClient
) {
    @MockBean
    lateinit var demoRepo: DemoRepo

    @Nested
    inner class GetMappings {
        @Test
        fun `should return no results to the caller`() {
            Mockito.`when`(demoRepo.findAll()).thenReturn(Flux.empty())

            client.get().uri("/api/all").exchange()
                .expectStatus().isOk
                .expectBody().json("[]")
        }

        @Test
        fun `should return one result to the caller`() {
            val demoEntity = DemoEntity(
                UUID.randomUUID(),
                "This is the String",
                0
            )
            Mockito.`when`(demoRepo.findAll()).thenReturn(Flux.just(demoEntity))

            client.get().uri("/api/all").exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(demoEntity.id.toString())
                .jsonPath("$.[0].column1").isEqualTo(demoEntity.column1)
                .jsonPath("$.[0].column2").isEqualTo(demoEntity.column2)
        }

        @Test
        fun `should return many results to the caller`() {
            val demoEntityFlux = createDemoEntityFlux()
            Mockito.`when`(demoRepo.findAll()).thenReturn(demoEntityFlux)

            val collectList = demoEntityFlux.collectList().block().orEmpty()
            client.get().uri("/api/all").exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(collectList[0].id.toString())
                .jsonPath("$.[0].column1").isEqualTo(collectList[0].column1)
                .jsonPath("$.[0].column2").isEqualTo(collectList[0].column2)
                .jsonPath("$.[1].id").isEqualTo(collectList[1].id.toString())
                .jsonPath("$.[1].column1").isEqualTo(collectList[1].column1)
                .jsonPath("$.[1].column2").isEqualTo(collectList[1].column2)
                .jsonPath("$.[2].id").isEqualTo(collectList[2].id.toString())
                .jsonPath("$.[2].column1").isEqualTo(collectList[2].column1)
                .jsonPath("$.[2].column2").isEqualTo(collectList[2].column2)
        }

        private fun createDemoEntityFlux(): Flux<DemoEntity> {
            val demoEntityArray = ArrayList<DemoEntity>()
            for (it in 0..3) {
                demoEntityArray.add(
                    DemoEntity(
                        UUID.randomUUID(),
                        it.toString(),
                        it
                    )
                )
            }
            return demoEntityArray.toFlux()
        }
    }
}
