package com.platform.finance.report.web.controller;

import com.platform.finance.report.common.controller.BaseController;
import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.common.enums.*;
import com.platform.finance.report.common.mo.ResponseMO;
import com.platform.finance.report.common.utils.FileUtils;
import com.platform.finance.report.repo.ro.*;
import com.platform.finance.report.service.*;
import com.platform.finance.report.web.mo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/2 10:55
 * @description 期间表
 */
@Api(tags = "期间表")
@RestController
@RequestMapping("finance/duration")
public class DurationReportDataController extends BaseController {

    @Autowired
    private FinanceCumulativeLoanReportService financeCumulativeLoanReportService;

    @Autowired
    private FinanceCumulativeMaturityReportService financeCumulativeMaturityReportService;

    @Autowired
    private FinanceCumulativeActualRepaymentReportService financeCumulativeActualRepaymentReportService;

    @Autowired
    private FinanceCumulativeDeductionReportService financeCumulativeDeductionReportService;

    @Autowired
    private FinanceCompensatoryCumulativePartnersReportService financeCompensatoryCumulativePartnersReportService;

    @Autowired
    private FinanceBuyoutCumulativePartnersReportService financeBuyoutCumulativePartnersReportService;

    /**
     * 查询放款表
     *
     * @param queryMO
     * @return
     */
    @ApiOperation(value = "查询放款表")
    @PostMapping(value = "/loan")
    public ResponseMO<CumulativeLoanReportMO> selectCumulativeLoanReport(@RequestBody CumulativeLoanReportMO queryMO) {
        PageRequest pageRequest = PageRequest.of(queryMO.getPageNumber(), queryMO.getPageSize());
        CumulativeLoanReportRO cumulativeLoanReportRO = new CumulativeLoanReportRO();
        BeanUtils.copyProperties(queryMO, cumulativeLoanReportRO);
        Page<FinanceCumulativeLoanReportListRO> financeCumulativeLoanReportListROPage = financeCumulativeLoanReportService.selectCumulativeLoanReport(cumulativeLoanReportRO, pageRequest);
        if (financeCumulativeLoanReportListROPage != null) {
            queryMO.setFinanceCumulativeLoanReportListROList(financeCumulativeLoanReportListROPage.getContent());
            queryMO.setTotal(financeCumulativeLoanReportListROPage.getTotalElements());
            queryMO.setTotalPages(financeCumulativeLoanReportListROPage.getTotalPages());
        }
        return successWithData(queryMO);
    }

    /**
     * 导出放款表
     *
     * @param queryMO
     * @param response
     */
    @ApiOperation(value = "导出放款表")
    @PostMapping(value = "/exportLoan")
    public void exportCumulativeLoanReport(@RequestBody CumulativeLoanReportMO queryMO, HttpServletResponse response) {
        CumulativeLoanReportRO cumulativeLoanReportRO = new CumulativeLoanReportRO();
        BeanUtils.copyProperties(queryMO, cumulativeLoanReportRO);
        List<FinanceCumulativeLoanReportListRO> list = financeCumulativeLoanReportService.exportCumulativeLoanReport(cumulativeLoanReportRO);
        convertLoanExport(list);
        if (!CollectionUtils.isEmpty(list)) {
            FileUtils.downFile(FinanceCumulativeLoanReportListRO.class, list, response);
        }
    }

    /**
     * 放款表导出转换
     *
     * @param list
     */
    private void convertLoanExport(List<FinanceCumulativeLoanReportListRO> list) {
        list.stream().forEach(loanReport -> {
            loanReport.setFundingParty(FundingPartyEnum.getDescFrom(loanReport.getFundingParty()));
            loanReport.setChannelType(ChannelTypeEnum.getDescFrom(loanReport.getChannelType()));
            loanReport.setChannelName(ChannelNameEnum.getDescFrom(loanReport.getChannelName()));
            loanReport.setProductType(ProductTypeEnum.getDescFrom(loanReport.getProductType()));
            loanReport.setProductChildType(ProductChildTypeEnum.getDescFrom(loanReport.getProductChildType()));
        });
    }

