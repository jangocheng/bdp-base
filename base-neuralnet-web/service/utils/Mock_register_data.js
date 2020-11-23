/*

 *
 *  Mock文件
 *
 */
const Mock = require('mockjs');
var Random = Mock.Random;

class MOCK {
  /**
   * Mock 实时注册数据--渠道注册报表
   */
  static MockDate() {
    return Mock.mock({
      success: true,
      code: 200,
      msg: 'request successful',
      // 属性 list 的值是一个数组，其中含有 1 到 10 个元素
      'data|23': [
        {
          date: Random.date('yyyy-MM-dd'), //时间
          applyAmount: '1000', //申请件数
          applyApprovedAmount: '300', //申请通过数
          accessFirstTrialAmount: '100', //准入审核-初审数
          returnSupplementaryAmount: '600', //退回补充资料数
          accessApplyAmount: '3432', //准入申请
          approvalRefuseAmount: '20', //审批拒绝数
          firstTrialAmount: '1000283.23', //初审数
          closeAmount: '1233.33', //关闭数
          activationAmount: '80', //激活数
          refuseAmount: '124324.22', //拒绝数
          newAmount: '124324', //新增数
          accessAmount: '124324.344', //准入数
          applyTotalAmount: '1000', //积累申请件数
          applyApprovedTotalAmount: '300', //累积申请通过数
          accessFirstTrialTotalAmount: '100', //累积准入审核-初审数
          returnSupplementaryTotalAmount: '600', //积累退回补充资料数
          accessApplyTotalAmount: '3432', //积累准入申请
          approvalRefuseTotalAmount: '20', //积累审批拒绝数
          firstTrialTotalAmount: '1000283.23', //积累初审数
          closeTotalAmount: '1233.33', //积累关闭数
          activationTotalAmount: '80', //积累激活数
          refuseTotalAmount: '124324.22', //积累拒绝数
          newTotalAmount: '124324', //积累新增数
          accessTotalAmount: '124324.344', //积累准入数
        },
      ],
    });
  }

  /**
   * 申请数据
   */
  // static getRegisterData() {
  //   let data = Mock.mock({
  //     success: true,
  //     code: 200,
  //     msg: 'request successful',
  //     // 属性 list 的值是一个数组，其中含有 1 到 10 个元素
  //     'data|21': [
  //       {
  //         'id|+1': 1,
  //         date: '@date',
  //         'registerAmount|4000-5000': '1', //注册量
  //         'identifiedAmount|3000-4000': 1, //申请总件数
  //         'applyApprovedAmount|3000-4000': 1, //申请通过件数
  //         'applyRefusedAmount|1-300': 1, //申请拒件
  //         'applyCancelledAmount|150-200': 1, //申请取消
  //         'applyApprovingAmount|450-500': 1, //申请审核中
  //       },
  //     ],
  //   });
  //   return data;
  // }
}

module.exports = MOCK;
