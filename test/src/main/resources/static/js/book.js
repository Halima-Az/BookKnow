
document.addEventListener("DOMContentLoaded", function () {
    const element = document.getElementById("typewriter");
    if (!element) return; // sécurité si l’élément n’existe pas

    const text = element.textContent.trim();  // récupère le texte Thymeleaf
    element.textContent = "";                 // efface le contenu

    let index = 0;
    function typeWriter() {
        if (index < text.length) {
            element.textContent += text.charAt(index); // ajoute une lettre
            index++;
            setTimeout(typeWriter, 120); // vitesse d'écriture
        }
    }

    typeWriter();
});

