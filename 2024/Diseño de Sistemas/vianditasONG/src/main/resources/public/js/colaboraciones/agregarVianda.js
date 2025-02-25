let viandaIndex = 1; // Mantendrá el índice para los nombres de viandas

function agregarVianda() {
    const formContainer = document.querySelector('.form-y-envio-container');
    const originalForm = formContainer.querySelector('form');

    if (!originalForm) {
        console.error("No se encontró el formulario original.");
        return;
    }

    // Clonar el formulario original
    const clonedForm = originalForm.cloneNode(true);

    // Limpiar los valores de los inputs en el formulario clonado
    const inputs = clonedForm.querySelectorAll('input');
    inputs.forEach(input => input.value = '');

    // Actualizar los nombres de los campos en el formulario clonado
    const comidaInput = clonedForm.querySelector('input[name^="viandas[0].comida"]');
    const caloriasInput = clonedForm.querySelector('input[name^="viandas[0].calorias"]');
    const fechaCaducidadInput = clonedForm.querySelector('input[name^="viandas[0].fechaCaducidad"]');
    const pesoInput = clonedForm.querySelector('input[name^="viandas[0].peso"]');

    // Asignar los nuevos nombres usando el índice de viandaIndex
    if (comidaInput) comidaInput.name = `viandas[${viandaIndex}].comida`;
    if (caloriasInput) caloriasInput.name = `viandas[${viandaIndex}].calorias`;
    if (fechaCaducidadInput) fechaCaducidadInput.name = `viandas[${viandaIndex}].fechaCaducidad`;
    if (pesoInput) pesoInput.name = `viandas[${viandaIndex}].peso`;

    viandaIndex++; // Incrementar el índice para el siguiente formulario clonado

    // Insertar el formulario clonado antes del footer
    const footerForm = document.querySelector('.footer-form');
    formContainer.insertBefore(clonedForm, footerForm);
}