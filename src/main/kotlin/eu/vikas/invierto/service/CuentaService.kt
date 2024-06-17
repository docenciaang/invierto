package eu.vikas.invierto.service

import eu.vikas.invierto.domain.Cuenta
import eu.vikas.invierto.model.CuentaDTO
import eu.vikas.invierto.repos.CuentaRepository
import eu.vikas.invierto.util.NotFoundException
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class CuentaService(
    private val cuentaRepository: CuentaRepository
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