    /**
     * 查询到期表
     *
     * @param queryMO
     * @return
     */
    @ApiOperation(value = "查询到期表")
    @PostMapping(value = "/maturity")
    public ResponseMO<CumulativeMaturityReportMO> selectCumulativeMaturityReport(@RequestBody CumulativeMaturityReportMO queryMO) {
        PageRequest pageRequest = PageRequest.of(queryMO.getPageNumber(), queryMO.getPageSize());
        CumulativeMaturityReportRO cumulativeMaturityReportRO = new CumulativeMaturityReportRO();
        BeanUtils.copyProperties(queryMO, cumulativeMaturityReportRO);
        Page<FinanceCumulativeMaturityReportListRO> financeCumulativeMaturityReportListROPage = financeCumulativeMaturityReportService.selectCumulativeMaturityReport(cumulativeMaturityReportRO, pageRequest);
        if (financeCumulativeMaturityReportListROPage != null) {
            queryMO.setFinanceCumulativeMaturityReportListROList(financeCumulativeMaturityReportListROPage.getContent());
            queryMO.setTotal(financeCumulativeMaturityReportListROPage.getTotalElements());
            queryMO.setTotalPages(financeCumulativeMaturityReportListROPage.getTotalPages());
        }
        return successWithData(queryMO);
    }

    /**
     * 导出到期表
     *
     * @param queryMO
     * @param response
     */
    @ApiOperation(value = "导出到期表")
    @PostMapping(value = "/exportMaturity")
    public void exportCumulativeMaturityReport(@RequestBody CumulativeMaturityReportMO queryMO, HttpServletResponse response) {
        CumulativeMaturityReportRO cumulativeMaturityReportRO = new CumulativeMaturityReportRO();
        BeanUtils.copyProperties(queryMO, cumulativeMaturityReportRO);
        List<FinanceCumulativeMaturityReportListRO> list = financeCumulativeMaturityReportService.exportCumulativeMaturityReport(cumulativeMaturityReportRO);
        convertMaturityExport(list);
        if (!CollectionUtils.isEmpty(list)) {
            FileUtils.downFile(FinanceCumulativeMaturityReportListRO.class, list, response);
        }
    }

    /**
     * 到期表导出转换
     *
     * @param list
     */
    private void convertMaturityExport(List<FinanceCumulativeMaturityReportListRO> list) {
        list.stream().forEach(maturityReport -> {
            maturityReport.setFundingParty(FundingPartyEnum.getDescFrom(maturityReport.getFundingParty()));
            maturityReport.setChannelType(ChannelTypeEnum.getDescFrom(maturityReport.getChannelType()));
            maturityReport.setChannelName(ChannelNameEnum.getDescFrom(maturityReport.getChannelName()));
            maturityReport.setProductType(ProductTypeEnum.getDescFrom(maturityReport.getProductType()));
            maturityReport.setProductChildType(ProductChildTypeEnum.getDescFrom(maturityReport.getProductChildType()));
        });
    }

    /**
     * 查询实还表
     *
     * @param queryMO
     * @return
     */
    @ApiOperation(value = "查询实还表")
    @PostMapping(value = "/actualrepayment")
    public ResponseMO<CumulativeActualRepaymentReportMO> selectCumulativeActualRepaymentReport(@RequestBody CumulativeActualRepaymentReportMO queryMO) {
        PageRequest pageRequest = PageRequest.of(queryMO.getPageNumber(), queryMO.getPageSize());
        CumulativeActualRepaymentReportRO cumulativeActualRepaymentReportRO = new CumulativeActualRepaymentReportRO();
        BeanUtils.copyProperties(queryMO, cumulativeActualRepaymentReportRO);
        Page<FinanceCumulativeActualRepaymentReportListRO> financeCumulativeActualRepaymentReportListROPage = financeCumulativeActualRepaymentReportService.selectCumulativeActualRepaymentReport(cumulativeActualRepaymentReportRO, pageRequest);
        if (financeCumulativeActualRepaymentReportListROPage != null) {
            queryMO.setFinanceCumulativeActualRepaymentReportListROList(financeCumulativeActualRepaymentReportListROPage.getContent());
            queryMO.setTotal(financeCumulativeActualRepaymentReportListROPage.getTotalElements());
            queryMO.setTotalPages(financeCumulativeActualRepaymentReportListROPage.getTotalPages());
        }
        return successWithData(queryMO);
    }

