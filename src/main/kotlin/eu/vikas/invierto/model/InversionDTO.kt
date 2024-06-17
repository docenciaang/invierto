package eu.vikas.invierto.model

import jakarta.validation.constraints.Size
import java.time.LocalDate


class InversionDTO {

    var id: Long? = null

    var monto: Double? = null

    var fechaInversion: LocalDate? = null

    var fechaVencimiento: LocalDate? = null

    var tasaInteres: Double? = null

    @Size(max = 255)
    var nombreFondo: String? = null

    var valorActual: Double? = null

    var tipo: TipoInversion? = null

    var bancoId: Long? = null

    var archivado: Int? = null

}
