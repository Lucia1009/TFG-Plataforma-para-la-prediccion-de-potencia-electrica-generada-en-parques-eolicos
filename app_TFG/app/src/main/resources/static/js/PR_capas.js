document.addEventListener('DOMContentLoaded', () => {
    const addLayerBtn     = document.getElementById('add-layer-btn');
    const layersContainer = document.getElementById('layers-container');
    const layerTemplate   = document.getElementById('layer-template').content;

    function updateLayerIndices() { /* igual que antes */ }

    addLayerBtn.addEventListener('click', () => {
        const cloneFrag = document.importNode(layerTemplate, true);
        // 1) Añadimos al DOM
        layersContainer.appendChild(cloneFrag);
        // 2) Recuperamos la fila recién añadida
        const newRow = layersContainer.lastElementChild;
        // 3) Ligamos el listener de eliminar
        newRow.querySelector('.remove-layer-btn').addEventListener('click', () => {
            newRow.remove();
            updateLayerIndices();
        });
        // 4) Reindexamos
        updateLayerIndices();
    });
});
