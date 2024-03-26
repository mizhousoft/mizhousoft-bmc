import { configureStore } from '@reduxjs/toolkit';

import accountSlice from '@/redux/accountSlice';

export default configureStore({
    reducer: {
        accounts: accountSlice,
    },
});
