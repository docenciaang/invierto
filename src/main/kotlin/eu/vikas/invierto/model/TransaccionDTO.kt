package eu.vikas.invierto.model

import jakarta.validation.constraints.Size


class TransaccionDTO {

    var id: Long? = null

    var monto: Double? = null

    @Size(max = 255)
    var fecha: String? = null

    @Size(max = 255)
    var detalle: String? = null

    var origneId: Long? = null

    var destinoId: Long? = null

    var tipo: TipoTransaccion? = null

    // extra para calculslos
    var saldo: Double? = null

}
