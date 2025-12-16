document.addEventListener('DOMContentLoaded', function() {
    // Section scroll variables
    let isScrolling = false;
    let currentSection = 0;
    const sections = document.querySelectorAll('section');
    const totalSections = sections.length;
    let scrollTimeout;

    // Function to scroll to section
    function scrollToSection(index) {
        if (index < 0 || index >= totalSections) return;
        
        currentSection = index;
        isScrolling = true;
        
        window.scrollTo({
            top: sections[index].offsetTop,
            behavior: 'smooth'
        });
        
        // Reset scrolling flag after animation completes
        clearTimeout(scrollTimeout);
        scrollTimeout = setTimeout(() => {
            isScrolling = false;
        }, 1000);
    }

    // Handle wheel event for section scrolling
    window.addEventListener('wheel', function(e) {
        if (isScrolling) {
            e.preventDefault();
            return;
        }

        const delta = e.deltaY;
        
        // Only proceed for significant scrolls
        if (Math.abs(delta) < 50) return;
        
        e.preventDefault();
        
        if (delta > 0 && currentSection < totalSections - 1) {
            // Scroll down to next section
            scrollToSection(currentSection + 1);
        } else if (delta < 0 && currentSection > 0) {
            // Scroll up to previous section
            scrollToSection(currentSection - 1);
        }
    }, { passive: false });
    
    // Update current section on manual scroll
    window.addEventListener('scroll', function() {
        if (isScrolling) return;
        
        const scrollPosition = window.scrollY + (window.innerHeight / 3);
        
        sections.forEach((section, index) => {
            const sectionTop = section.offsetTop;
            const sectionBottom = sectionTop + section.offsetHeight;
            
            if (scrollPosition >= sectionTop && scrollPosition < sectionBottom) {
                currentSection = index;
            }
        });
    });
    // Loading Screen
    const loadingScreen = document.createElement('div');
    loadingScreen.className = 'loading';
    loadingScreen.innerHTML = '<div class="spinner"></div>';
    document.body.prepend(loadingScreen);

    // Hide loading screen after page loads
    window.addEventListener('load', function() {
        setTimeout(function() {
            loadingScreen.classList.add('hidden');
            // Remove loading screen from DOM after animation completes
            setTimeout(() => {
                loadingScreen.remove();
            }, 500);
        }, 1000);
    });

    // Navbar scroll effect
    const navbar = document.querySelector('nav');
    let lastScroll = 0;

    window.addEventListener('scroll', function() {
        const currentScroll = window.pageYOffset;
        
        // Add/remove scrolled class based on scroll position
        if (currentScroll > 50) {
            navbar.classList.add('nav-scrolled');
        } else {
            navbar.classList.remove('nav-scrolled');
        }
        
        // Hide/show navbar on scroll
        if (currentScroll <= 0) {
            navbar.style.transform = 'none';
            return;
        }
        
        if (currentScroll > lastScroll && currentScroll > 100) {
            // Scrolling down
            navbar.style.transform = 'translateY(-100%)';
        } else {
            // Scrolling up
            navbar.style.transform = 'none';
        }
        
        lastScroll = currentScroll;
    });

    // Mobile menu toggle
    const menuButton = document.querySelector('.md\:hidden');
    const navMenu = document.querySelector('.md\:flex');
    
    if (menuButton) {
        menuButton.addEventListener('click', function() {
            navMenu.classList.toggle('hidden');
            navMenu.classList.toggle('flex');
            navMenu.classList.toggle('flex-col');
            navMenu.classList.toggle('absolute');
            navMenu.classList.toggle('top-16');
            navMenu.classList.toggle('left-0');
            navMenu.classList.toggle('right-0');
            navMenu.classList.toggle('bg-white');
            navMenu.classList.toggle('p-4');
            navMenu.classList.toggle('shadow-lg');
        });
    }

    // Smooth scrolling for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            
            const targetId = this.getAttribute('href');
            if (targetId === '#') return;
            
            const targetElement = document.querySelector(targetId);
            if (targetElement) {
                // Close mobile menu if open
                if (!navMenu.classList.contains('hidden')) {
                    menuButton.click();
                }
                
                window.scrollTo({
                    top: targetElement.offsetTop - 80,
                    behavior: 'smooth'
                });
            }
        });
    });

    // Back to top button
    const backToTopButton = document.getElementById('back-to-top');
    
    window.addEventListener('scroll', function() {
        if (window.pageYOffset > 300) {
            backToTopButton.classList.add('visible');
            backToTopButton.classList.remove('hidden');
        } else {
            backToTopButton.classList.remove('visible');
            backToTopButton.classList.add('hidden');
        }
    });
    
    backToTopButton.addEventListener('click', function(e) {
        e.preventDefault();
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    });

    // Form validation
    const contactForm = document.querySelector('#contact form');
    if (contactForm) {
        contactForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Simple validation
            let isValid = true;
            const nameInput = document.getElementById('name');
            const emailInput = document.getElementById('email');
            const subjectInput = document.getElementById('subject');
            const messageInput = document.getElementById('message');
            
            // Reset previous error states
            [nameInput, emailInput, subjectInput, messageInput].forEach(input => {
                input.classList.remove('error');
                const errorMessage = input.nextElementSibling;
                if (errorMessage && errorMessage.classList.contains('error-message')) {
                    errorMessage.style.display = 'none';
                }
            });
            
            // Validate name
            if (!nameInput.value.trim()) {
                showError(nameInput, '이름을 입력해주세요.');
                isValid = false;
            }
            
            // Validate email
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailInput.value.trim()) {
                showError(emailInput, '이메일을 입력해주세요.');
                isValid = false;
            } else if (!emailRegex.test(emailInput.value.trim())) {
                showError(emailInput, '유효한 이메일 주소를 입력해주세요.');
                isValid = false;
            }
            
            // Validate subject
            if (!subjectInput.value.trim()) {
                showError(subjectInput, '제목을 입력해주세요.');
                isValid = false;
            }
            
            // Validate message
            if (!messageInput.value.trim()) {
                showError(messageInput, '메시지를 입력해주세요.');
                isValid = false;
            }
            
            // If form is valid, you can submit it
            if (isValid) {
                // Here you would typically send the form data to a server
                alert('메시지가 성공적으로 전송되었습니다!');
                contactForm.reset();
            }
        });
    }
    
    function showError(input, message) {
        input.classList.add('error');
        let errorMessage = input.nextElementSibling;
        
        // Create error message element if it doesn't exist
        if (!errorMessage || !errorMessage.classList.contains('error-message')) {
            errorMessage = document.createElement('div');
            errorMessage.className = 'error-message';
            input.parentNode.insertBefore(errorMessage, input.nextSibling);
        }
        
        errorMessage.textContent = message;
        errorMessage.style.display = 'block';
    }
    
    // Animate elements on scroll
    const animateOnScroll = function() {
        const elements = document.querySelectorAll('.animate-on-scroll');
        
        elements.forEach(element => {
            const elementTop = element.getBoundingClientRect().top;
            const windowHeight = window.innerHeight;
            
            if (elementTop < windowHeight - 100) {
                element.classList.add('animate-fadeInUp');
            }
        });
    };
    
    // Add animation class to elements that should be animated
    document.querySelectorAll('section').forEach((section, index) => {
        if (index > 0) { // Skip the first section (hero)
            section.classList.add('opacity-0', 'animate-on-scroll');
        }
    });
    
    // Initial check for elements in viewport
    animateOnScroll();
    
    // Check for elements in viewport on scroll
    window.addEventListener('scroll', animateOnScroll);
});
