package eu.vikas.invierto.model

import jakarta.validation.constraints.Size
import java.time.LocalDate


class CuentaDTO {

    var id: Long? = null

    @Size(max = 255)
    var numeroCuenta: String? = null

    var saldo: Double? = null

    var fechaCreacion: LocalDate? = null

    var bancoId: Long? = null

}
