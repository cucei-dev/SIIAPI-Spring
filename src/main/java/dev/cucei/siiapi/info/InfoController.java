package dev.cucei.siiapi.info;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Root endpoint that returns API information.
 */
@RestController
public class InfoController {

    private static final String VERSION = "2.0.0";

    @GetMapping({"/", "/api/", "/api/v2/"})
    public InfoResponse getInfo() {
        return new InfoResponse(
            "ok",
            VERSION,
            "api.cucei.dev",
            "SIIAPI",
            "API del Sistema Integral de Informacion y Administracion Universitaria"
        );
    }
}
