package com.platform.finance.report.web.controller;

import com.platform.finance.report.common.controller.BaseController;
import com.platform.finance.report.common.core.Page;
import com.platform.finance.report.common.enums.*;
import com.platform.finance.report.common.mo.ResponseMO;
import com.platform.finance.report.common.utils.FileUtils;
import com.platform.finance.report.repo.ro.*;
import com.platform.finance.report.service.FinanceOverdueReportService;
import com.platform.finance.report.service.FinanceResidualPrincipalDistributionReportService;
import com.platform.finance.report.service.FinanceResidualPrincipalFiveLevelReportService;
import com.platform.finance.report.service.FinanceUndueReportService;
import com.platform.finance.report.web.mo.OverdueReportMO;
import com.platform.finance.report.web.mo.PrincipalDistributionReportMO;
import com.platform.finance.report.web.mo.PrincipalFiveLevelReportMO;
import com.platform.finance.report.web.mo.UndueReportMO;
import com.platform.finance.report.web.mo.bean.FiveLevelReportBean;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/3 13:29
 * @description 余额表
 */
@Api(tags = "余额表")
@RestController
@RequestMapping("finance/balance")
public class BalanceReportDataController extends BaseController {

    @Autowired
    private FinanceOverdueReportService financeOverdueReportService;

    @Autowired
    private FinanceUndueReportService financeUndueReportService;

    @Autowired
    private FinanceResidualPrincipalDistributionReportService financeResidualPrincipalDistributionReportService;

    @Autowired
    private FinanceResidualPrincipalFiveLevelReportService financeResidualPrincipalFiveLevelReportService;

    /**
     * 查询逾期表
     *
     * @param queryMO
     * @return
     */
    @ApiOperation(value = "查询逾期表")
    @PostMapping(value = "/overdue")
    public ResponseMO<OverdueReportMO> selectOverdueReport(@RequestBody OverdueReportMO queryMO) {
        PageRequest pageRequest = PageRequest.of(queryMO.getPageNumber(), queryMO.getPageSize());
        OverdueReportRO overdueReportRO = new OverdueReportRO();
        BeanUtils.copyProperties(queryMO, overdueReportRO);
        Page<FinanceOverdueReportListRO> financeOverdueReportListROPage = financeOverdueReportService.selectOverdueReport(overdueReportRO, pageRequest);
        if (financeOverdueReportListROPage != null) {
            queryMO.setFinanceOverdueReportListROList(financeOverdueReportListROPage.getContent());
            queryMO.setTotal(financeOverdueReportListROPage.getTotalElements());
            queryMO.setTotalPages(financeOverdueReportListROPage.getTotalPages());
        }
        return successWithData(queryMO);
    }

    /**
     * 导出逾期表
     *
     * @param queryMO
     * @param response
     */
    @ApiOperation(value = "导出逾期表")
    @PostMapping(value = "/exportOverdue")
    public void exportOverdueReport(@RequestBody OverdueReportMO queryMO, HttpServletResponse response) {
        OverdueReportRO overdueReportRO = new OverdueReportRO();
        BeanUtils.copyProperties(queryMO, overdueReportRO);
        List<FinanceOverdueReportListRO> list = financeOverdueReportService.exportOverdueReport(overdueReportRO);
        convertLoanExport(list);
        if (!CollectionUtils.isEmpty(list)) {
            FileUtils.downFile(FinanceOverdueReportListRO.class, list, response);
        }
    }

    /**
     * 逾期表导出转换
     *
     * @param list
     */
    private void convertLoanExport(List<FinanceOverdueReportListRO> list) {
        list.stream().forEach(overdueReport -> {
            overdueReport.setFundingParty(FundingPartyEnum.getDescFrom(overdueReport.getFundingParty()));
            overdueReport.setChannelType(ChannelTypeEnum.getDescFrom(overdueReport.getChannelType()));
            overdueReport.setChannelName(ChannelNameEnum.getDescFrom(overdueReport.getChannelName()));
            overdueReport.setProductType(ProductTypeEnum.getDescFrom(overdueReport.getProductType()));
            overdueReport.setProductChildType(ProductChildTypeEnum.getDescFrom(overdueReport.getProductChildType()));
        });
    }

