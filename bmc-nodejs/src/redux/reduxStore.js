import accountSlice from '@/redux/accountSlice';
import { configureStore } from '@reduxjs/toolkit';

export default configureStore({
    reducer: {
        accounts: accountSlice,
    },
});
