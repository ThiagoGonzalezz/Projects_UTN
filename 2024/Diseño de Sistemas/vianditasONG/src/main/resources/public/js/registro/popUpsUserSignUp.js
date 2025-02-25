document.getElementById('toggle-password').addEventListener('click', function () {
    var passwordInput = document.getElementById('contra-input');
    var toggleIcon = document.getElementById('toggle-password');
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        toggleIcon.classList.remove('fa-eye');
        toggleIcon.classList.add('fa-eye-slash');
    } else {
        passwordInput.type = 'password';
        toggleIcon.classList.remove('fa-eye-slash');
        toggleIcon.classList.add('fa-eye');
    }
});

document.getElementById('toggle-password-confirm').addEventListener('click', function () {
    var passwordInput = document.getElementById('contra-confirm-input');
    var toggleIcon = document.getElementById('toggle-password-confirm');
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        toggleIcon.classList.remove('fa-eye');
        toggleIcon.classList.add('fa-eye-slash');
    } else {
        passwordInput.type = 'password';
        toggleIcon.classList.remove('fa-eye-slash');
        toggleIcon.classList.add('fa-eye');
    }
});

function popUpBienvenido(){
    const popUpRegistro = document.getElementById("pop-up-envio-registro");
    const popUpBienvenido = document.getElementById("pop-up-bienvenido");


    popUpRegistro.close();
    popUpBienvenido.showModal();
}

function crearCuenta(){

    var usuario = document.getElementById("usuario-input").value;
    var password = document.getElementById("contra-input").value;

    try {
        const response = fetch('/usuarios', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username: usuario, password: password })
        })
        .then(response => {
        if(response.status == 200){
        popUpBienvenido()
        }
        });
    } catch(error) {
        console.error('Error en la conexión:', error);
    }

}

////////////////////////////////////////////////////////////////////////////////////////////////////

async function validarNombreUsuario() {
    const nombreUsuario = document.getElementById("usuario-input");

    borrarErrorNombreUsuarioExistente();

    const esRepetido = await nombreUsuarioRepetido(nombreUsuario.value);

    if (esRepetido) {
        mostrarErrorNombreUsuarioExistente();
    } else {
        borrarErrorNombreUsuarioExistente();
    }
}


async function nombreUsuarioRepetido(nombre) {
    let flagRepetido = false;

    try {
        const respuesta = await fetch(`/usuarios/validacion-nombre?username=${encodeURIComponent(nombre)}`);

        if (respuesta.status == 400) {
            console.log("Usuario repetido");
            flagRepetido = true;
        }
    } catch (error) {
        console.error('Error al validar el nombre de usuario:', error);
    }

    console.log(flagRepetido);

    return flagRepetido;
}

function validarPassword() {
    validarCoincidencia();
    borrarErrorPasswordInvalida();

    const password = document.getElementById("contra-input").value;

    fetch(`/usuarios/validacion-password?password=${encodeURIComponent(password)}`)
            .then(respuesta => respuesta.text())
            .then(respuestaValidacion => {

                if (respuestaValidacion === "OK") {
                    borrarErrorPasswordInvalida();
                } else {
                    mostrarErrorPasswordInvalida(respuestaValidacion);
                }
            })
            .catch(error => {
                console.error('Error al validar la contraseña:', error);
            });
    }


function validarCoincidencia(){
    const passwordOriginal = document.getElementById("contra-input");
    const passwordConfirmacion = document.getElementById("contra-confirm-input");

    borrarErrorPasswordDistinta();

    if(passwordOriginal.value === passwordConfirmacion.value){
        borrarErrorPasswordDistinta();
    }else{
        mostrarErrorPasswordDistinta();
    }
}

function mostrarErrorNombreUsuarioExistente(){
    const usuarioInput = document.getElementById("usuario-input");
    const usuarioLabel = document.querySelector(`label[for="${"usuario-input"}"]`);

    usuarioInput.classList.add("input-error-cuenta");
    usuarioLabel.classList.add("label-error-cuenta");

    var mensajeError = document.createElement("p");
    mensajeError.classList.add("mensaje-error-usuario-existente");
    mensajeError.innerHTML =`
         <i class="fas fa-exclamation-circle icono-error-pop-up"></i>
        ${"El nombre de usuario ingresado ya esta en uso"}
        `;

    // Insertar el mensaje de error después del campo de la password
    usuarioInput.parentNode.appendChild(mensajeError);
    validarFormularioCompleto();
}

function borrarErrorNombreUsuarioExistente(){
    const usuarioInput = document.getElementById("usuario-input");
    const usuarioLabel = document.querySelector(`label[for="${"usuario-input"}"]`);

    usuarioInput.classList.remove("input-error-cuenta");
    usuarioLabel.classList.remove("label-error-cuenta");

    var mensajesError = document.querySelectorAll(".mensaje-error-usuario-existente");
        mensajesError.forEach(function(mensaje) {
        mensaje.remove();
    });

    validarFormularioCompleto();
}

