package eu.vikas.invierto.model

import jakarta.validation.constraints.Size


class BancoDTO {

    var id: Long? = null

    @Size(max = 255)
    var nombre: String? = null

}
