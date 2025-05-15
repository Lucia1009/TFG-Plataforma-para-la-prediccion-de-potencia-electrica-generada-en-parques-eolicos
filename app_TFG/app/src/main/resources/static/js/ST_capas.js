document.addEventListener('DOMContentLoaded', () => {
    const addLayerBtn     = document.getElementById('st-add-layer-btn');
    const layersContainer = document.getElementById('st-layers-container');
    const layerTemplate   = document.getElementById('st-layer-template').content;

    function updateLayerIndices() {
        layersContainer.querySelectorAll('.st-layer-form').forEach((row, idx) => {
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
        newRow.querySelector('.st-remove-layer-btn').addEventListener('click', () => {
            newRow.remove();
            updateLayerIndices();
        });
        // 4) Reindexamos
        updateLayerIndices();
    });
});