function mostrarErrorPasswordInvalida(respuestaValidacion){
    const contraInput = document.getElementById("contra-input");
    const contraLabel = document.querySelector(`label[for="${"contra-input"}"]`);
    const ojoBtn = document.getElementById("toggle-password");

    contraInput.classList.add("input-error-cuenta");
    contraLabel.classList.add("label-error-cuenta");
    ojoBtn.classList.add("icono-ojo-error");

    var mensajeError = document.createElement("p");
    mensajeError.classList.add("mensaje-error-password-invalida");
    mensajeError.innerHTML = `
        <i class="fas fa-exclamation-circle icono-error-pop-up"></i>
        <a id="texto-contra-invalida">${respuestaValidacion}</a>
        `;

    // Insertar el mensaje de error después del campo de la password
    contraInput.parentNode.appendChild(mensajeError);
    validarFormularioCompleto();
}

function borrarErrorPasswordInvalida(){
    const contraInput = document.getElementById("contra-input");
    const contraLabel = document.querySelector(`label[for="${"contra-input"}"]`);
    const ojoBtn = document.getElementById("toggle-password");


    contraInput.classList.remove("input-error-cuenta");
    contraLabel.classList.remove("label-error-cuenta");
    ojoBtn.classList.remove("icono-ojo-error");

    var mensajesError = document.querySelectorAll(".mensaje-error-password-invalida");
        mensajesError.forEach(function(mensaje) {
        mensaje.remove();
    });

    validarFormularioCompleto();
}

function mostrarErrorPasswordDistinta(){
    const contraConfirmInput = document.getElementById("contra-confirm-input");
    const contraConfirmLabel = document.querySelector(`label[for="${"contra-confirm-input"}"]`);
    const ojoBtn = document.getElementById("toggle-password-confirm");

    contraConfirmInput.classList.add("input-error-cuenta");
    contraConfirmLabel.classList.add("label-error-cuenta");
    ojoBtn.classList.add("icono-ojo-error");

    var mensajeError = document.createElement("p");
    mensajeError.classList.add("mensaje-error-password-confirm-invalida");
    mensajeError.innerHTML =`
         <i class="fas fa-exclamation-circle icono-error-pop-up"></i>
        ${"Las contraseñas no coinciden"}
        `;

    // Insertar el mensaje de error después del campo de la password
    contraConfirmInput.parentNode.appendChild(mensajeError);
    validarFormularioCompleto();
}

function borrarErrorPasswordDistinta(){
    const contraConfirmInput = document.getElementById("contra-confirm-input");
    const contraConfirmLabel = document.querySelector(`label[for="${"contra-confirm-input"}"]`);
    const ojoBtn = document.getElementById("toggle-password-confirm");

    contraConfirmInput.classList.remove("input-error-cuenta");
    contraConfirmLabel.classList.remove("label-error-cuenta");
    ojoBtn.classList.remove("icono-ojo-error");

    var mensajesError = document.querySelectorAll(".mensaje-error-password-confirm-invalida");
        mensajesError.forEach(function(mensaje) {
        mensaje.remove();
    });

    validarFormularioCompleto();
}


function validarFormularioCompleto() {
    const usuarioInput = document.getElementById("usuario-input");
    const passwordInput = document.getElementById("contra-input");
    const passwordConfirmInput = document.getElementById("contra-confirm-input");
    const crearCuentaBtn = document.getElementById("crear-cuenta-btn");

    // Verifica que los campos no estén vacíos
    const camposLlenos = usuarioInput.value !== "" && passwordInput.value !== "" && passwordConfirmInput.value !== "";

    // Verifica si hay errores visibles en el formulario
    const flagSinErrores = document.querySelectorAll(".input-error-cuenta").length === 0;

    // Habilita o deshabilita el botón dependiendo de la validación
    if (camposLlenos && flagSinErrores) {
        activarCrearCuenta();
    } else {
        desactivarCrearCuenta();
    }
}

function activarCrearCuenta(){
    const botonRegistrarse = document.getElementById("boton-registrarse");

    botonRegistrarse.disabled = false;
    botonRegistrarse.classList.remove("btn-registrado-desactivado");
    botonRegistrarse.classList.add("pop-up-btn");
}

function desactivarCrearCuenta(){
    const botonRegistrarse = document.getElementById("boton-registrarse");

    botonRegistrarse.disabled = true;
    botonRegistrarse.classList.add("btn-registrado-desactivado")
    botonRegistrarse.classList.remove("pop-up-btn");

}