document.addEventListener('DOMContentLoaded', function () {
    const buttons = document.querySelectorAll('.ver-foto-btn');

    buttons.forEach(button => {
        button.addEventListener('click', function () {
            const id = this.getAttribute('data-id');
            const photoDiv = document.getElementById(`foto-${id}`);

            if (photoDiv.style.display === 'none') {
                photoDiv.style.display = 'block';
            } else {
                photoDiv.style.display = 'none';
            }
        });
    });
});




