import mitt from 'mitt';

const emitter = mitt();

export default {
    addEventListener(eventName, func) {
        emitter.on(eventName, func);
    },

    removeEventListener(eventName, func) {
        emitter.off(eventName, func);
    },

    emitEvent(eventName, params) {
        emitter.emit(eventName, params);
    },
};
