export default function PasswordNote({ charAppearSize, historyRepeatSize }) {
    return (
        <ol>
            <li>密码不能包含帐号名或倒序的帐号名，同一字符不能出现超过{charAppearSize}次。</li>
            <li>密码至少包括一个大写字符(A-Z)，一个小写字母(a-z)，一个数字字符，一个特殊字符~!@#$%^&*()_-+=。</li>
            <li>不能与最近的{historyRepeatSize}个历史密码重复。</li>
        </ol>
    );
}
