document.addEventListener('DOMContentLoaded', function() {
    const fechaNacimientoInput = document.getElementById('fecha-nacimiento');
    if (fechaNacimientoInput) {
        fechaNacimientoInput.max = new Date().toISOString().split('T')[0];
    }
});


function actualizarOpcionesColabHumano(){
    const selects = document.querySelectorAll('#colaboraciones-container select');
    const selectedValues = Array.from(selects).map(select => select.value);

    let tarjetasSeleccionada = false;

    selects.forEach(select => {
        Array.from(select.options).forEach(option => {
            if (selectedValues.includes(option.value) && option.value !== select.value) {
                option.disabled = true;
            } else if(option.value != "-1"){
                option.disabled = false;
            }

            // Verificar si la opción "Distribución de tarjetas" está seleccionada
            if (option.value === "ENTREGA_TARJETAS" && option.selected) {
                tarjetasSeleccionada = true;
            }
        });
    });

    if (tarjetasSeleccionada) {
        domicilioObligatorio();
    } else {
        domicilioOpcional();
    }
}

function domicilioObligatorio(){
    var inputs = document.getElementsByClassName("input-domicilio");

    Array.from(inputs).forEach(function(input) {
        input.required = "true";
    });

    var asteriscos = document.getElementsByClassName("modalidad-domicilio");

    Array.from(asteriscos).forEach(function(asterisco) {
        asterisco.style.display = "inline";
    });
}

function domicilioOpcional(){
    var inputs = document.getElementsByClassName("input-domicilio");

    Array.from(inputs).forEach(function(input) {
        input.removeAttribute('required');
    });

    var asteriscos = document.getElementsByClassName("modalidad-domicilio");

    Array.from(asteriscos).forEach(function(asterisco) {
        asterisco.style.display = "none";
    });

    eliminarErroresDomicilio();
}

function addFormaColabHumano() {
    const container = document.getElementById('colaboraciones-container');
    const colabAddBtn = document.getElementById('colab-add-btn');
    const currentColabs = container.children.length;
    const maxColabs = 4;

    if (currentColabs >= maxColabs - 1) {
        colabAddBtn.style.display = 'none';
    }

    const newColabDiv = document.createElement('div');
    newColabDiv.classList.add('colab-container');
    newColabDiv.innerHTML = `
        <select id="formas-de-colaboracion-${currentColabs}" name="formas-de-colaboracion[]" oninput="updateColorSelect(this.id);" onchange="actualizarOpcionesColabHumano()">
                                <option value="-1" hidden disabled selected>Seleccione un tipo de colaboración </option>
                                <option value="DONACION_DINERO">Donación de dinero</option>
                                <option value="DONACION_VIANDAS">Donación de viandas</option>
                                <option value="DISTRIBUCION_VIANDAS">Distribución de viandas</option>
                                <option value="ENTREGA_TARJETAS">Distribución de tarjetas</option>
        </select>
        <button type="button" onclick="removeColab(this)" class="btn-secundario">Eliminar</button>
    `;
    container.appendChild(newColabDiv);

    actualizarOpcionesColabHumano();
}

function removeColab(button) {
    button.parentElement.remove();
    const container = document.getElementById('colaboraciones-container');
    const colabAddBtn = document.getElementById('colab-add-btn');
    const currentColabs = container.children.length;
    const maxColabs = 4;

    actualizarOpcionesColabHumano();

    if (currentColabs < maxColabs) {
        colabAddBtn.style.display = 'inline-block';
        return;
    }
}

function eliminarErroresDomicilio(){
    var inputs = document.getElementsByClassName("input-domicilio");

    Array.from(inputs).forEach(function(input) {
        limpiarError(input);
        limpiarErrorSelect(input);
    });
}

function limpiarErrorSelect(select){
    const gridSelect = select.closest('.grid-item');

    updateColorSelect(select.id)

    gridSelect.classList.remove("select2-error")

}