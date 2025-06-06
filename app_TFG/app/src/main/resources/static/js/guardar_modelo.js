document.addEventListener('DOMContentLoaded', function() {
    const overlay = document.getElementById('modalOverlay');
    const btnGuardar = document.getElementById('btnGuardar');
    const btnCerrar = document.getElementById('btnCerrar');

    // Cuando se pulsa "Guardar modelo", mostramos el overlay
    btnGuardar.addEventListener('click', function() {
        overlay.classList.add('show');
    });

    // Botón “Cancelar” dentro del overlay: ocultar el modal
    btnCerrar.addEventListener('click', function() {
        overlay.classList.remove('show');
    });

    // Si se hace clic fuera del cuadro .save-screen, también cerramos el overlay
    overlay.addEventListener('click', function(e) {
        if (e.target === overlay) {
            overlay.classList.remove('show');
        }
    });
});