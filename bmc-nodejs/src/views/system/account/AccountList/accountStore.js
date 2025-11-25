import { create } from 'zustand';

import { DEFAULT_DATA_PAGE, ERROR_FETCH_STATUS, LOADING_FETCH_STATUS } from '@/config/common';
import httpRequest from '@/utils/http-request';

const initialState = {
    fetchStatus: LOADING_FETCH_STATUS,
    dataSource: DEFAULT_DATA_PAGE,

    filter: {
        status: 0,
    },
};

const useAccountStore = create((set, get) => ({
    ...initialState,

    setLoading: () => {
        set({ fetchStatus: LOADING_FETCH_STATUS });
    },

    reset: () => {
        set(initialState);
    },

    fetchList: async (pageNumber, pageSize, filter) => {
        const requestBody = {
            url: '/account/fetchAccountInfoList.action',
            data: {
                pageNumber,
                pageSize,
                status: filter.status,
            },
        };

        set({ fetchStatus: LOADING_FETCH_STATUS, filter });

        try {
            const response = await httpRequest.get(requestBody);
            const { fetchStatus, dataPage = DEFAULT_DATA_PAGE } = response;

            set({
                fetchStatus,
                dataSource: dataPage,
            });

            return response;
        } catch (error) {
            set({ fetchStatus: ERROR_FETCH_STATUS });

            throw error;
        }
    },
}));

export default useAccountStore;
