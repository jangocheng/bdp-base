const mathjs = require('mathjs');

/**
 * 用来求百分比
 * @param numerator 除数
 * @param denominator 被除数
 * @returns {string}
 */
const division = ({ numerator, denominator }) => {
  try {
    if (denominator === 0) return '0.00%';
    return mathjs.eval(`${numerator}/${denominator} * 100`).toFixed(2) + '%';
  } catch (error) {
    console.log(error);
    return '0.00%';
  }
};

module.exports = {
  division,
};
