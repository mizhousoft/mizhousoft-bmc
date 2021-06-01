import { combineReducers } from 'redux';
import accountReducer from '@/views/system/account/redux/accountReducer';

const rootReducer = combineReducers({
    accounts: accountReducer,
});

export default rootReducer;
