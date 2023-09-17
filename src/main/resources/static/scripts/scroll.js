const fadeInElements = document.querySelectorAll('.fade-in');

window.addEventListener('scroll', checkVisibility);

const fadeInOptions = {
    rootMargin: "0px",
    threshold: 0.2
};

const fadeInObserver = new IntersectionObserver(function(entries, fadeInObserver) {
    entries.forEach(entry => {
        if (!entry.isIntersecting) {
            return;
        } else {
            entry.target.classList.add('show');
            fadeInObserver.unobserve(entry.target);
        }
    });
}, fadeInOptions);

fadeInElements.forEach(fadeInElement => {
    fadeInObserver.observe(fadeInElement);
});

function checkVisibility() {
    fadeInElements.forEach((element) => {
        const elementTop = element.getBoundingClientRect().top;
        const elementBottom = element.getBoundingClientRect().bottom;
    
        // If the element is in the viewport, add the "is-visible" class
        if (elementTop < window.innerHeight && elementBottom > 0) {
            element.classList.add('show');
        } else {
            element.classList.remove('show');
        }
    });
}