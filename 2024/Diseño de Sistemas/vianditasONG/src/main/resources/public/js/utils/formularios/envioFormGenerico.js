function enviarFormulario() {
    // Obtener todos los formularios dentro del contenedor
    const formContainer = document.querySelector('.form-y-envio-container');
    const forms = formContainer.querySelectorAll('form');

    let formularioValido = true;

    // Resetear estilos y mensajes de error
    limpiarMensajeError();

    forms.forEach(form => {
        const inputs = form.querySelectorAll('input[required], select[required]');

        // Validar campos obligatorios en el formulario actual
        if (!procesarCampos(inputs, form)) {
            formularioValido = false; // Si al menos un formulario no es válido, marcar el formulario como no válido
        }
    });

    // Hacer algo si todos los formularios son válidos
    if (formularioValido) {
        console.log("Todos los formularios son válidos");
        popUpBienvenido()
    } else {
        console.log("Uno o más formularios tienen errores");
    }

}

function limpiarMensajeError() {
    var mensajesError = document.querySelectorAll(".mensaje-error");
    mensajesError.forEach(function (mensaje) {
        mensaje.remove();
    });
}

function limpiarError(input) {
    var label = document.querySelector(`label[for="${input.id}"]`);

    if (label) {
        label.classList.remove("label-error");
    }
    input.classList.remove("input-error");
}

function eliminarErroresDomicilio() {
    var inputs = document.getElementsByClassName("input-domicilio");

    Array.from(inputs).forEach(function (input) {
        limpiarError(input);
    });
}

function procesarCampos(inputs, form) {
    let formularioValido = true;

    inputs.forEach(input => {
        if (input.value === '' || input.value === '-1') {
            var label = document.querySelector(`label[for="${input.id}"]`);

            if (label) {
                label.classList.add("label-error");
            }
            input.classList.add("input-error");

            formularioValido = false;

            if (input.type === 'text') {
                input.addEventListener("input", function () {
                    limpiarError(input);
                });
            } else {
                input.addEventListener("click", function () {
                    limpiarError(input);
                });
            }
        }
    });

    return formularioValido;
}