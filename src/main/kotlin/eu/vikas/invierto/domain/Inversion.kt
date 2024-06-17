package eu.vikas.invierto.domain

import eu.vikas.invierto.model.TipoInversion
import jakarta.validation.constraints.Size
import java.time.LocalDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document
class Inversion {

    @Id
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
