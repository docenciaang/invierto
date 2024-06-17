package eu.vikas.invierto.rest

import eu.vikas.invierto.model.RespuestaId
import eu.vikas.invierto.model.TransaccionDTO
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
    value = ["/api/transacciones"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class TransaccionResource(
    private val transaccioneservice: TransaccionService
) {

    @GetMapping
    fun getAlltransacciones(): ResponseEntity<List<TransaccionDTO>> =
            ResponseEntity.ok(transaccioneservice.findAll())

    @GetMapping("/{id}")
    fun getTransicion(@PathVariable(name = "id") id: Long): ResponseEntity<TransaccionDTO> =
            ResponseEntity.ok(transaccioneservice.get(id))

    @PostMapping
    @ApiResponse(responseCode = "201")
    fun createTransicion(@RequestBody @Valid transaccionDTO: TransaccionDTO): ResponseEntity<RespuestaId> {
        val createdId = transaccioneservice.create(transaccionDTO)
        return ResponseEntity(RespuestaId(createdId), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateTransicion(@PathVariable(name = "id") id: Long, @RequestBody @Valid
            transaccionDTO: TransaccionDTO): ResponseEntity<RespuestaId> {
        transaccioneservice.update(id, transaccionDTO)
        return ResponseEntity.ok(RespuestaId(id))
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    fun deleteTransicion(@PathVariable(name = "id") id: Long): ResponseEntity<Void> {
        transaccioneservice.delete(id)
        return ResponseEntity.noContent().build()
    }

}