    /**
     * 查询未到期表
     *
     * @param queryMO
     * @return
     */
    @ApiOperation(value = "查询未到期表")
    @PostMapping(value = "/undue")
    public ResponseMO<UndueReportMO> selectUndueReport(@RequestBody UndueReportMO queryMO) {
        PageRequest pageRequest = PageRequest.of(queryMO.getPageNumber(), queryMO.getPageSize());
        UndueReportRO undueReportRO = new UndueReportRO();
        BeanUtils.copyProperties(queryMO, undueReportRO);
        Page<FinanceUndueReportListRO> financeUndueReportListROPage = financeUndueReportService.selectUndueReport(undueReportRO, pageRequest);
        if (financeUndueReportListROPage != null) {
            queryMO.setFinanceUndueReportListROList(financeUndueReportListROPage.getContent());
            queryMO.setTotal(financeUndueReportListROPage.getTotalElements());
            queryMO.setTotalPages(financeUndueReportListROPage.getTotalPages());
        }
        return successWithData(queryMO);
    }

    /**
     * 导出未到期表
     *
     * @param queryMO
     * @param response
     */
    @ApiOperation(value = "导出未到期表")
    @PostMapping(value = "/exportUndue")
    public void exportUndueReport(@RequestBody UndueReportMO queryMO, HttpServletResponse response) {
        UndueReportRO undueReportRO = new UndueReportRO();
        BeanUtils.copyProperties(queryMO, undueReportRO);
        List<FinanceUndueReportListRO> list = financeUndueReportService.exportUndueReport(undueReportRO);
        convertUndueExport(list);
        if (!CollectionUtils.isEmpty(list)) {
            FileUtils.downFile(FinanceUndueReportListRO.class, list, response);
        }
    }

    /**
     * 未到期导出转换
     *
     * @param list
     */
    private void convertUndueExport(List<FinanceUndueReportListRO> list) {
        list.stream().forEach(undueReport -> {
            undueReport.setFundingParty(FundingPartyEnum.getDescFrom(undueReport.getFundingParty()));
            undueReport.setChannelType(ChannelTypeEnum.getDescFrom(undueReport.getChannelType()));
            undueReport.setChannelName(ChannelNameEnum.getDescFrom(undueReport.getChannelName()));
            undueReport.setProductType(ProductTypeEnum.getDescFrom(undueReport.getProductType()));
            undueReport.setProductChildType(ProductChildTypeEnum.getDescFrom(undueReport.getProductChildType()));
        });
    }

    /**
     * 查询剩余本金分布表
     *
     * @param queryMO
     * @return
     */
    @ApiOperation(value = "查询剩余本金分布表")
    @PostMapping(value = "/distribution")
    public ResponseMO<PrincipalDistributionReportMO> selectResidualPrincipalDistributionReport(@RequestBody PrincipalDistributionReportMO queryMO) {
        PageRequest pageRequest = PageRequest.of(queryMO.getPageNumber(), queryMO.getPageSize());
        ResidualPrincipalDistributionReportRO residualPrincipalDistributionReportRO = new ResidualPrincipalDistributionReportRO();
        BeanUtils.copyProperties(queryMO, residualPrincipalDistributionReportRO);
        Page<FinanceResidualPrincipalDistributionReportListRO> financeResidualPrincipalDistributionReportListROPage = financeResidualPrincipalDistributionReportService.selectResidualPrincipalDistributionReport(residualPrincipalDistributionReportRO, pageRequest);
        if (financeResidualPrincipalDistributionReportListROPage != null) {
            queryMO.setFinanceResidualPrincipalDistributionReportListROList(financeResidualPrincipalDistributionReportListROPage.getContent());
            queryMO.setTotal(financeResidualPrincipalDistributionReportListROPage.getTotalElements());
            queryMO.setTotalPages(financeResidualPrincipalDistributionReportListROPage.getTotalPages());
        }
        return successWithData(queryMO);
    }

