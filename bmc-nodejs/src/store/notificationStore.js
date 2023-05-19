import { asyncFetch } from '@/utils/request';
import { BASENAME } from '@/config/application';
import DefaultUserStore from '@/store/DefaultUserStore';

const TODO_KEY = 'TODO_KEY';
const PUSHTIME_KEY = 'PUSHTIME_KEY';

let todos;
let pushTime = -1;

export default {
    getTodos() {
        if (undefined === todos) {
            const text = DefaultUserStore.getItem(TODO_KEY);
            if (text) {
                todos = JSON.parse(text);
            } else {
                todos = [];
            }
        }

        return todos;
    },

    getPushTime() {
        if (pushTime === -1) {
            const text = DefaultUserStore.getItem(PUSHTIME_KEY);
            if (text) {
                pushTime = parseInt(text, 10);
            } else {
                pushTime = 0;
            }
        }

        return pushTime;
    },

    asyncFetchData() {
        asyncFetch({
            url: `${BASENAME}/fetchNotifications.action`,
        }).then((response) => {
            if (response.todos) {
                todos = response.todos;

                DefaultUserStore.setItem(TODO_KEY, JSON.stringify(todos));
            }

            if (response.pushTime) {
                pushTime = response.pushTime;

                DefaultUserStore.setItem(PUSHTIME_KEY, `${pushTime}`);
            }
            else {
                pushTime = new Date().getTime();

                DefaultUserStore.setItem(PUSHTIME_KEY, `${pushTime}`);
            }
        });
    },
};