    /**
     * 导出实还表
     *
     * @param queryMO
     * @param response
     * @return
     */
    @ApiOperation(value = "导出实还表")
    @PostMapping(value = "/exportActualRepayment")
    public void exportCumulativeActualRepaymentReport(@RequestBody CumulativeActualRepaymentReportMO queryMO, HttpServletResponse response) {
        CumulativeActualRepaymentReportRO cumulativeActualRepaymentReportRO = new CumulativeActualRepaymentReportRO();
        BeanUtils.copyProperties(queryMO, cumulativeActualRepaymentReportRO);
        List<FinanceCumulativeActualRepaymentReportListRO> list = financeCumulativeActualRepaymentReportService.exportCumulativeActualRepaymentReport(cumulativeActualRepaymentReportRO);
        convertActualRepaymentExport(list);
        if (!CollectionUtils.isEmpty(list)) {
            FileUtils.downFile(FinanceCumulativeActualRepaymentReportListRO.class, list, response);
        }
    }

    /**
     * 实还表导出转换
     *
     * @param list
     */
    private void convertActualRepaymentExport(List<FinanceCumulativeActualRepaymentReportListRO> list) {
        list.stream().forEach(actualRepaymentReport -> {
            actualRepaymentReport.setFundingParty(FundingPartyEnum.getDescFrom(actualRepaymentReport.getFundingParty()));
            actualRepaymentReport.setChannelType(ChannelTypeEnum.getDescFrom(actualRepaymentReport.getChannelType()));
            actualRepaymentReport.setChannelName(ChannelNameEnum.getDescFrom(actualRepaymentReport.getChannelName()));
            actualRepaymentReport.setProductType(ProductTypeEnum.getDescFrom(actualRepaymentReport.getProductType()));
            actualRepaymentReport.setProductChildType(ProductChildTypeEnum.getDescFrom(actualRepaymentReport.getProductChildType()));
            actualRepaymentReport.setPayType(PayTypeEnum.getDescFrom(actualRepaymentReport.getPayType()));
            actualRepaymentReport.setPayStatus(PayStatusEnum.getDescFrom(actualRepaymentReport.getPayStatus()));
        });
    }

    /**
     * 查询减免表
     *
     * @param queryMO
     * @return
     */
    @ApiOperation(value = "查询减免表")
    @PostMapping(value = "/deduction")
    public ResponseMO<CumulativeDeductionReportMO> selectCumulativeDeductionReport(@RequestBody CumulativeDeductionReportMO queryMO) {
        PageRequest pageRequest = PageRequest.of(queryMO.getPageNumber(), queryMO.getPageSize());
        CumulativeDeductionReportRO cumulativeDeductionReportRO = new CumulativeDeductionReportRO();
        BeanUtils.copyProperties(queryMO, cumulativeDeductionReportRO);
        Page<FinanceCumulativeDeductionReportListRO> financeCumulativeDeductionReportListROPage = financeCumulativeDeductionReportService.selectCumulativeDeductionReport(cumulativeDeductionReportRO, pageRequest);
        if (financeCumulativeDeductionReportListROPage != null) {
            queryMO.setFinanceCumulativeDeductionReportListROList(financeCumulativeDeductionReportListROPage.getContent());
            queryMO.setTotal(financeCumulativeDeductionReportListROPage.getTotalElements());
            queryMO.setTotalPages(financeCumulativeDeductionReportListROPage.getTotalPages());
        }
        return successWithData(queryMO);
    }

