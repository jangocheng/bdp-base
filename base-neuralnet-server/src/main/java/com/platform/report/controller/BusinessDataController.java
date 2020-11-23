package com.platform.report.controller;

import com.platform.report.common.constant.BiCommon;
import com.platform.report.common.constant.ResponseCode;
import com.platform.report.common.entity.MessageTO;
import com.platform.report.common.utils.ResponseUtils;
import com.platform.report.common.utils.StringUtil;
import com.platform.report.controller.mo.BusinessPopularizeChannelReqMO;
import com.platform.report.controller.mo.BusinessPWReqMO;
import com.platform.report.service.MerchantStoreService;
import com.platform.report.service.PaymentWithdrawService;
import com.platform.report.service.PopularizeService;
import com.platform.report.service.RegisterApplyService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/business")
@Log4j2
public class BusinessDataController {

    @Resource
    private PopularizeService popularizeService;

    @Resource
    private RegisterApplyService registerApplyService;

    @Resource
    private PaymentWithdrawService paymentWithdrawService;

    @Resource
    private MerchantStoreService merchantStoreService;

    @ApiOperation(value = "查询进注册数据")
    @GetMapping("/register_data/{type}/{start_date}/{end_date}")
    public MessageTO getRegisterReport(@PathVariable("type") String type, @PathVariable("start_date") String start, @PathVariable("end_date") String end) {
        MessageTO message;
        switch (type.trim()) {
            case "hour":
                message = registerApplyService.getRegisterChannelByHour(start.trim(), end.trim());
                break;
            case "day":
                message = registerApplyService.getRegisterDataByDay(start.trim(), end.trim());
                break;
            default:
                message = ResponseUtils.failure(ResponseCode.BAD_REQUEST, "请求参数type错误，只提供时间粒度为：hour,day,week,month的数据");
        }
        return message;
    }

    @ApiOperation(value = "获取所有渠道列表--根据注册量排序")
    @GetMapping("/register/channels")
    public MessageTO getRegisterChannels(@RequestParam(value = "start_date", required = false) String start, @RequestParam(value = "end_date", required = false) String end) {
        return registerApplyService.getRegisterChannels(start, end);
    }

    @ApiOperation(value = "查询进件数据")
    @GetMapping("/apply_data/{type}/{start_date}/{end_date}")
    public MessageTO getApplyReport(@PathVariable("type") String type, @PathVariable("start_date") String start, @PathVariable("end_date") String end) {
        MessageTO message;
        switch (type.trim()) {
            case "hour":
                message = registerApplyService.getApplyChannelByHour(start.trim(), end.trim());
                break;
            case "day":
                message = registerApplyService.getApplyDataByDay(start.trim(), end.trim());
                break;
            default:
                message = ResponseUtils.failure(ResponseCode.BAD_REQUEST, "请求参数type错误，只提供时间粒度为：hour,day,week,month的数据");
        }
        return message;
    }

    @ApiOperation(value = "获取所有渠道列表--根据进件量排序")
    @GetMapping("/apply/channels")
    public MessageTO getApplyChannels(@RequestParam(value = "start_date", required = false) String start, @RequestParam(value = "end_date", required = false) String end) {
        return registerApplyService.getApplyChannels(start, end);
    }


    @ApiOperation(value = "查询推广引流申请进件报告数据")
    @GetMapping("/popularize/apply/{type}/{start_date}/{end_date}")
    public MessageTO getPopularizeApplyReport(@PathVariable("type") String type, @PathVariable("start_date") String start, @PathVariable("end_date") String end) {
        MessageTO message;
        switch (type) {
            case "hour":
                message = popularizeService.getStreamChannelByHour(start.trim(), end.trim());
                break;
            case "day":
                message = popularizeService.getStreamApplyDataByDay(start.trim(), end.trim());
                break;
            default:
                message = ResponseUtils.failure(ResponseCode.BAD_REQUEST, "请求参数type错误，只提供时间粒度为：hour,day,week,month的数据");
        }
        return message;
    }

    @ApiOperation(value = "查询推广引流消费数据")
    @GetMapping("/popularize/consumption")
    public MessageTO getPopularizeConsumption(@RequestParam(value = "channel", required = false) String channel,
                                              @RequestParam("start_date") String start, @RequestParam("end_date") String end) {
        return popularizeService.getStreamConsumptionData(StringUtil.getValidValue(channel), start.trim(), end.trim());
    }


