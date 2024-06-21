package eu.vikas.invierto.service

import eu.vikas.invierto.domain.Cuenta
import eu.vikas.invierto.model.*
import eu.vikas.invierto.repos.CuentaRepository
import eu.vikas.invierto.util.NotFoundException
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class CuentaService(
    private val cuentaRepository: CuentaRepository,
    private val transaccionService: TransaccionService,
) {

    fun findAll(): List<CuentaDTO> {
        val cuentas = cuentaRepository.findAll(Sort.by("id"))
        return cuentas.stream()
                .map { cuenta -> mapToDTO(cuenta, CuentaDTO()) }
                .toList()
    }

    fun `get`(id: Long): CuentaDTO = cuentaRepository.findById(id)
            .map { cuenta -> mapToDTO(cuenta, CuentaDTO()) }
            .orElseThrow { NotFoundException() }


    /**
     * Devuelva la inversion y sus transacciones ordenas por fecha
     * Se calcula:
     *  - saldo
     *  - monto de la transaccion positivo si es ingreso, negativo si es salida
     *    si origen y destino iguales , no se cambia el signo
     */
    fun detalleCuenta(id: Long) : DetalleCuentaDTO {


        val resDTO = this.get(id)
        val trans: List<TransaccionDTO> = transaccionService.findAllCuenta(id)
        // calculamos saldo y signo
        var saldo = resDTO.saldo ?: 0.0

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
        val detalle = DetalleCuentaDTO()
        return detalle.completarDetalleCuenta(resDTO,trans)

    }

    fun create(cuentaDTO: CuentaDTO): Long {
        val cuenta = Cuenta()
        mapToEntity(cuentaDTO, cuenta)
        return cuentaRepository.save(cuenta).id!!
    }

    fun update(id: Long, cuentaDTO: CuentaDTO) {
        val cuenta = cuentaRepository.findById(id)
                .orElseThrow { NotFoundException() }
        mapToEntity(cuentaDTO, cuenta)
        cuentaRepository.save(cuenta)
    }

    fun delete(id: Long) {
        cuentaRepository.deleteById(id)
    }

    private fun mapToDTO(cuenta: Cuenta, cuentaDTO: CuentaDTO): CuentaDTO {
        cuentaDTO.id = cuenta.id
        cuentaDTO.numeroCuenta = cuenta.numeroCuenta
        cuentaDTO.saldo = cuenta.saldo
        cuentaDTO.fechaCreacion = cuenta.fechaCreacion
        cuentaDTO.bancoId = cuenta.bancoId
        return cuentaDTO
    }

    private fun mapToEntity(cuentaDTO: CuentaDTO, cuenta: Cuenta): Cuenta {
        cuenta.numeroCuenta = cuentaDTO.numeroCuenta
        cuenta.saldo = cuentaDTO.saldo
        cuenta.fechaCreacion = cuentaDTO.fechaCreacion
        cuenta.bancoId = cuentaDTO.bancoId
        return cuenta
    }

}
