export default {
    isDev() {
        return process.env.NODE_ENV === 'development';
    },

    isNumber(value) {
        const reg = /^\d+$/;
        return reg.test(value);
    },

    isPhoneNumber(value) {
        const reg = /^1((3\d)|(4[,05-9])|(5[,0-35-9])|(6[,56])|(7[0-8])|(8\d)|(9[,0135689]))\d{8}$/;
        return reg.test(value);
    },

    parseQuery(input) {
        input = input.trim().replace(/^[#&?]/, '');

        const ret = {};

        const params = input.split('&');
        if (undefined !== params && params.length > 0) {
            params.forEach((param) => {
                const [key, value] = param.replace(/\+/g, ' ').split('=');
                ret[key] = value;
            });
        }

        return ret;
    },

    bytesToSize(bytes) {
        if (bytes === 0) {
            return '0';
        }
        if (bytes < 1024) {
            return `${bytes}Byte`;
        }
        if (bytes < 1024 * 1024) {
            return `${parseFloat((bytes / 1024).toFixed(1))}KB`;
        }
        return `${parseFloat((bytes / 1024 / 1024).toFixed(2))}MB`;
    },

    timeToPlayFormat(time) {
        let t;

        if (time > -1) {
            const hour = Math.floor(time / 3600);
            const min = Math.floor(time / 60) % 60;
            const sec = time % 60;
            if (hour < 10) {
                t = `0${hour}:`;
            } else {
                t = `${hour}:`;
            }

            if (min < 10) {
                t += '0';
            }
            t += `${min}:`;
            if (sec < 10) {
                t += '0';
            }
            t += sec.toFixed(2);
        }

        t = t.substring(0, t.length - 3);
        return t;
    },

    tsFormat(ts) {
        if (ts === 0) {
            return '';
        }

        const date = new Date(ts);

        let month = date.getMonth() + 1;
        let day = date.getDate();
        let hour = date.getHours();
        let minute = date.getMinutes();
        let second = date.getSeconds();

        month = month < 10 ? `0${month}` : month;
        day = day < 10 ? `0${day}` : day;
        hour = hour < 10 ? `0${hour}` : hour;
        minute = minute < 10 ? `0${minute}` : minute;
        second = second < 10 ? `0${second}` : second;

        let strDate = `${date.getFullYear()}-`;
        strDate += `${month}-`;
        strDate += `${day} `;
        strDate += `${hour}:`;
        strDate += `${minute}:`;
        strDate += second;

        return strDate;
    },

    tsFormatYMDHM(ts) {
        if (ts === 0) {
            return '';
        }

        const date = new Date(ts);

        let month = date.getMonth() + 1;
        let day = date.getDate();
        let hour = date.getHours();
        let minute = date.getMinutes();

        month = month < 10 ? `0${month}` : month;
        day = day < 10 ? `0${day}` : day;
        hour = hour < 10 ? `0${hour}` : hour;
        minute = minute < 10 ? `0${minute}` : minute;

        let strDate = `${date.getFullYear()}-`;
        strDate += `${month}-`;
        strDate += `${day} `;
        strDate += `${hour}:`;
        strDate += minute;

        return strDate;
    },

    uuid() {
        const s = [];
        const hexDigits = '0123456789abcdef';
        for (let i = 0; i < 36; i++) {
            const start = Math.floor(Math.random() * 0x10);
            s[i] = hexDigits.substring(start, start + 1);
        }
        s[14] = '4'; // bits 12-15 of the time_hi_and_version field to 0010

        const start = Math.floor(Math.random() * 0x10);
        s[19] = hexDigits.substring(start, start + 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01

        s[8] = '-';
        s[13] = '-';
        s[18] = '-';
        s[23] = '-';

        return s.join('');
    },
};
