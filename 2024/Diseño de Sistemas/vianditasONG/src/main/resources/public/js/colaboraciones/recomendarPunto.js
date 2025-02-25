document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById('modal');
    const acceptButton = document.getElementById('accept-button');
    const recommendLink = document.getElementById('recommend-link');
    const notifications = document.getElementById('notifications');
    const chatContainer = document.getElementById('chat-container');
    const noNotifications = document.getElementById('no-notifications');
    const notificationDot = notifications.querySelector('.notification-dot');

    function mostrarMensaje() {
        if (modal) {
            modal.style.display = 'block';
        }
    }

    function requestRecommendation() {
        mostrarMensaje();

        fetch('/colocar-heladera/puntos-recomendados', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error ${response.status}: ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
                const puntos = data.puntos.map((punto, index) => {
                const direccion = `${punto.direccion.calle} ${punto.direccion.altura || ''}`.trim();
                const latitud = punto.puntoGeografico.latitud;
                const longitud = punto.puntoGeografico.longitud;

                return `
                    <li>
                        ${direccion} (${latitud}, ${longitud})
                    </li>
                    ${index < data.puntos.length - 1 ? '<hr>' : ''}
                `;
                }).join("");

            const contenidoNotificacion = `
                <ul>${puntos}</ul>
            `;
            showNotification('Direcciones recomendadas:', contenidoNotificacion);
        })
        .catch(error => {
            console.error('Error al enviar los datos:', error);
            showNotification('Error:', 'Ocurrió un error al realizar la recomendación.');
        });
    }

    function showNotification(title, content) {
        const chatBody = chatContainer.querySelector('.chat-body');

        if (!chatBody) {
            console.error("Elemento 'chatBody' no encontrado en 'chatContainer'.");
            return;
        }

        const notification = document.createElement('div');
        notification.classList.add('notification');

        const notificationTitle = document.createElement('div');
        notificationTitle.classList.add('notification-title');
        notificationTitle.textContent = title;

        const notificationContent = document.createElement('div');
        notificationContent.classList.add('notification-content');
        notificationContent.innerHTML = content;

        notification.appendChild(notificationTitle);
        notification.appendChild(notificationContent);
        chatBody.appendChild(notification);


        if (noNotifications) {
            noNotifications.style.display = 'none';
        }

        chatContainer.classList.add('show');


        if (notificationDot) {
            notificationDot.style.display = 'block';
        }
    }

    acceptButton.addEventListener('click', function () {
        if (modal) {
            modal.style.display = 'none';
        }
    });

    if (recommendLink) {
        recommendLink.addEventListener('click', function (event) {
            event.preventDefault();
            requestRecommendation();
        });
    }
});