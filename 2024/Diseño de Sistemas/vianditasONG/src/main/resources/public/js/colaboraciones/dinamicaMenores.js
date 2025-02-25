// dinamicaMenores.js
function calculateAge(dateOfBirth) {
    const today = new Date();
    const birthDate = new Date(dateOfBirth);
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDifference = today.getMonth() - birthDate.getMonth();
    if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
    return age;
}

document.getElementById('cantidad-menores').addEventListener('input', function () {
    const cantidad = parseInt(this.value);
    const container = document.getElementById('formularios-menores-container');
    container.innerHTML = '';

    const fechaNacimiento = document.getElementById('fecha-nacimiento').value;
    const age = calculateAge(fechaNacimiento);

    if (isNaN(age) || age < 18) {
            alert('Debe ser mayor de 18 años para registrar menores a cargo.');
            this.value = -1;
            return;
    }

    if (!isNaN(cantidad) && cantidad > 0) {
        for (let i = 0; i < cantidad; i++) {
            const form = document.createElement('div');
            form.className = 'formulario-menor';
            form.innerHTML = `
                <h4>Menor ${i + 1}</h4>
                <div class="grid-item">
                    <label for="nombre-menor-${i}">Nombre *</label>
                    <input type="text" id="nombre-menor-${i}" name="menores[${i}][nombre]" class="text-input" required>
                </div>
                <div class="grid-item">
                    <label for="apellido-menor-${i}">Apellido *</label>
                    <input type="text" id="apellido-menor-${i}" name="menores[${i}][apellido]" class="text-input" required>
                </div>
                <div class="grid-item">
                    <label for="fecha-nacimiento-menor-${i}">Fecha Nacimiento</label>
                    <input type="date" id="fecha-nacimiento-menor-${i}" name="menores[${i}][fechaDeNacimiento]" class="fecha-input" required>
                </div>
                <div class="grid-item">
                    <label for="tipo-dni-menor-${i}">Tipo de Documento *</label>
                    <select id="tipo-dni-menor-${i}" name="menores[${i}][tipoDeDocumento]" class="text-input" required>
                        <option value="DNI">DNI</option>
                        <option value="LC">Libreta cívica</option>
                        <option value="LE">Libreta de enrolamiento</option>
                    </select>
                </div>
                <div class="grid-item">
                    <label for="dni-menor-${i}">Numero de Documento *</label>
                    <input type="text" id="dni-menor-${i}" name="menores[${i}][numeroDeDocumento]" class="text-input" required>
                </div>
            `;
            container.appendChild(form);
        }
    }
});

document.getElementById('form-principal').addEventListener('submit', function (event) {
    const container = document.getElementById('formularios-menores-container');
    const formPrincipal = document.getElementById('form-principal');
    let isValid = true;

    // Validar manualmente los campos requeridos de los formularios de menores
    const inputs = container.querySelectorAll('input[required], select[required]');
    inputs.forEach(input => {
        if (!input.value) {
            isValid = false;
            input.classList.add('invalid');
        } else {
            input.classList.remove('invalid');
        }
    });

    if (!isValid) {
        event.preventDefault();
        alert('Por favor, complete todos los campos requeridos de los menores.');
    } else {
        // Mover temporalmente los formularios de los menores dentro del formulario principal
        while (container.firstChild) {
            formPrincipal.appendChild(container.firstChild);
        }

        // Enviar el formulario
        formPrincipal.submit();
    }
});

function popUpBienvenido() {
    const container = document.getElementById('formularios-menores-container');
    let isValid = true;

    // Validar manualmente los campos requeridos de los formularios de menores
    const inputs = container.querySelectorAll('input[required], select[required]');
    inputs.forEach(input => {
        if (!input.value) {
            isValid = false;
            input.classList.add('invalid');
        } else {
            input.classList.remove('invalid');
        }
    });

    if (isValid) {
        const popUpBienvenido = document.getElementById("pop-up-bienvenido");
        popUpBienvenido.showModal();
    } else {
        alert('Por favor, complete todos los campos requeridos de los menores.');
    }
}

