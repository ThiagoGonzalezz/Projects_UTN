function enviarFormulario() {
    const formData = new FormData();
    const form = document.querySelector('.distribucion-form');
    const heladeraDestinoId = document.getElementById("heladera-destino-id").value;
    const heladeraOrigenId = document.getElementById("heladera-origen-id").value;

    let formularioValido = true;

    limpiarMensajesError();

    const inputs = form.querySelectorAll('input[required], select[required]');
    if (!procesarCampos(inputs)) {
        formularioValido = false;
    }

    if (formularioValido) {
        inputs.forEach(input => {
            if (input.name === 'heladera-destino-id' || input.name === 'heladera-origen-id' ||
                input.name === 'motivo-de-distribucion' || input.name === 'cantidad-viandas') {
                formData.append(input.name, input.value);
            }
        });
        formData.append("heladera-destino-id", heladeraDestinoId);
        formData.append("heladera-origen-id", heladeraOrigenId);

        console.log("Formulario válido. Enviando datos...");

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/distribucion-viandas", true);

        xhr.onload = function () {
            if (xhr.status === 200) {
                console.log("Formulario enviado correctamente");
                popUpBienvenido();
            } else if (xhr.status === 400) {
                console.error("Error 400: Heladeras duplicadas.");
                window.location.href = "/errores/400HeladerasDup"; // Página de error por heladeras duplicadas
            } else if (xhr.status === 409) {
                console.error("Error 409: Cantidad de viandas incorrecta.");
                window.location.href = "/errores/409CantViandasErroneas"; // Página de error por cantidad de viandas
            } else {
                console.error(`Error al enviar el formulario. Estado: ${xhr.status}`);
            }
        };

        xhr.send(formData);  // Enviar el formulario si es válido
    } else {
        console.log("El formulario tiene errores");
    }
}


function limpiarMensajesError() {
    const mensajesError = document.querySelectorAll(".mensaje-error");
    mensajesError.forEach(mensaje => mensaje.remove());
}

function limpiarError(input) {
    const label = document.querySelector(`label[for="${input.id}"]`);
    if (label) {
        label.classList.remove("label-error");
    }
    input.classList.remove("input-error");
}

function procesarCampos(inputs) {
    let formularioValido = true;

    inputs.forEach(input => {

        if (input.value === '' || input.value === '-1') {
            const label = document.querySelector(`label[for="${input.id}"]`);

            if (label) {
                label.classList.add("label-error");
            }
            input.classList.add("input-error");

            formularioValido = false;

            if (input.type === 'text') {
                input.addEventListener("input", () => limpiarError(input));
            } else {
                input.addEventListener("click", () => limpiarError(input));
            }
        }
    });

    return formularioValido;
}