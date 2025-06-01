document.addEventListener('DOMContentLoaded', () => {
    const dropZone = document.getElementById('dropZone');
    const fileInput = document.getElementById('file_upload');
    const fileNameDisplay = document.getElementById('fileNameDisplay');
    const mensajeUpload=document.getElementById('mensaje_upload');

    function showFileName(file) {
        mensajeUpload.textContent = `Archivo seleccionado: ${file.name}`;
        mensajeUpload.title = `Archivo seleccionado: ${file.name}`;
    }


    dropZone.addEventListener('dragover', (e) => {
        e.preventDefault();
        dropZone.classList.add('dragover');
    });

    dropZone.addEventListener('dragleave', () => {
        dropZone.classList.remove('dragover');
    });

    dropZone.addEventListener('drop', (e) => {
        e.preventDefault();
        dropZone.classList.remove('dragover');

        const files = e.dataTransfer.files;
        if (files.length > 0) {
            const file = files[0];
            if (file.name.endsWith('.csv')) {
                fileInput.files = e.dataTransfer.files;
                showFileName(file);
            } else {
                alert("Por favor, sube un archivo .csv");
            }
        }
    });

    fileInput.addEventListener('change', () => {
        if (fileInput.files.length > 0) {
            const file = fileInput.files[0];
            showFileName(file);
        }else{
            mensajeUpload.textContent = `Arrastra y suelta tu archivo .csv aquí`;
        }
    });
});


document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');
    const spinner = document.getElementById('loadingSpinner');

    form.addEventListener('submit', function () {
        spinner.classList.remove('hidden');
    });

    // Si aparece el modal, oculta el spinner
    const overlay = document.getElementById('modalOverlay');
    if (overlay) {
        spinner.classList.add('hidden');
    }
});
