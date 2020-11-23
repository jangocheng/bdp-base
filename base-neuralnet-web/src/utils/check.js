
class Check {
  /**
   *
   * 验证设备号是否正确
   * @param {String} IMEI 设备号
   */
  static isIMEI(IMEI) {
    const reg = /^\d{15}$/;
    return reg.test(IMEI);
  }
  /**
   * 验证手机号是否正确
   * @param {String} Iphone 手机号
   */
  static isPhone(phone) {
    const reg = /^1(3|4|5|7|8)\d{9}$/;
    return reg.test(phone);
  }
}

export default Check;
