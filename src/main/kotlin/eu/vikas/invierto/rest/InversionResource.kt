package eu.vikas.invierto.rest

import eu.vikas.invierto.model.DetalleInversionDTO
import eu.vikas.invierto.model.InversionDTO
import eu.vikas.invierto.model.RespuestaId
import eu.vikas.invierto.service.InversionService
import eu.vikas.invierto.service.TransaccionService
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
    value = ["/api/inversions"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class InversionResource(
    private val inversionService: InversionService,
) {

    @GetMapping
    fun getAllInversions(): ResponseEntity<List<InversionDTO>> =
            ResponseEntity.ok(inversionService.findAll())

    @GetMapping("/{id}")
    fun getInversion(@PathVariable(name = "id") id: Long): ResponseEntity<InversionDTO> =
            ResponseEntity.ok(inversionService.get(id))

    @GetMapping("/detalle/{id}")
    fun getDetallesInversion(@PathVariable(name = "id") id: Long): ResponseEntity<DetalleInversionDTO>{

        return ResponseEntity.ok( inversionService.detalleInversion(id))

    }


    @PostMapping
    @ApiResponse(responseCode = "201")
    fun createInversion(@RequestBody @Valid inversionDTO: InversionDTO): ResponseEntity<RespuestaId> {
        val createdId = inversionService.create(inversionDTO)
        return ResponseEntity(RespuestaId(createdId), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateInversion(@PathVariable(name = "id") id: Long, @RequestBody @Valid
            inversionDTO: InversionDTO): ResponseEntity<RespuestaId> {
        inversionService.update(id, inversionDTO)
        return ResponseEntity.ok(RespuestaId(id))
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    fun deleteInversion(@PathVariable(name = "id") id: Long): ResponseEntity<Void> {
        inversionService.delete(id)
        return ResponseEntity.noContent().build()
    }

}
