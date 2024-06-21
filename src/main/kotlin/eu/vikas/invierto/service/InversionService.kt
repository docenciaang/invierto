package eu.vikas.invierto.service

import eu.vikas.invierto.domain.Inversion
import eu.vikas.invierto.model.DetalleInversionDTO
import eu.vikas.invierto.model.InversionDTO
import eu.vikas.invierto.model.TipoTransaccion
import eu.vikas.invierto.model.TransaccionDTO
import eu.vikas.invierto.repos.InversionRepository
import eu.vikas.invierto.util.NotFoundException
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class InversionService(
    private val inversionRepository: InversionRepository,
    private val transaccionService: TransaccionService,
) {

    fun findAll(): List<InversionDTO> {
        val inversions = inversionRepository.findAll(Sort.by("id"))
        return inversions.stream()
                .map { inversion -> mapToDTO(inversion, InversionDTO()) }
                .toList()
    }

    fun `get`(id: Long): InversionDTO = inversionRepository.findById(id)
            .map { inversion -> mapToDTO(inversion, InversionDTO()) }
            .orElseThrow { NotFoundException() }

    fun create(inversionDTO: InversionDTO): Long {
        val inversion = Inversion()
        mapToEntity(inversionDTO, inversion)
        return inversionRepository.save(inversion).id!!
    }

    fun update(id: Long, inversionDTO: InversionDTO) {
        val inversion = inversionRepository.findById(id)
                .orElseThrow { NotFoundException() }
        mapToEntity(inversionDTO, inversion)
        inversionRepository.save(inversion)
    }

    fun delete(id: Long) {
        inversionRepository.deleteById(id)
    }


    /**
     * Devuelva la inversion y sus transacciones ordenas por fecha
     * Se calcula:
     *  - saldo
     *  - monto de la transaccion positivo si es ingreso, negativo si es salida
     *    si origen y destino iguales , no se cambia el signo
     */
    fun detalleInversion(id: Long) : DetalleInversionDTO{


        val resDTO = this.get(id)
        val trans: List<TransaccionDTO> = transaccionService.findAllInversion(id)
        // calculamos saldo y signo
        var saldo = resDTO.monto ?: 0.0

        trans.forEach { t ->
            var signo =
                when(t.tipo){
                    TipoTransaccion.TRASPASO -> if ( t.origenId == id ) 1 else -1
                    TipoTransaccion.COMPRA -> -1
                    TipoTransaccion.VENTA -> 1
                    TipoTransaccion.AJUSTE -> 1
                    TipoTransaccion.ENTRADA -> 1
                    TipoTransaccion.SALIDA -> -1
                    TipoTransaccion.DIVIDENDO -> +1
                    TipoTransaccion.INTERESES -> +1
                    TipoTransaccion.REVALORIZACION -> +1
                    null -> 0
                }


            saldo += signo * ( t.monto ?:0.0)
            t.saldo = saldo

        }
        val detalle = DetalleInversionDTO()
        return detalle.completarDetalleInversion(resDTO,trans)

    }


 /**
   * ===============================================
   *  funciones auxiliares
   * =================================================
   **/


    private fun mapToDTO(inversion: Inversion, inversionDTO: InversionDTO): InversionDTO {
        inversionDTO.id = inversion.id
        inversionDTO.monto = inversion.monto
        inversionDTO.fechaInversion = inversion.fechaInversion
        inversionDTO.fechaVencimiento = inversion.fechaVencimiento
        inversionDTO.tasaInteres = inversion.tasaInteres
        inversionDTO.nombreFondo = inversion.nombreFondo
        inversionDTO.valorActual = inversion.valorActual
        inversionDTO.tipo = inversion.tipo
        inversionDTO.bancoId = inversion.bancoId
        inversionDTO.archivado = inversion.archivado
        return inversionDTO
    }

    private fun mapToEntity(inversionDTO: InversionDTO, inversion: Inversion): Inversion {
        inversion.monto = inversionDTO.monto
        inversion.fechaInversion = inversionDTO.fechaInversion
        inversion.fechaVencimiento = inversionDTO.fechaVencimiento
        inversion.tasaInteres = inversionDTO.tasaInteres
        inversion.nombreFondo = inversionDTO.nombreFondo
        inversion.valorActual = inversionDTO.valorActual
        inversion.tipo = inversionDTO.tipo
        inversion.bancoId = inversionDTO.bancoId
        inversion.archivado = inversionDTO.archivado
        return inversion
    }

}
