document.addEventListener('DOMContentLoaded', () => {
    const addLayerBtn     = document.getElementById('pr-add-layer-btn');
    const layersContainer = document.getElementById('pr-layers-container');
    const layerTemplate   = document.getElementById('pr-layer-template').content;

    // 1) Primero, inyectamos dos capas “fijas”
    ['Entrada', 'Salida'].forEach(fixedName => {
        const clone = document.importNode(layerTemplate, true);
        const row   = clone.querySelector('tr');
        // 1.a) Fijar el nombre y hacerlo readonly
        const nameInput = row.querySelector('input[name="pr_layerName"]');
        nameInput.value    = fixedName;
        nameInput.readOnly = true;
        // 1.b) Ocultar el botón de “Eliminar”
        row.querySelector('.pr-remove-layer-btn').style.display = 'none';
        // 1.c) Añadir al contenedor
        layersContainer.appendChild(row);
    });

    // 2) Función para renumerar (solo dinámicas)
    function updateLayerIndices() {
        const dynamicRows = Array.from(layersContainer.querySelectorAll('.pr-layer-form'))
            .filter(row => !row.querySelector('input[name="pr_layerName"]').readOnly);

        dynamicRows.forEach((row, idx) => {
            row.querySelector('input[name="pr_layerName"]')
                .placeholder = `Capa ${idx + 1}`;
        });
    }

    // 3) Listener para añadir nuevas capas “dinámicas”
    addLayerBtn.addEventListener('click', () => {
        const clone = document.importNode(layerTemplate, true);
        const row   = clone.querySelector('tr');

        // enlazar remover
        row.querySelector('.pr-remove-layer-btn')
            .addEventListener('click', () => {
                row.remove();
                updateLayerIndices();
            });
        layersContainer.appendChild(row);
        updateLayerIndices();
    });

});
