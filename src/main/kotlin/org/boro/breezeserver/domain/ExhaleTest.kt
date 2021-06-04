package org.boro.breezeserver.domain

import javax.persistence.Id
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
class ExhaleTest(
    var lungCapacity: Int,
    var checkedAt: Instant,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {
    constructor(lungCapacity: Int, checkedAt: Instant): this(lungCapacity, checkedAt, null)
}
