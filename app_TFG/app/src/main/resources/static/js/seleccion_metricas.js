document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.dropdown-multiselect').forEach(container => {
        const button = container.querySelector('.dropdown-toggle');
        const menu   = container.querySelector('.dropdown-menu');
        const checkboxes = container.querySelectorAll('.form-control-metrica');
        const hiddenInput = container.querySelector('input[type="hidden"]');

        // 1) Mostrar/ocultar dropdown al hacer clic en el botón
        button.addEventListener('click', () => {
            menu.style.display = (menu.style.display === 'block') ? 'none' : 'block';
        });

        // 2) Función que actualiza el <input type="hidden"> y cambia el texto del botón
        function updateSelection() {
            // Recogemos solo los valores de los checkboxes marcados
            const seleccionadas = Array.from(checkboxes)
                .filter(cb => cb.checked)
                .map(cb => cb.value);

            // En el hidden, las pasamos separadas por coma
            hiddenInput.value = seleccionadas.join(',');

            // Si hay alguna seleccionada, mostramos esa lista en el botón
            if (seleccionadas.length > 0) {
                button.innerHTML = seleccionadas.join(', ') + ' <span class="arrow">▼</span>';
            } else {
                // Si no hay, volvemos al texto por defecto
                button.innerHTML = 'Seleccione las métricas que desee para el test<span class="arrow">▼</span>';
            }
        }

        // 3) Asociar change a cada checkbox
        checkboxes.forEach(cb => {
            cb.addEventListener('change', updateSelection);
        });

        // 4) Inicializar estado (por si había valores marcados por defecto)
        updateSelection();

        // 5) Cerrar el menú si se hace clic fuera del contenedor
        document.addEventListener('click', (e) => {
            if (!container.contains(e.target)) {
                menu.style.display = 'none';
            }
        });
    });
});
