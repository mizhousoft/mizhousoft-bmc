import mitt from 'mitt';

const emitter = mitt();

export function addEventListener(eventName, func) {
    emitter.on(eventName, func);
}

export function removeEventListener(eventName, func) {
    emitter.off(eventName, func);
}

export function emitEvent(eventName, params) {
    emitter.emit(eventName, params);
}
