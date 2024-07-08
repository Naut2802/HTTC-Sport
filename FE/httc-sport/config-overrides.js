const { override, useBabelRc } = require('customize-cra');

module.exports = override(
    /* eslint-disable react-hooks/rules-of-hooks */
    useBabelRc(),
);
