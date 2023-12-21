export default {
    getItem(key) {
        return localStorage.getItem(key);
    },

    setItem(key, value) {
        localStorage.setItem(key, value);
    },

    removeItem(key) {
        localStorage.removeItem(key);
    },

    clearAll() {
        localStorage.clear();
    },
};
