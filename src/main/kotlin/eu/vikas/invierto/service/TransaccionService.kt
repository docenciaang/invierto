package eu.vikas.invierto.service

import eu.vikas.invierto.domain.Transaccion
import eu.vikas.invierto.model.CuentaDTO
import eu.vikas.invierto.model.InversionDTO
import eu.vikas.invierto.model.TipoTransaccion
import eu.vikas.invierto.model.TransaccionDTO
import eu.vikas.invierto.repos.CuentaRepository
import eu.vikas.invierto.repos.InversionRepository
import eu.vikas.invierto.repos.TransicionRepository
import eu.vikas.invierto.util.NotFoundException
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

 /**
  * ===============================================
  * Representan movimientos entre cuentas e inversiones.
  * Origen y destino tienen significados distintos según el tipo de operacion
  *
  *  - Traspaso,
  *  - Compra: orgine  CC, destino Inversion
  *  - Venta : origen inversion , destino CC
  *  - Gasto : (comisión, etc)  para una inversion origen la CC donde se carga, destino, la inversion que se anota
  *     con el gasto.
  *   - Entrada: cualquier ingreso en CC: origen null, destino CC
  *   - Salida: cualquier movimiento de salida en CC: origen CC salida null
  *   - Dividendos y Intereses.  origen: CC donde se pagan, destino: inversion
  *   - Revalorizacion: origen=destino la inversion
  *   - Ajuste: origen=destino la inversion o CC
  *
  *   Monto : siempre positivo. Dependiendo del tipo se ve como positivo o negatico en origen/destino
  *
  * =================================================
  **/

@Service
class TransaccionService(
     private val transicionRepository: TransicionRepository,
     private val cuentaRepository: CuentaRepository,
     private val inversioRepository: InversionRepository,
) {

     /**
       * ===============================================
       *  Todos las transacciones
       * =================================================
       **/

    fun findAll(): List<TransaccionDTO> {
        val transacciones = transicionRepository.findAll(Sort.by("fechaInversion"))
        return transacciones.stream()
                .map { transicion -> mapToDTO(transicion, TransaccionDTO()) }
                .toList()
    }

      /**
        * ===============================================
        *  Transacciones de una inversion, ordenadas por fecha
       *
       *  Utilizada por inversionService
       *
        * =================================================
        **/

    fun findAllInversion(idInversion: Long): List<TransaccionDTO>{
        val orden = Sort.by(Sort.Order.asc("fechaInversion"))
        val transacciones = transicionRepository.findPorInversion(idInversion, orden)
        return transacciones.stream()
            .map { transicion -> mapToDTO(transicion, TransaccionDTO()) }
            .toList()
    }
     fun findAllCuenta(id: Long): List<TransaccionDTO> {
         val orden = Sort.by(Sort.Order.asc("fechaCreacion"))
         val transacciones = transicionRepository.findPorCuenta(id, orden)
         return transacciones.stream()
             .map { transicion -> mapToDTO(transicion, TransaccionDTO()) }
             .toList()
     }

      /**
        * ===============================================
        *  Una transaccion por id
        * =================================================
        **/

    fun `get`(id: Long): TransaccionDTO = transicionRepository.findById(id)
            .map { transicion -> mapToDTO(transicion, TransaccionDTO()) }
            .orElseThrow { NotFoundException() }

      /**
        * ===============================================
        *  Crea una nueva transaccion
       *  Comprueba id de origen y destino y tipo de inversion son compatibles
       *
       *  @exception: NotFoundException si no existe el  origen o destino
        * =================================================
        **/

    fun create(transaccionDTO: TransaccionDTO): Long {
           var cc_origen : CuentaDTO? = null
          var inv_origen : InversionDTO? = null
          var cc_destino : CuentaDTO? = null
          var inv_destino : InversionDTO? = null



        if(transaccionDTO.origenId != null ) {
            if (cuentaRepository.existsById(transaccionDTO.origenId!!))
               cc_origen = CuentaDTO().apply { id = transaccionDTO.origenId }
            else if (inversioRepository.existsById(transaccionDTO.origenId!!))
               inv_origen = InversionDTO().apply { id = transaccionDTO.origenId }
        }

          if(transaccionDTO.destinoId != null ) {
              if (cuentaRepository.existsById(transaccionDTO.destinoId!!))
                  cc_destino = CuentaDTO().apply { id = transaccionDTO.destinoId }
              else if (inversioRepository.existsById(transaccionDTO.destinoId!!))
                  inv_destino = InversionDTO().apply { id = transaccionDTO.destinoId }
          }

         // en este punto sabemos origen y destino si son cc o inversion o null




        when(transaccionDTO.tipo){
            TipoTransaccion.TRASPASO ->{
                // solo entre cuentas o entre inversiones
                if (cc_origen != null && (cc_destino != null)){
                    true
                } else if (inv_origen != null && inv_destino != null ) {
                    true
                } else {
                    throw ( NotFoundException("traspaso entre cuentas"))
                }
            }
            TipoTransaccion.COMPRA -> {
                // origen debe ser cc, destino inv
               if( cc_origen == null || inv_destino == null)
                   throw ( NotFoundException("Compra erro cc origen o inv destino"))
            }
            TipoTransaccion.VENTA -> inv_origen != null && cc_destino != null
            TipoTransaccion.AJUSTE -> transaccionDTO.origenId == transaccionDTO.destinoId
            TipoTransaccion.ENTRADA ->  cc_destino != null
            TipoTransaccion.SALIDA -> cc_origen != null
            TipoTransaccion.DIVIDENDO , TipoTransaccion.INTERESES -> cc_origen != null && inv_destino != null
            TipoTransaccion.REVALORIZACION ->{
                if( inv_origen != inv_destino)
                    throw ( NotFoundException("dividendo, origen dist destino"))
            }
            null -> false
        }


             val transaccion = Transaccion()
             mapToEntity(transaccionDTO, transaccion)
             return transicionRepository.save(transaccion).id!!


    }

      /**
        * ===============================================
        *  Actualiza una transaccion
        * =================================================
        **/

    fun update(id: Long, transaccionDTO: TransaccionDTO) {
        val transicion = transicionRepository.findById(id)
                .orElseThrow { NotFoundException() }
        mapToEntity(transaccionDTO, transicion)
        transicionRepository.save(transicion)
    }

    fun delete(id: Long) {
        transicionRepository.deleteById(id)
    }

      /**
        * ===============================================
        *  funciones privadas
        * =================================================
        **/

    private fun mapToDTO(transicion: Transaccion, transaccionDTO: TransaccionDTO): TransaccionDTO {
        transaccionDTO.id = transicion.id
        transaccionDTO.monto = transicion.monto
        transaccionDTO.fecha = transicion.fecha
        transaccionDTO.detalle = transicion.detalle
        transaccionDTO.origenId = transicion.origenId
        transaccionDTO.destinoId = transicion.destinoId
        transaccionDTO.tipo = transicion.tipo
        return transaccionDTO
    }

    private fun mapToEntity(transaccionDTO: TransaccionDTO, transicion: Transaccion): Transaccion {
        transicion.monto = transaccionDTO.monto
        transicion.fecha = transaccionDTO.fecha
        transicion.detalle = transaccionDTO.detalle
        transicion.origenId = transaccionDTO.origenId
        transicion.destinoId = transaccionDTO.destinoId
        transicion.tipo = transaccionDTO.tipo
        return transicion
    }



 }