    /**
     * 导出减免表
     *
     * @param queryMO
     * @param response
     * @return
     */
    @ApiOperation(value = "导出减免表")
    @PostMapping(value = "/exportDeduction")
    public void exportCumulativeDeductionReport(@RequestBody CumulativeDeductionReportMO queryMO, HttpServletResponse response) {
        CumulativeDeductionReportRO cumulativeDeductionReportRO = new CumulativeDeductionReportRO();
        BeanUtils.copyProperties(queryMO, cumulativeDeductionReportRO);
        List<FinanceCumulativeDeductionReportListRO> list = financeCumulativeDeductionReportService.exportCumulativeDeductionReport(cumulativeDeductionReportRO);
        convertDeductionExport(list);
        if (!CollectionUtils.isEmpty(list)) {
            FileUtils.downFile(FinanceCumulativeDeductionReportListRO.class, list, response);
        }
    }

    /**
     * 减免表导出转换
     *
     * @param list
     */
    private void convertDeductionExport(List<FinanceCumulativeDeductionReportListRO> list) {
        list.stream().forEach(deductionReport -> {
            deductionReport.setFundingParty(FundingPartyEnum.getDescFrom(deductionReport.getFundingParty()));
            deductionReport.setChannelType(ChannelTypeEnum.getDescFrom(deductionReport.getChannelType()));
            deductionReport.setChannelName(ChannelNameEnum.getDescFrom(deductionReport.getChannelName()));
            deductionReport.setProductType(ProductTypeEnum.getDescFrom(deductionReport.getProductType()));
            deductionReport.setProductChildType(ProductChildTypeEnum.getDescFrom(deductionReport.getProductChildType()));
            deductionReport.setDeductionType(DeductionTypeEnum.getDescFrom(deductionReport.getDeductionType()));
        });
    }


    /**
     * 查询代偿表
     *
     * @param queryMO
     * @return
     */
    @ApiOperation(value = "查询代偿表")
    @PostMapping(value = "/compensatory")
    public ResponseMO<CompensatoryReportMO> selectCompensatoryCumulativePartnersReport(@RequestBody CompensatoryReportMO queryMO) {
        PageRequest pageRequest = PageRequest.of(queryMO.getPageNumber(), queryMO.getPageSize());
        CompensatoryCumulativePartnersReportRO compensatoryCumulativePartnersReportRO = new CompensatoryCumulativePartnersReportRO();
        BeanUtils.copyProperties(queryMO, compensatoryCumulativePartnersReportRO);
        Page<FinanceCompensatoryCumulativePartnersReportListRO> financeCompensatoryCumulativePartnersReportListROPage = financeCompensatoryCumulativePartnersReportService.selectCompensatoryCumulativePartnersReport(compensatoryCumulativePartnersReportRO, pageRequest);
        if (financeCompensatoryCumulativePartnersReportListROPage != null) {
            queryMO.setFinanceCompensatoryCumulativePartnersReportListROList(financeCompensatoryCumulativePartnersReportListROPage.getContent());
            queryMO.setTotal(financeCompensatoryCumulativePartnersReportListROPage.getTotalElements());
            queryMO.setTotalPages(financeCompensatoryCumulativePartnersReportListROPage.getTotalPages());
        }
        return successWithData(queryMO);
    }

    /**
     * 导出代偿表
     *
     * @param queryMO
     * @param response
     * @return
     */
    @ApiOperation(value = "导出代偿表")
    @PostMapping(value = "/exportCompensatory")
    public void exportCompensatoryCumulativePartnersReport(@RequestBody CumulativeDeductionReportMO queryMO, HttpServletResponse response) {
        CompensatoryCumulativePartnersReportRO compensatoryCumulativePartnersReportRO = new CompensatoryCumulativePartnersReportRO();
        BeanUtils.copyProperties(queryMO, compensatoryCumulativePartnersReportRO);
        List<FinanceCompensatoryCumulativePartnersReportListRO> list = financeCompensatoryCumulativePartnersReportService.exportCompensatoryCumulativePartnersReport(compensatoryCumulativePartnersReportRO);
        convertCompensatoryExport(list);
        if (!CollectionUtils.isEmpty(list)) {
            FileUtils.downFile(FinanceCompensatoryCumulativePartnersReportListRO.class, list, response);
        }
    }

