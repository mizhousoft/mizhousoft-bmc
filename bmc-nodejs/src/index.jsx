import React from 'react';
import { createRoot } from 'react-dom/client';
import { Provider } from 'react-redux';

import App from './container/App';
import reduxStore from './redux/reduxStore';

const container = document.getElementById('root');

const root = createRoot(container);
root.render(
    <React.StrictMode>
        <Provider store={reduxStore}>
            <App />
        </Provider>
    </React.StrictMode>
);
