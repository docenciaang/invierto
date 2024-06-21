package eu.vikas.invierto.model

import jakarta.validation.constraints.Size
import java.time.LocalDate


class DetalleCuentaDTO {

    var id: Long? = null

    @Size(max = 255)
    var numeroCuenta: String? = null

    var saldo: Double? = null

    var fechaCreacion: LocalDate? = null

    var bancoId: Long? = null

    var movimientos: List<TransaccionDTO> = emptyList()

    fun completarDetalleCuenta(ccDTO: CuentaDTO, trans: List<TransaccionDTO>): DetalleCuentaDTO {
            id = ccDTO.id
            saldo = ccDTO.saldo
            bancoId = ccDTO.bancoId
            numeroCuenta = ccDTO.numeroCuenta
            fechaCreacion = ccDTO.fechaCreacion
            movimientos = trans
        return this
    }
}