    @ApiOperation(value = "查询推广引流门店数据")
    @GetMapping("/popularize/store")
    public MessageTO getPopularizeStore(@RequestParam(value = "channel", required = false) String channel,
                                        @RequestParam("start_date") String start, @RequestParam("end_date") String end) {
        return popularizeService.getStreamStoreData(StringUtil.getValidValue(channel), start.trim(), end.trim());
    }

    @ApiOperation(value = "查询推广引流品牌数据")
    @GetMapping("/popularize/brand")
    public MessageTO getPopularizeBrand(@RequestParam(value = "channel", required = false) String channel,
                                        @RequestParam("start_date") String start, @RequestParam("end_date") String end) {
        return popularizeService.getStreamBrandData(StringUtil.getValidValue(channel), start.trim(), end.trim());

    }

    @ApiOperation(value = "业务大盘数据---推广渠道-报表")
    @GetMapping("/popularize/{type}/{start_date}/{end_date}")
    public MessageTO getPopularizeChannelReport(@PathVariable("type") String type, @PathVariable("start_date") String start, @PathVariable("end_date") String end) {
        MessageTO message;
        switch (type) {
            case "day":
                message = popularizeService.getChannelReport(start, end);
                break;
            case "hour":
                message = ResponseUtils.failure(ResponseCode.BAD_REQUEST, "请求参数type错误，暂时没有提供时间粒度为：hour的数据");
                break;

            default:
                message = ResponseUtils.failure(ResponseCode.BAD_REQUEST, "请求参数type错误，只提供时间粒度为：day的数据");
        }
        return message;
    }

    @ApiOperation(value = "业务大盘数据-推广渠道-汇总数据")
    @PostMapping("/popularize/channel/summary")
    public MessageTO getPWRateProduct(@RequestBody @Validated BusinessPopularizeChannelReqMO reqVO) {
        String summaryKey = reqVO.getSummaryField();
        String channel = StringUtil.getValidValue(reqVO.getChannel());
        List<Map<String, Object>> data;

        try {
            switch (summaryKey) {
                case BiCommon.SUMMARY_EDU_EXPERIENCE_KEY:
                    data = popularizeService.getEduSummary(channel, reqVO.getStartDate().trim(), reqVO.getEndDate().trim());
                    break;
                case BiCommon.SUMMARY_AGE_KEY:
                    data = popularizeService.getAgeSummary(channel, reqVO.getStartDate().trim(), reqVO.getEndDate().trim());
                    break;
                case BiCommon.SUMMARY_GENDER_KEY:
                    data = popularizeService.getGenderSummary(channel, reqVO.getStartDate().trim(), reqVO.getEndDate().trim());
                    break;
                case BiCommon.SUMMARY_PROVINCE_KEY:
                    data = popularizeService.getProvinceSummary(channel, reqVO.getStartDate().trim(), reqVO.getEndDate().trim());
                    break;
                case BiCommon.SUMMARY_CUSTOMER_LEVEL:
                    data = popularizeService.getCustomerLevelSummary(channel, reqVO.getStartDate().trim(), reqVO.getEndDate().trim());
                    break;
                case BiCommon.SUMMARY_LOAN_PERIOD:
                    data = popularizeService.getLoanPeriodSummary(channel, reqVO.getStartDate().trim(), reqVO.getEndDate().trim());
                    break;
                case BiCommon.SUMMARY_MOBILE_MODEL_KEY:
                    data = popularizeService.getMobileModelSummary(channel, reqVO.getStartDate().trim(), reqVO.getEndDate().trim());
                    break;
                default:
                    log.error("请求参数summaryField错误,summaryField:{}", summaryKey);
                    return ResponseUtils.failure(ResponseCode.BAD_REQUEST, "请求参数summaryField错误，汇总字段只能为：mobileModel--手机机型；" +
                            "gender ---性别；age -- 年龄；" + "province --- 省份" + " customerLevel -- 客户等级" + " loanPeriod -- 贷款期数" +
                            "educationalBackground -- 学历");
            }
        } catch (Exception e) {
            log.error("查询推广渠" + summaryKey + "汇总失败，请求参数：{},错误：{}", reqVO, e);
            return ResponseUtils.failure("查询推广渠道" + summaryKey + "汇总失败,请及时联系管理员！！");
        }

        Map<String, Long> rows = uniformSummaryResult(data);

        return ResponseUtils.success(rows);
    }


    @ApiOperation(value = "查询支付提现(paymentWithdraw)-交易笔数")
    @PostMapping("/pw/consumption/amount")
    public MessageTO getPWConsumptionAmount(@RequestBody @Validated BusinessPWReqMO pwReqVO) {
        return paymentWithdrawService.getConsumptionAmount(pwReqVO);
    }

