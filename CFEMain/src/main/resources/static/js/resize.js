function autoResize(textarea) {
    textarea.style.height = 'auto';
    textarea.style.height = textarea.scrollHeight + 'px';
}

window.addEventListener('DOMContentLoaded', function() {
    const textarea = document.getElementById('code');
    autoResize(textarea);
});