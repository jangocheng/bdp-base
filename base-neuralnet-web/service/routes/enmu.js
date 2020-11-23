/**
 * 客户类型枚举
 */
const customerENUM = {
  '01': '内部员工',
  '02': '社会人士',
  '03': '所有',
  ALL: '03',
};

/**
 * 定义系统枚举
 * 与数据库表现一致
 */
const osTypeENUM = {
  '-1': '其他',
  '01': 'H5',
  '02': 'Android',
  '03': 'IOS',
  '04': 'Shop',
  '05': '所有',
};
const checkTypeENUM = {
  '01': '所有',
  '02': '系统审核通过',
  '03': '系统审核拒绝',
  '04': '人工审核通过',
  '05': '人工审核拒绝',
};

module.exports = { customerENUM, osTypeENUM, checkTypeENUM };
