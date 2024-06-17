package eu.vikas.invierto.rest

import eu.vikas.invierto.model.BancoDTO
import eu.vikas.invierto.model.RespuestaId
import eu.vikas.invierto.service.BancoService
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import java.lang.Void
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(
    value = ["/api/bancos"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class BancoResource(
    private val bancoService: BancoService
) {

    @GetMapping
    fun getAllBancos(): ResponseEntity<List<BancoDTO>> = ResponseEntity.ok(bancoService.findAll())

    @GetMapping("/{id}")
    fun getBanco(@PathVariable(name = "id") id: Long): ResponseEntity<BancoDTO> =
            ResponseEntity.ok(bancoService.get(id))

    @PostMapping
    @ApiResponse(responseCode = "201")
    fun createBanco(@RequestBody @Valid bancoDTO: BancoDTO): ResponseEntity<RespuestaId> {
        val createdId = bancoService.create(bancoDTO)
        return ResponseEntity(RespuestaId(createdId), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateBanco(@PathVariable(name = "id") id: Long, @RequestBody @Valid bancoDTO: BancoDTO):
            ResponseEntity<RespuestaId> {
        bancoService.update(id, bancoDTO)
        return ResponseEntity.ok(RespuestaId(id))
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    fun deleteBanco(@PathVariable(name = "id") id: Long): ResponseEntity<Void> {
        bancoService.delete(id)
        return ResponseEntity.noContent().build()
    }

}
