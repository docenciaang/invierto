package eu.vikas.invierto.util

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException




@ResponseStatus(HttpStatus.UNAUTHORIZED)
class NoAutorizadoExcepcion : RuntimeException {

    constructor() : super()

    constructor(message: String) : super(message)

}
