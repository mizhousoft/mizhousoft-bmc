import { createSlice } from '@reduxjs/toolkit';

import { DEFAULT_DATA_PAGE, LOADING_FETCH_STATUS, SUCCEED_FETCH_STATUS } from '@/config/common';

const accountSlice = createSlice({
    name: 'account',
    initialState: {
        fetchStatus: LOADING_FETCH_STATUS,
        dataSource: DEFAULT_DATA_PAGE,

        filter: {
            status: 0,
        },
    },
    reducers: {
        fetchEvent: (state, { payload }) => {
            state.fetchStatus = LOADING_FETCH_STATUS;
            state.filter = payload.filter;
        },
        fetchResultEvent: (state, { payload }) => {
            state.fetchStatus = payload.fetchStatus;
            state.dataSource = payload.dataSource;
        },
        actionEvent: (state, { payload }) => {
            state.fetchStatus = LOADING_FETCH_STATUS;
        },
        actionResultEvent: (state, { payload }) => {
            state.fetchStatus = SUCCEED_FETCH_STATUS;
        },
    },
});

export const { fetchEvent, fetchResultEvent, actionEvent, actionResultEvent } = accountSlice.actions;

export default accountSlice.reducer;
