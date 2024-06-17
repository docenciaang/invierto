package eu.vikas.invierto.model

import jakarta.validation.constraints.Size
import java.time.LocalDate


class TransaccionInversion {

    var fecha: LocalDate? = null

    var monto: Double? = null

    @Size(max = 255)
    var detalle: String? = null

}
