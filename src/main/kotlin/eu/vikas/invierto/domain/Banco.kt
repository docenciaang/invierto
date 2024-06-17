package eu.vikas.invierto.domain

import jakarta.validation.constraints.Size
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document
class Banco {

    @Id
    var id: Long? = null

    @Size(max = 255)
    var nombre: String? = null

}
