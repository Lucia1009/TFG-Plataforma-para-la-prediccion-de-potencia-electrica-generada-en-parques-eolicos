// static/js/rf.js
document.addEventListener('DOMContentLoaded', () => {
    const splitAxis      = document.getElementById('rf-split-axis');
    const sparseNormCont = document.getElementById('sparseNormContainer');
    const sparseNorm     = document.getElementById('rf-sparse-norm');

    if (!splitAxis) return;  // no estamos en RF

    function toggleSparseNorm() {
        if (splitAxis.value === 'SPARSE_OBLIQUE') {
            sparseNormCont.style.display = '';
        } else {
            sparseNormCont.style.display = 'none';
            sparseNorm.value = 'NONE';
        }
    }

    // listener y estado inicial
    splitAxis.addEventListener('change', toggleSparseNorm);
    toggleSparseNorm();
});
