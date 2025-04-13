// eslint.config.mjs
import antfu from '@antfu/eslint-config';
import compat from 'eslint-plugin-compat';

export default antfu(
    {
        ignores: [
            '**/node_modules/**',
            '**/dist/**',
            '**/*.md',
            'src/assets/iconfont/iconfont.js',
        ],
        settings: {
            polyfills: ['Promise', 'URL'],
        },
        languageOptions: {
            globals: {
                ENV_TEST_ADMIN: "readonly",
                ENV_TEST_PASSWORD: "readonly",
            },
        },
        stylistic: false,
        typescript: false,
        react: true,
        rules: {
            'node/prefer-global/process': 'off', // TODO: remove this
            'node/prefer-global/buffer': 'off', // TODO: remove this
            'jsdoc/empty-tags': 'off',
            'unicorn/prefer-number-properties': 'off',
            'unicorn/prefer-node-protocol': 'off',
            'unicorn/prefer-includes': 'off', // TODO: remove this
            'unicorn/no-new-array': 'off',
            'unicorn/prefer-string-starts-ends-with': 'off', // TODO: remove this
            'regexp/no-unused-capturing-group': 'off',
            'regexp/no-misleading-capturing-group': 'off',
            'regexp/no-super-linear-backtracking': 'off', // TODO: remove this
            'regexp/optimal-quantifier-concatenation': 'off',
            'react-hooks/exhaustive-deps': 'off',
            'react-hooks-extra/no-direct-set-state-in-use-effect': 'off',
            'react/prefer-destructuring-assignment': 'off', // TODO: remove this
            'react-refresh/only-export-components': 'off', // TODO: remove this
            'react/no-clone-element': 'off',
            'react/no-children-for-each': 'off',
            'react/no-children-count': 'off',
            'react/no-children-map': 'off',
            'react/no-children-only': 'off',
            'react/no-unstable-default-props': 'off',
            'react/no-create-ref': 'off', // TODO: remove this
            'perfectionist/sort-imports': 'off',
            'perfectionist/sort-exports': 'off',
            'perfectionist/sort-named-imports': 'off',
            'perfectionist/sort-named-exports': 'off',
            'regexp/strict': 'off',
            'unused-imports/no-unused-vars': [2, {
                args: 'none'
            }],
            'react-hooks/rules-of-hooks': 'warn',
        },
    },
    compat.configs['flat/recommended'],
);
