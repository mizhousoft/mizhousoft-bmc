export function checkNewPassword(rule, value) {
    if (value) {
        if (!/[a-z]/.test(value) || !/[A-Z]/.test(value) || !/\d/.test(value) || !/[!#$%&()*+,.:;<=>?@^_-]/.test(value)) {
            return Promise.reject(
                new Error('密码至少包括一个大写字符(A-Z)，一个小写字母(a-z)，一个数字字符，一个特殊字符!@#$%^&*()+=.,<>?:;_-')
            );
        }
    }

    return Promise.resolve();
}

export function checkConfirmPassword(rule, value, form) {
    if (value && value !== form.getFieldValue('password')) {
        return Promise.reject(new Error('密码和确认密码不一样'));
    }

    return Promise.resolve();
}
