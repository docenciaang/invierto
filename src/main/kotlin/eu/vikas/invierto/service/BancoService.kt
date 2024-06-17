package eu.vikas.invierto.service

import eu.vikas.invierto.domain.Banco
import eu.vikas.invierto.model.BancoDTO
import eu.vikas.invierto.repos.BancoRepository
import eu.vikas.invierto.util.NotFoundException
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class BancoService(
    private val bancoRepository: BancoRepository
) {

    fun findAll(): List<BancoDTO> {
        val bancoes = bancoRepository.findAll(Sort.by("id"))
        return bancoes.stream()
                .map { banco -> mapToDTO(banco, BancoDTO()) }
                .toList()
    }

    fun `get`(id: Long): BancoDTO = bancoRepository.findById(id)
            .map { banco -> mapToDTO(banco, BancoDTO()) }
            .orElseThrow { NotFoundException() }

    fun create(bancoDTO: BancoDTO): Long {
        val banco = Banco()
        mapToEntity(bancoDTO, banco)
        return bancoRepository.save(banco).id!!
    }

    fun update(id: Long, bancoDTO: BancoDTO) {
        val banco = bancoRepository.findById(id)
                .orElseThrow { NotFoundException() }
        mapToEntity(bancoDTO, banco)
        bancoRepository.save(banco)
    }

    fun delete(id: Long) {
        bancoRepository.deleteById(id)
    }

    private fun mapToDTO(banco: Banco, bancoDTO: BancoDTO): BancoDTO {
        bancoDTO.id = banco.id
        bancoDTO.nombre = banco.nombre
        return bancoDTO
    }

    private fun mapToEntity(bancoDTO: BancoDTO, banco: Banco): Banco {
        banco.nombre = bancoDTO.nombre
        return banco
    }

}
