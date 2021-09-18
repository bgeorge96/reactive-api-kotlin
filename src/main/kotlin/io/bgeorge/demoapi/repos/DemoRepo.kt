package io.bgeorge.demoapi.repos

import io.bgeorge.demoapi.entities.DemoEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface DemoRepo : ReactiveCrudRepository<DemoEntity, Int>