    /**
     * 导出剩余本金分布表
     *
     * @param queryMO
     * @param response
     */
    @ApiOperation(value = "导出剩余本金分布表")
    @PostMapping(value = "/exportDistribution")
    public void exportResidualPrincipalDistributionReport(@RequestBody PrincipalDistributionReportMO queryMO, HttpServletResponse response) {
        ResidualPrincipalDistributionReportRO residualPrincipalDistributionReportRO = new ResidualPrincipalDistributionReportRO();
        BeanUtils.copyProperties(queryMO, residualPrincipalDistributionReportRO);
        List<FinanceResidualPrincipalDistributionReportListRO> list = financeResidualPrincipalDistributionReportService.exportResidualPrincipalDistributionReport(residualPrincipalDistributionReportRO);
        convertDistributionExport(list);
        if (!CollectionUtils.isEmpty(list)) {
            FileUtils.downFile(FinanceResidualPrincipalDistributionReportListRO.class, list, response);
        }
    }

    /**
     * 剩余本金分布表导出转换
     *
     * @param list
     */
    private void convertDistributionExport(List<FinanceResidualPrincipalDistributionReportListRO> list) {
        list.stream().forEach(distributionReport -> {
            distributionReport.setChannelType(ChannelTypeEnum.getDescFrom(distributionReport.getChannelType()));
            distributionReport.setChannelName(ChannelNameEnum.getDescFrom(distributionReport.getChannelName()));
            distributionReport.setProductType(ProductTypeEnum.getDescFrom(distributionReport.getProductType()));
            distributionReport.setProductChildType(ProductChildTypeEnum.getDescFrom(distributionReport.getProductChildType()));
        });
    }

    /**
     * 查询剩余本金五级分类表
     *
     * @param queryMO
     * @return
     */
    @ApiOperation(value = "查询剩余本金五级分类表")
    @PostMapping(value = "/fivelevel")
    public ResponseMO<PrincipalFiveLevelReportMO> selectResidualPrincipalFiveLevelReport(@RequestBody PrincipalFiveLevelReportMO queryMO) {
        String dataDateStart = queryMO.getDataDateStart();
        String dataDateEnd = queryMO.getDataDateEnd();
        List<FinanceResidualPrincipalFiveLevelReportListRO> list = financeResidualPrincipalFiveLevelReportService.selectResidualPrincipalFiveLevelReport(dataDateStart, dataDateEnd);
        List<FiveLevelReportBean> fiveLevelReportBeanList = new ArrayList<>();
        list.stream().forEach(fiveLevelReport -> {
            FiveLevelReportBean fiveLevelReportBean = new FiveLevelReportBean();
            fiveLevelReportBean.setDataDate(fiveLevelReport.getDataDate());
            fiveLevelReportBean.setLevelAmountList(Arrays.asList(fiveLevelReport.getZeroLevelAmount(),
                    fiveLevelReport.getOneLevelAmount(),
                    fiveLevelReport.getTwoLevelAmount(),
                    fiveLevelReport.getThreeLevelAmount(),
                    fiveLevelReport.getFourLevelAmount(),
                    fiveLevelReport.getFiveLevelAmount(),
                    fiveLevelReport.getSixLevelAmount(),
                    fiveLevelReport.getSevenLevelAmount(),
                    fiveLevelReport.getEightLevelAmount(),
                    fiveLevelReport.getNineLevelAmount(),
                    fiveLevelReport.getTenLevelAmount(),
                    fiveLevelReport.getElevenLevelAmount(),
                    fiveLevelReport.getTwelveLevelAmount(),
                    fiveLevelReport.getThirteenLevelAmount(),
                    fiveLevelReport.getTotalAmount()));
            fiveLevelReportBeanList.add(fiveLevelReportBean);

        });
        queryMO.setFiveLevelReportBeanList(fiveLevelReportBeanList);
        return successWithData(queryMO);
    }
}
