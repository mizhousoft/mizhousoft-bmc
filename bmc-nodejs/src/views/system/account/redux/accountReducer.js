import { DEFAULT_DATA_PAGE, LOADING_FETCH_STATUS } from '@/constants/common';
import ACCOUNT from './accountActionType';

const INIT_STATE = {
    fetchStatus: LOADING_FETCH_STATUS,
    dataSource: DEFAULT_DATA_PAGE,

    filter: {
        status: 0,
    },
};

function accountReducer(state = INIT_STATE, action) {
    const { type, payload, filter } = action;

    switch (type) {
        case ACCOUNT.FETCH_LIST:
            return { ...state, filter, fetchStatus: LOADING_FETCH_STATUS };
        case ACCOUNT.FETCH_LIST_SUCCESS:
            return { ...state, ...payload };
        case ACCOUNT.FETCH_LIST_FAILURE:
            return { ...state, ...payload };
        case ACCOUNT.ACTION:
            return { ...state, ...payload, fetchStatus: LOADING_FETCH_STATUS };
        case ACCOUNT.ACTION_FAILURE:
            return { ...state, ...payload };
        default:
            return state;
    }
}

export default accountReducer;
