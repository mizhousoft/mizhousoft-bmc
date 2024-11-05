module.exports = {
    extends: [
        'airbnb',
        'prettier',
        'plugin:compat/recommended',
        'plugin:react/recommended',
        'plugin:import/typescript',
    ],
    env: {
        browser: true,
        es6: true,
    },
    settings: {
        react: {
            version: 'detect', // React version. "detect" automatically picks the version you have installed.
        },
        "import/resolver": {
            webpack: {
                config: 'webpack.config.js'
            }
        }
    },
    parser: '@typescript-eslint/parser',
    plugins: ['react', '@babel', '@typescript-eslint', 'react-hooks', 'unicorn'],
    overrides: [{
        files: ['*.js', '*.jsx', '*.ts', '*.tsx'],
        rules: {
            '@typescript-eslint/no-unused-vars': [2, { args: 'none' }],
            'no-unused-expressions': 'off',
            '@typescript-eslint/no-unused-expressions': 2,
            'class-methods-use-this': 0,
            'compat/compat': 0,
        },
    }],
    rules: {
        'react/jsx-one-expression-per-line': 0,
        'react/prop-types': 0,
        'react/forbid-prop-types': 0,
        'react/jsx-indent': 0,
        'react/jsx-wrap-multilines': ['error', { declaration: false, assignment: false }],
        'react/jsx-filename-extension': 0,
        'react/state-in-constructor': 0,
        'react/jsx-props-no-spreading': 0,
        'react/destructuring-assignment': 0, // TODO: remove later
        'react/require-default-props': 0,
        'react/sort-comp': 0,
        'react/display-name': 0,
        'react/static-property-placement': 0,
        'react/jsx-no-bind': 0, // Should not check test file
        'react/no-find-dom-node': 0,
        'react/no-unused-prop-types': 0,
        'react/default-props-match-prop-types': 0,
        'react-hooks/rules-of-hooks': 2, // Checks rules of Hooks
        'react/function-component-definition': 0,
        'react/no-unused-class-component-methods': 0,
        'react/no-unknown-property': ['error', { ignore: ['directory', 'webkitdirectory'] }],
        'import/extensions': 0,
        'import/no-cycle': 0,
        'import/no-extraneous-dependencies': [
            'error',
            {
                devDependencies: [
                    'site/**',
                    'tests/**',
                    'scripts/**',
                    '**/*.test.js',
                    '**/__tests__/*',
                    '*.config.js',
                    '**/*.md',
                ],
            },
        ],
        'jsx-a11y/no-static-element-interactions': 0,
        'jsx-a11y/anchor-has-content': 0,
        'jsx-a11y/click-events-have-key-events': 0,
        'jsx-a11y/anchor-is-valid': 0,
        'jsx-a11y/no-noninteractive-element-interactions': 0,
        // label-has-for has been deprecated
        // https://github.com/evcohen/eslint-plugin-jsx-a11y/blob/master/docs/rules/label-has-for.md
        'jsx-a11y/label-has-for': 0,

        'comma-dangle': ['error', {
            "arrays": 'always-multiline',
            "objects": 'always-multiline',
            "imports": 'always-multiline',
            "exports": 'always-multiline',
            "functions": "never"
        }],
        'consistent-return': 0, // TODO: remove later
        'no-param-reassign': 0, // TODO: remove later
        'no-underscore-dangle': 0,
        // for (let i = 0; i < len; i++)
        'no-plusplus': 0,
        // https://eslint.org/docs/rules/no-continue
        // labeledLoop is conflicted with `eslint . --fix`
        'no-continue': 0,
        // ban this for Number.isNaN needs polyfill
        'no-restricted-globals': 0,
        'max-classes-per-file': 0,

        'unicorn/better-regex': 2,
        'unicorn/prefer-string-trim-start-end': 2,
        'unicorn/expiring-todo-comments': 2,
        'unicorn/no-abusive-eslint-disable': 2,

        // https://github.com/typescript-eslint/typescript-eslint/issues/2540#issuecomment-692866111
        'no-use-before-define': 0,
        '@typescript-eslint/no-use-before-define': 2,
        'no-shadow': 0,
        '@typescript-eslint/no-shadow': [2, { ignoreTypeValueShadow: true }],
        // https://github.com/typescript-eslint/typescript-eslint/issues/2528#issuecomment-689369395
        'no-undef': 0,
        'no-unused-vars': [2, {
            args: 'none'
        }],
        'no-script-url': 0,
        'import/order': 0,
    },
    globals: {
        gtag: true,
    },
}
