package eu.vikas.invierto.domain

import jakarta.validation.constraints.Size
import java.time.LocalDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document
class Cuenta {

    @Id
    var id: Long? = null

    @Size(max = 255)
    var numeroCuenta: String? = null

    var saldo: Double? = null

    var fechaCreacion: LocalDate? = null

    var bancoId: Long? = null

}
