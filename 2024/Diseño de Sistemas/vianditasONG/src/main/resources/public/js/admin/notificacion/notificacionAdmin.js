document.addEventListener('DOMContentLoaded', function() {
    const notifications = document.getElementById('notifications');
    const chatContainer = document.getElementById('chat-container');
    const closeChat = document.getElementById('close-chat');
    const noNotifications = document.getElementById('no-notifications');

    // Muestra el chat al hacer clic en el icono de notificaciones
    notifications.addEventListener('click', function(event) {
        event.preventDefault(); // Evita el comportamiento por defecto del enlace
        chatContainer.classList.toggle('show');
        
        // Si el chat está visible, ocultar el mensaje de "Todavía no hay notificaciones" si hay notificaciones
        if (chatContainer.classList.contains('show')) {
            if (chatContainer.querySelectorAll('.notification').length > 0) {
                noNotifications.style.display = 'none';
            } else {
                noNotifications.style.display = 'block';
            }
        }
    });

    // Cierra el chat al hacer clic en el botón de cerrar
    closeChat.addEventListener('click', function() {
        chatContainer.classList.remove('show');
    });

    // Ejemplo de añadir una notificación
    function addNotification(message) {
        const notification = document.createElement('div');
        notification.classList.add('notification');
        notification.textContent = message;
        chatContainer.querySelector('.chat-body').appendChild(notification);

        // Ocultar mensaje de "Todavía no hay notificaciones" si se añade una notificación
        noNotifications.style.display = 'none';
    }

    // Ejemplo de cómo agregar una notificación (descomentar para probar)
    // addNotification('Nueva notificación recibida');
});