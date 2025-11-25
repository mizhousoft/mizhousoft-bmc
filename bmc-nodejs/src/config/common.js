export const SMALL_PAGE_SIZE = 5;

export const DEFAULT_PAGE_SIZE = 10;

export const SMALL_DATA_PAGE = {
    content: [],
    pageNumber: 1,
    pageSize: SMALL_PAGE_SIZE,
    totalNumber: 0,
    totalPage: 0,
};

export const DEFAULT_DATA_PAGE = {
    content: [],
    pageNumber: 1,
    pageSize: DEFAULT_PAGE_SIZE,
    totalNumber: 0,
    totalPage: 0,
};

export const LOADING_FETCH_STATUS = {
    loading: true,
    okey: true,
    statusCode: 200,
    message: undefined,
};

export const SUCCEED_FETCH_STATUS = {
    loading: false,
    okey: true,
    statusCode: 200,
    message: undefined,
};

export const ERROR_FETCH_STATUS = {
    loading: false,
    okey: false,
    statusCode: 501,
    message: 'º”‘ÿ ˝æ› ß∞‹',
};
