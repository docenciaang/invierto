package eu.vikas.invierto.domain

import eu.vikas.invierto.model.TipoTransaccion
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document
class Transaccion {

    @Id
    var id: Long? = null

    var monto: Double? = null

    @Size(max = 255)
    var fecha: String? = null

    @Size(max = 255)
    var detalle: String? = null

    var origenId: Long? = null

    var destinoId: Long? = null

    var tipo: TipoTransaccion? = null

}
