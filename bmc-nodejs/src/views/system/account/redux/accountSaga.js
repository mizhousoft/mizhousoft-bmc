import { put, takeLatest } from 'redux-saga/effects';
import { BASENAME } from '@/config/application';
import { get } from '@/utils/request';
import { DEFAULT_DATA_PAGE } from '@/constants/common';
import ACCOUNT from './accountActionType';

function* fetchAccountInfoList(action) {
    const url = `${BASENAME}/account/fetchAccountInfoList.action`;
    const { payload } = action;

    const result = yield get({
        url,
        data: payload,
    });

    yield put({
        type: ACCOUNT.FETCH_LIST_SUCCESS,
        payload: {
            fetchStatus: result.fetchStatus,
            dataSource: result.dataPage ?? DEFAULT_DATA_PAGE,
        },
    });
}

/** ***************************** WATCHERS ************************************ */

function* accountSaga() {
    yield takeLatest(ACCOUNT.FETCH_LIST, fetchAccountInfoList);
}

export default accountSaga;
