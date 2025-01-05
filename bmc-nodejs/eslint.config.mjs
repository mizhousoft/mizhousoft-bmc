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
            'ts/no-require-imports': 'off',
            'ts/explicit-function-return-type': 'off',
            'ts/ban-ts-comment': 'off', // TODO: remove this
            'ts/consistent-type-definitions': 'off',
            'ts/consistent-type-imports': 'off', // TODO: remove this
            'ts/method-signature-style': 'off', // TODO: remove this
            'ts/no-non-null-asserted-optional-chain': 'off',
            'unicorn/prefer-number-properties': 'off',
            'unicorn/prefer-node-protocol': 'off',
            'unicorn/prefer-includes': 'off', // TODO: remove this
            'unicorn/no-new-array': 'off',
            'unicorn/prefer-string-starts-ends-with': 'off', // TODO: remove this
            'regexp/no-unused-capturing-group': 'off',
            'regexp/no-misleading-capturing-group': 'off',
            'regexp/no-super-linear-backtracking': 'off', // TODO: remove this
            'regexp/optimal-quantifier-concatenation': 'off',
            'test/prefer-lowercase-title': 'off',
            'test/prefer-hooks-in-order': 'off', // TODO: remove this
            'react-hooks/exhaustive-deps': 'off',
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
            /* turn off React 19 only rules */
            'react/no-forward-ref': 'off',
            'react/no-context-provider': 'off',
            'unused-imports/no-unused-vars': [2, {
                args: 'none'
            }],
            'node/handle-callback-err': 'off',
            'react-hooks/rules-of-hooks': 'off',
        },
    },
    compat.configs['flat/recommended'],
);
