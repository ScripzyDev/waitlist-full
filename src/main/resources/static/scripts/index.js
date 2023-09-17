window.addEventListener('load', checkError());

function checkError() {
    if(document.getElementById("error-message").innerText != "") {
        togglePopup("error-container");
    }
}

function togglePopup(name) {
    var errorContainer = document.getElementById(name);
    if(errorContainer.classList.contains("toggle")) {
        errorContainer.classList.remove("toggle");
        document.body.style.overflow = "inherit";
    } else {
        errorContainer.classList.add("toggle");
        document.body.style.overflow = "hidden";
    }
}

function toggleFaq(id) {
    var active = document.getElementById("faq-answer-" + id).classList.contains("active");
    var answers = document.querySelectorAll(".faq-answer"); //hide all
    for (var i = 0; i < answers.length; i++) {
        answers[i].classList.add("instant-transition");
        answers[i].classList.remove('active');
    }

    var images = document.querySelectorAll(".faq-img"); // dropup image for all
    for (var i = 0; i < images.length; i++) {
        images[i].src= "images/dropdown.png"
    }

    var hr = document.querySelectorAll(".hr"); // dropup image for all
    for (var i = 0; i < hr.length; i++) {
        hr[i].classList.remove("active")
    }

    setTimeout(function() {
        for (var i = 0; i < answers.length; i++) {
            answers[i].classList.remove("instant-transition");
        }
        if(!active) {
            document.getElementById("faq-answer-" + id).classList.add("active");
            document.getElementById("faq-img-" + id).src= "images/dropup-active.png"
            document.getElementById("hr-" + id).classList.add("active");
        }
    }, 10);
}