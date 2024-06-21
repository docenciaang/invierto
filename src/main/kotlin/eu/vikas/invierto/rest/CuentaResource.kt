package eu.vikas.invierto.rest

import eu.vikas.invierto.model.CuentaDTO
import eu.vikas.invierto.model.DetalleCuentaDTO
import eu.vikas.invierto.model.DetalleInversionDTO
import eu.vikas.invierto.model.RespuestaId
import eu.vikas.invierto.service.CuentaService
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
    value = ["/api/cuentas"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class CuentaResource(
    private val cuentaService: CuentaService
) {

    @GetMapping
    fun getAllCuentas(): ResponseEntity<List<CuentaDTO>> =
            ResponseEntity.ok(cuentaService.findAll())

    @GetMapping("/{id}")
    fun getCuenta(@PathVariable(name = "id") id: Long): ResponseEntity<CuentaDTO> =
            ResponseEntity.ok(cuentaService.get(id))

    @GetMapping("/detalle/{id}")
    fun getDetallesCuenta(@PathVariable(name = "id") id: Long): ResponseEntity<DetalleCuentaDTO>{

        return ResponseEntity.ok( cuentaService.detalleCuenta(id))

    }
    @PostMapping
    @ApiResponse(responseCode = "201")
    fun createCuenta(@RequestBody @Valid cuentaDTO: CuentaDTO): ResponseEntity<RespuestaId> {
        val createdId = cuentaService.create(cuentaDTO)
        return ResponseEntity(RespuestaId(createdId), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateCuenta(@PathVariable(name = "id") id: Long, @RequestBody @Valid cuentaDTO: CuentaDTO):
            ResponseEntity<RespuestaId> {
        cuentaService.update(id, cuentaDTO)
        return ResponseEntity.ok(RespuestaId(id))
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    fun deleteCuenta(@PathVariable(name = "id") id: Long): ResponseEntity<Void> {
        cuentaService.delete(id)
        return ResponseEntity.noContent().build()
    }

}
