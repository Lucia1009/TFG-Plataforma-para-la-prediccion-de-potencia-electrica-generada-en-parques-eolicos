document.addEventListener('DOMContentLoaded', () => {
    const addLayerBtn     = document.getElementById('pr-add-layer-btn');
    const layersContainer = document.getElementById('pr-layers-container');
    const layerTemplate   = document.getElementById('pr-layer-template').content;

    function updateLayerIndices() {
        layersContainer.querySelectorAll('.pr-layer-form').forEach((row, idx) => {
            row.querySelector('input[name="layerName[]"]').placeholder = `Capa ${idx+1}`;
        });
    }

    addLayerBtn.addEventListener('click', () => {
        const cloneFrag = document.importNode(layerTemplate, true);
        // 1) Añadimos al DOM
        layersContainer.appendChild(cloneFrag);
        // 2) Recuperamos la fila recién añadida
        const newRow = layersContainer.lastElementChild;
        // 3) Ligamos el listener de eliminar
        newRow.querySelector('.pr-remove-layer-btn').addEventListener('click', () => {
            newRow.remove();
            updateLayerIndices();
        });
        // 4) Reindexamos
        updateLayerIndices();
    });
});
