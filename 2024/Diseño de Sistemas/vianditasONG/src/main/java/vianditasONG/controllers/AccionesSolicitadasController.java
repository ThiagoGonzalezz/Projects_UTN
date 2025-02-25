package vianditasONG.controllers;

import vianditasONG.dtos.inputs.AccionSolicitadaInputDTO;
import vianditasONG.utils.usuarioRolesYPermisos.Usuario;
import vianditasONG.services.IAccionesSolicitadasService;

public class AccionesSolicitadasController {

    private IAccionesSolicitadasService accionesSolicitadasService;

    public AccionesSolicitadasController(IAccionesSolicitadasService accionesSolicitadasService) {
        this.accionesSolicitadasService = accionesSolicitadasService;
    }

    public Object crear(Object solicitud) {
        //En la sigueinte línea se debería hacer el mapeo real entre el verdadero input y el DTO
        AccionSolicitadaInputDTO dto = (AccionSolicitadaInputDTO) solicitud;

        //En la siguiente línea se debería obtener el usuario logueado que está ejecutando el método desde el contexto actual
        //Por fines prácticos, se le asigna null
        Usuario usuarioActual = null;

        return this.accionesSolicitadasService.crear(dto, usuarioActual);
    }

}
