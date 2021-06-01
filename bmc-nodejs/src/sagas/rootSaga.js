import { spawn } from 'redux-saga/effects';
import accountSaga from '@/views/system/account/redux/accountSaga';

function* rootSaga() {
    yield spawn(accountSaga);
}

export default rootSaga;