    /**
     * 代偿表导出转换
     *
     * @param list
     */
    private void convertCompensatoryExport(List<FinanceCompensatoryCumulativePartnersReportListRO> list) {
        list.stream().forEach(compensatoryReport -> {
            compensatoryReport.setFundingParty(FundingPartyEnum.getDescFrom(compensatoryReport.getFundingParty()));
            compensatoryReport.setChannelType(ChannelTypeEnum.getDescFrom(compensatoryReport.getChannelType()));
            compensatoryReport.setChannelName(ChannelNameEnum.getDescFrom(compensatoryReport.getChannelName()));
            compensatoryReport.setProductType(ProductTypeEnum.getDescFrom(compensatoryReport.getProductType()));
        });
    }

    /**
     * 查询买断表
     *
     * @param queryMO
     * @return
     */
    @ApiOperation(value = "查询买断表")
    @PostMapping(value = "/buyout")
    public ResponseMO<BuyoutReportMO> selectBuyoutCumulativePartnersReport(@RequestBody BuyoutReportMO queryMO) {
        PageRequest pageRequest = PageRequest.of(queryMO.getPageNumber(), queryMO.getPageSize());
        BuyoutCumulativePartnersReportRO buyoutCumulativePartnersReportRO = new BuyoutCumulativePartnersReportRO();
        BeanUtils.copyProperties(queryMO, buyoutCumulativePartnersReportRO);
        Page<FinanceBuyoutCumulativePartnersReportListRO> financeBuyoutCumulativePartnersReportListROPage = financeBuyoutCumulativePartnersReportService.selectBuyoutCumulativePartnersReport(buyoutCumulativePartnersReportRO, pageRequest);
        if (financeBuyoutCumulativePartnersReportListROPage != null) {
            queryMO.setFinanceBuyoutCumulativePartnersReportListROList(financeBuyoutCumulativePartnersReportListROPage.getContent());
            queryMO.setTotal(financeBuyoutCumulativePartnersReportListROPage.getTotalElements());
            queryMO.setTotalPages(financeBuyoutCumulativePartnersReportListROPage.getTotalPages());
        }
        return successWithData(queryMO);
    }

    /**
     * 导出买断表
     *
     * @param queryMO
     * @param response
     * @return
     */
    @ApiOperation(value = "导出买断表")
    @PostMapping(value = "/exportBuyout")
    public void exportBuyoutCumulativePartnersReport(@RequestBody BuyoutReportMO queryMO, HttpServletResponse response) {
        BuyoutCumulativePartnersReportRO buyoutCumulativePartnersReportRO = new BuyoutCumulativePartnersReportRO();
        BeanUtils.copyProperties(queryMO, buyoutCumulativePartnersReportRO);
        List<FinanceBuyoutCumulativePartnersReportListRO> list = financeBuyoutCumulativePartnersReportService.exportBuyoutCumulativePartnersReport(buyoutCumulativePartnersReportRO);
        convertBuyoutExport(list);
        if (!CollectionUtils.isEmpty(list)) {
            FileUtils.downFile(FinanceBuyoutCumulativePartnersReportListRO.class, list, response);
        }
    }

    /**
     * 买断表导出转换
     *
     * @param list
     */
    private void convertBuyoutExport(List<FinanceBuyoutCumulativePartnersReportListRO> list) {
        list.stream().forEach(buyoutReport -> {
            buyoutReport.setFundingParty(FundingPartyEnum.getDescFrom(buyoutReport.getFundingParty()));
            buyoutReport.setChannelType(ChannelTypeEnum.getDescFrom(buyoutReport.getChannelType()));
            buyoutReport.setChannelName(ChannelNameEnum.getDescFrom(buyoutReport.getChannelName()));
            buyoutReport.setProductType(ProductTypeEnum.getDescFrom(buyoutReport.getProductType()));
        });
    }
}
