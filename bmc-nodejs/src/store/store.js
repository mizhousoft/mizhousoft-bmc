import { configureStore } from '@reduxjs/toolkit';

import accountSlice from '@/views/system/account/redux/accountSlice';

export default configureStore({
    reducer: {
        accounts: accountSlice,
    },
});
