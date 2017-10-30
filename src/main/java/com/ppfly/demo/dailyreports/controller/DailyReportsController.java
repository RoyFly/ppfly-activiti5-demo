package com.ppfly.demo.dailyreports.controller;

import com.ppfly.demo.common.entities.BusinessOper;
import com.ppfly.demo.dailyreports.entites.DailyReports;
import com.ppfly.demo.dailyreports.entites.SubDailyReports;
import com.ppfly.demo.dailyreports.service.DailyReportsService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

/**
 * 日报Controller
 * Created by ppfly on 2017/10/9.
 */
@Controller
@RequestMapping("/dailyReports")
public class DailyReportsController {
    @Autowired
    private DailyReportsService dailyReportsService;

    /**
     * 跳转到发起日报任务页面
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/goToOriginatingDaily")
    public String goToOriginatingDaily(@ModelAttribute("dailyReports") DailyReports dailyReports, HttpServletRequest request, HttpServletResponse response) {
        return "dailyreports/originatingDaily";
    }

    /**
     * 收集日报
     *
     * @param dailyReports
     * @return
     */
    @RequestMapping(value = "/collectDaily", method = RequestMethod.POST)
    public String declareEvent(Model model, @Valid DailyReports dailyReports, BindingResult result) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("MSG", "出错啦！");
                return "dailyreports/collectDaily";
            }
            ProcessInstance processInstance = dailyReportsService.collectDaily(dailyReports, "dailyReports");
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    /**
     * （组员）填写日报
     *
     * @param model
     * @param subDailyReports
     * @param result
     * @param taskId
     * @param businessId
     * @return
     */
    @PostMapping(value = "/fillInTheDaily/{taskId}")
    public String fillInTheDaily(Model model, @Valid SubDailyReports subDailyReports, BindingResult result,
                                 @PathVariable("taskId") String taskId, @RequestParam(value = "businessId") String businessId) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("MSG", "出错啦！");
                return "dailyreports/fillInTheDaily";
            }
            dailyReportsService.fillInTheDaily(subDailyReports, taskId);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * 处理（审核）/完成日报
     */
    @PostMapping(value = "/dealDaily/{taskId}")
    public String dealDaily(@ModelAttribute("businessOper") BusinessOper businessOper, @PathVariable("taskId") String taskId, @RequestParam(value = "businessId", required = true) String businessId) {
        try {
            businessOper.setOperTime(new Date());
            dailyReportsService.audit(taskId, businessOper);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

}
