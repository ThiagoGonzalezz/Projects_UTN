function iniciarSesion() {
    var usuario = document.getElementById("usuario").value;
    var password = document.getElementById("password-input").value;

    limpiarErrores();
    
    try {
        const response = fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username: usuario, password: password })
        })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else if (response.status === 401) {
                mostrarErrorDatosIncorrectos();
           } else if (response.status === 403) {
                window.location.href = '/errores/403NoDadoDeAlta';
           }
        });
    } catch(error) {
        console.error('Error en la conexión:', error);
    }
}

function mostrarErrorDatosIncorrectos() {
    var usuarioInput = document.getElementById("usuario");
    var passwordInput = document.getElementById("password-input");
    var usuarioLabel = document.getElementById("usuario-label");
    var passwordLabel = document.getElementById("password-label");
    var toggleIcon = document.getElementById('toggle-password');

    usuarioInput.classList.add("input-error"); 
    passwordInput.classList.add("input-error");
    usuarioLabel.classList.add("label-error"); 
    passwordLabel.classList.add("label-error");
    toggleIcon.style.color = 'red';

    // Crear el mensaje de error
    var mensajeError = document.createElement("p");
    mensajeError.classList.add("mensaje-error");
    mensajeError.innerHTML =`
         <i class="fas fa-exclamation-circle icono-error"></i>
        ${"Usuario o contraseña incorrecta"}
        `;

    // Insertar el mensaje de error después del campo de la password
    passwordInput.parentNode.parentNode.appendChild(mensajeError);

    // Añadir un evento input para limpiar errores cuando el usuario empieza a escribir
    usuarioInput.addEventListener("input", function() {
        limpiarErrores();
    });

    passwordInput.addEventListener("input", function() {
        limpiarErrores();
    });
}

function limpiarErrores() {
    // Eliminar mensajes de error anteriores
    var mensajesError = document.querySelectorAll(".mensaje-error");
        mensajesError.forEach(function(mensaje) {
        mensaje.remove();
    });

    // Eliminar clase error de los inputs
    var inputs = document.querySelectorAll(".login-input");
    inputs.forEach(function(input) {
        input.classList.remove("input-error");
    });

    // Eliminar clase error de los labels
    var labels = document.querySelectorAll(".login-label");
    labels.forEach(function(label) {
        label.classList.remove("label-error");
    });

    var toggleIcon = document.getElementById('toggle-password');
    toggleIcon.style.color = '#ff9900';
}

document.getElementById('toggle-password').addEventListener('click', function () {
    var passwordInput = document.getElementById('password-input');
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

