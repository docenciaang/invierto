package eu.vikas.invierto.model

import jakarta.validation.constraints.Size
import java.time.LocalDate


class DetalleInversionDTO {


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
    var movimientos: List<TransaccionDTO> = emptyList()

    fun completarDetalleInversion(invDTO: InversionDTO, trans: List<TransaccionDTO>): DetalleInversionDTO {

        return DetalleInversionDTO().apply {
            id = invDTO.id
            monto = invDTO.monto
            fechaInversion = invDTO.fechaInversion
            fechaVencimiento = invDTO.fechaVencimiento
            tasaInteres = invDTO.tasaInteres
            nombreFondo = invDTO.nombreFondo
            valorActual = invDTO.valorActual
            tipo = invDTO.tipo
            bancoId = invDTO.bancoId
            archivado = invDTO.archivado
            movimientos = trans
        }
    }
}
