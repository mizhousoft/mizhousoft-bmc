import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';

import App from './container/App';
import store from './store/store';

const container = document.getElementById('root');

const root = ReactDOM.createRoot(container);
root.render(
    <React.StrictMode>
        <Provider store={store}>
            <App />
        </Provider>
    </React.StrictMode>
);
