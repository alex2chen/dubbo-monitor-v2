// http://eslint.org/docs/user-guide/configuring

module.exports = {
  root: true,
  parser: 'babel-eslint',
  parserOptions: {
    sourceType: 'module'
  },
  env: {
    browser: true,
  },
  // https://github.com/feross/standard/blob/master/RULES.md#javascript-standard-style
  extends: 'standard',
  // required to lint *.vue files
  plugins: [
    'html'
  ],
  // add your custom rules here
  'rules': {
    // allow paren-less arrow functions
    'arrow-parens': 0,
    // allow async-await
    'generator-star-spacing': 0,
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 2 : 0,
    'space-in-parens': [1, 'never'],//warning小括号里面不要有空格
    'spaced-comment': [1, 'always'],//warning行注释
    'space-before-function-paren': [0, 'always'],//忽略函数定义时括号前面不要有空格
    'semi': [0, 'always']//忽略语句强制分号结尾
  }
}
