package com.ppfly.demo.leave.controller;

import com.ppfly.demo.common.entities.BusinessOper;
import com.ppfly.demo.common.entities.WorkflowInfo;
import com.ppfly.demo.common.service.BaseWorkflowService;
import com.ppfly.demo.leave.entities.Leave;
import com.ppfly.demo.leave.service.LeaveService;
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
import java.util.List;
import java.util.Map;

/**
 * Created by ppfly on 2017/9/22.
 */
@Controller
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    protected LeaveService leaveService;

    @Autowired
    protected BaseWorkflowService baseWorkflowService;

    /**
     * 跳转到请假页面
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/goToAskForLeave")
    public String goToAskForLeave(@ModelAttribute("leave") Leave leave, HttpServletRequest request, HttpServletResponse response) {
        return "leave/askForLeave";
    }

    /**
     * 请假
     *
     * @param model
     * @param leave
     * @param result
     * @return
     */
    @RequestMapping(value = "/askForLeave", method = RequestMethod.POST)
    public String askForLeave(Model model, @Valid Leave leave, BindingResult result) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("MSG", "出错啦！");
                String flag = leave.getFlag();
                if (flag != null && !"".equals(flag)) {//调整请假申请页面
                    List<BusinessOper> businessOperList = baseWorkflowService.getBusinessOperListByBizId(leave.getId() + "");
                    model.addAttribute(businessOperList);
                    return "leave/reAskForLeave";
                }
                return "leave/askForLeave";//请假申请页面
            }
            ProcessInstance processInstance = leaveService.askForLeave(leave, "leave");
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    /**
     * 跳转到调整请假页面
     *
     * @param map
     * @param taskId
     * @param businessId
     * @return
     */
    @GetMapping("/goToReAskForLeave/{taskId}")
    public String goToReAskForLeave(Map<String, Object> map, @PathVariable("taskId") String taskId, @RequestParam("businessId") String businessId) {
        WorkflowInfo workflowInfo = baseWorkflowService.getWorkflowInfoById(businessId);
        workflowInfo.setTaskId(taskId);
        List<BusinessOper> businessOperList = baseWorkflowService.getBusinessOperListByBizId(businessId);
        map.put("leave", workflowInfo);
        map.put("businessOperList", businessOperList);
        return "leave/reAskForLeave";
    }

    /**
     * 处理（审核）/完成请假
     */
    @PostMapping(value = "/dealLeave/{taskId}")
    public String dealLeave(@ModelAttribute("businessOper") BusinessOper businessOper, @PathVariable("taskId") String taskId, @RequestParam(value = "businessId", required = true) String businessId) {
        try {
            businessOper.setOperTime(new Date());
            leaveService.audit(taskId, businessOper);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
