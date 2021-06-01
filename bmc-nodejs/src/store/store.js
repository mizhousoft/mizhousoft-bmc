import { createStore, applyMiddleware } from 'redux';
import createSagaMiddleware from 'redux-saga';
import rootReducer from '@/reducers/rootReducer';
import rootSaga from '@/sagas/rootSaga';

const preloadedState = {};

const sagaMiddleware = createSagaMiddleware();

const store = createStore(rootReducer, preloadedState, applyMiddleware(sagaMiddleware));

sagaMiddleware.run(rootSaga);

export default store;
