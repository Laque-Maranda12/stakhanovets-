const API_URL = 'http://localhost:8080';

// --- Auth helpers ---
function getToken() {
    return localStorage.getItem('token');
}

function setToken(token) {
    localStorage.setItem('token', token);
}

function removeToken() {
    localStorage.removeItem('token');
}

function isLoggedIn() {
    return !!getToken();
}

async function apiFetch(path, options = {}) {
    const headers = options.headers || {};
    headers['Content-Type'] = 'application/json';
    const token = getToken();
    if (token) {
        headers['Authorization'] = 'Bearer ' + token;
    }
    options.headers = headers;
    const response = await fetch(API_URL + path, options);
    if (!response.ok) {
        const text = await response.text();
        throw new Error(text || ('HTTP ' + response.status));
    }
    const contentType = response.headers.get('content-type');
    if (contentType && contentType.includes('application/json')) {
        return response.json();
    }
    return null;
}

// --- Cart helpers ---
function getCart() {
    return JSON.parse(localStorage.getItem('cart') || '[]');
}

function addToCart(tool) {
    const cart = getCart();
    if (!cart.find(t => t.id === tool.id)) {
        cart.push({ id: tool.id, name: tool.name, pricePerDay: tool.pricePerDay, deposit: tool.deposit });
        localStorage.setItem('cart', JSON.stringify(cart));
    }
    updateCartBadge();
}

function removeFromCart(toolId) {
    let cart = getCart();
    cart = cart.filter(t => t.id !== toolId);
    localStorage.setItem('cart', JSON.stringify(cart));
    updateCartBadge();
}

function clearCart() {
    localStorage.removeItem('cart');
    updateCartBadge();
}

function updateCartBadge() {
    const badge = document.getElementById('cart-badge');
    if (badge) {
        const count = getCart().length;
        badge.textContent = count;
        badge.style.display = count > 0 ? 'inline' : 'none';
    }
}

// --- Nav auth state ---
async function updateNavAuth() {
    const authNav = document.getElementById('auth-nav');
    if (!authNav) return;

    if (isLoggedIn()) {
        try {
            const user = await apiFetch('/api/auth/me');
            authNav.innerHTML = `
                <span class="navbar-text text-light me-3"><i class="bi bi-person-circle"></i> ${user.fullName}</span>
                <a class="btn btn-outline-light btn-sm" href="#" onclick="logout(); return false;">Выйти</a>
            `;
        } catch (e) {
            removeToken();
            authNav.innerHTML = '<a class="btn btn-outline-light btn-sm" href="login.html">Войти</a>';
        }
    } else {
        authNav.innerHTML = '<a class="btn btn-outline-light btn-sm" href="login.html">Войти</a>';
    }
}

function logout() {
    removeToken();
    window.location.href = 'index.html';
}

// --- Init on every page ---
document.addEventListener('DOMContentLoaded', () => {
    updateCartBadge();
    updateNavAuth();
});
