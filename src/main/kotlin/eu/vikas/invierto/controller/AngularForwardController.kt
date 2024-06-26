package eu.vikas.invierto.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


/**
 * Serve Angulars index.html for all requests that are not relevant for the server.
 */
@Controller
class AngularForwardController {

    @GetMapping("{path:^(?!api|public|swagger)[^\\.]*}/**")
    fun handleForward(): String = "forward:/"

}
