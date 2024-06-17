package eu.vikas.invierto.service

import eu.vikas.invierto.domain.Transaccion
import eu.vikas.invierto.model.TransaccionDTO
import eu.vikas.invierto.repos.TransicionRepository
import eu.vikas.invierto.util.NotFoundException
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

 /**
   * ===============================================
   *
   * =================================================
   **/

@Service
class TransaccionService(
    private val transicionRepository: TransicionRepository
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
        *  Transacciones de una inversion
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
       *  Comprueba id de origen y destino y tipo de inversion
        * =================================================
        **/

    fun create(transaccionDTO: TransaccionDTO): Long {
        val transicion = Transaccion()
        mapToEntity(transaccionDTO, transicion)
        return transicionRepository.save(transicion).id!!
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

    private fun mapToDTO(transicion: Transaccion, transaccionDTO: TransaccionDTO): TransaccionDTO {
        transaccionDTO.id = transicion.id
        transaccionDTO.monto = transicion.monto
        transaccionDTO.fecha = transicion.fecha
        transaccionDTO.detalle = transicion.detalle
        transaccionDTO.origneId = transicion.origneId
        transaccionDTO.destinoId = transicion.destinoId
        transaccionDTO.tipo = transicion.tipo
        return transaccionDTO
    }

    private fun mapToEntity(transaccionDTO: TransaccionDTO, transicion: Transaccion): Transaccion {
        transicion.monto = transaccionDTO.monto
        transicion.fecha = transaccionDTO.fecha
        transicion.detalle = transaccionDTO.detalle
        transicion.origneId = transaccionDTO.origneId
        transicion.destinoId = transaccionDTO.destinoId
        transicion.tipo = transaccionDTO.tipo
        return transicion
    }

}