    @ApiOperation(value = "查询支付提现(paymentWithdraw)-交易金额")
    @PostMapping("/pw/consumption/sum")
    public MessageTO getPWConsumptionSum(@RequestBody @Validated BusinessPWReqMO pwReqVO) {
        return paymentWithdrawService.getConsumptionSum(pwReqVO);
    }

    @ApiOperation(value = "查询支付提现(paymentWithdraw)-最大单笔交易金额")
    @PostMapping("/pw/consumption/maximum")
    public MessageTO getPWMaximumConsumption(@RequestBody @Validated BusinessPWReqMO pwReqVO) {
        return paymentWithdrawService.getMaximumConsumption(pwReqVO);
    }

    @ApiOperation(value = "查询支付提现(paymentWithdraw)-笔均交易金额")
    @PostMapping("/pw/consumption/average")
    public MessageTO getPWAvgConsumption(@RequestBody @Validated BusinessPWReqMO pwReqVO) {
        return paymentWithdrawService.getAvgConsumption(pwReqVO);
    }

    @ApiOperation(value = "查询支付提现(paymentWithdraw)-支付类型占比数据")
    @PostMapping("/pw/rate/paytype")
    public MessageTO getPWRatePayType(@RequestBody @Validated BusinessPWReqMO pwReqVO) {
        return paymentWithdrawService.getPWRatePayType(pwReqVO);
    }

    @ApiOperation(value = "查询支付提现(paymentWithdraw)-交易笔数数据")
    @PostMapping("/pw/rate/product")
    public MessageTO getPWRateProduct(@RequestBody @Validated BusinessPWReqMO pwReqVO) {
        return paymentWithdrawService.getRateProduct(pwReqVO);
    }

    @ApiOperation(value = "查询支付提现(paymentWithdraw)-获取消费类型")
    @GetMapping("/pw/paytypes")
    public MessageTO getPWPayTypes() {
        return paymentWithdrawService.getConsumptionType();
    }


    @ApiOperation(value = "业务大盘数据---门店审核-报表")
    @GetMapping("/store/report/{type}/{start_date}/{end_date}")
    public MessageTO getStoreReport(@PathVariable("type") String type, @PathVariable("start_date") String start, @PathVariable("end_date") String end) {
        MessageTO message;
        switch (type) {
            case "day":
                message = merchantStoreService.getStoreData(start, end);
                break;
            default:
                message = ResponseUtils.failure(ResponseCode.BAD_REQUEST, "请求参数type错误，只提供时间粒度为：day的数据");
        }
        return message;
    }

    @ApiOperation(value = "业务大盘数据---商户审核-报表")
    @GetMapping("/merchant/report/{type}/{start_date}/{end_date}")
    public MessageTO getMerchantReport(@PathVariable("type") String type, @PathVariable("start_date") String start, @PathVariable("end_date") String end) {
        MessageTO message;
        switch (type) {
            case "day":
                message = merchantStoreService.getMerchantData(start, end);
                break;
            default:
                message = ResponseUtils.failure(ResponseCode.BAD_REQUEST, "请求参数type错误，只提供时间粒度为：day的数据");
        }
        return message;
    }


//    @ApiOperation(value="导出进件渠道信息数据")
//    @GetMapping("/channel/export/{type}/{start}/{end}")
//    public MessageTO exportChannelCount(HttpServletResponse response, @PathVariable("type") String type, @PathVariable("start") Integer start, @PathVariable("end") Integer end) throws IOException {
//        String prefix = type;
//
//        String fileName = prefix
//                + DatetimeUtil.nowToString()
//                + ".xlsx";
//
//        MessageTO messageTO = businessDataService.getChannelCount(type,start,end);
//        if ("day".equals(type)) {
//            List<BusinessApplyChannelCountDay> list = (List<BusinessApplyChannelCountDay>) messageTO.getData();
//            System.out.println(JSON.toJSONString(list));
//            new ExportExcel(prefix, BusinessApplyChannelCountDay.class, 1, 1).setDataList(list)
//                    .write(response, fileName).dispose();
//
//        }
//        return ResponseUtils.failure("default");
//    }


    private Map<String, Long> uniformSummaryResult(List<Map<String, Object>> data) {
        Map<String, Long> result = new HashMap<>();
        for (Map<String, Object> entity : data) {
            String key = entity.get("type") == null ? null : entity.get("type").toString();
            String value = entity.get("amount") == null ? null : entity.get("amount").toString();
            if (StringUtils.isBlank(key)) {
                continue;
            }

            result.put(key, StringUtils.isBlank(value) ? 0L : Long.valueOf(value));

        }
        return result;
    }

}
