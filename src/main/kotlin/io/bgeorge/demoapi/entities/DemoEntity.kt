package io.bgeorge.demoapi.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("demo_data")
data class DemoEntity(
    @Id val id: UUID,
    var column1: String,
    var column2: Int
)
