package com.ppfly.demo.event.controller;

import com.ppfly.demo.event.entities.EventPO;
import com.ppfly.demo.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 事件流程Handler
 * Created by ppfly on 2017/9/22.
 */
@Controller
@RequestMapping("/event")
public class EventController {
    @Autowired
    private EventService eventService;


    /**
     * 跳转到事件申告页面
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/goToDeclareEvent")
    public String goToDeclareEvent(@ModelAttribute("eventPO") EventPO eventPO, HttpServletRequest request, HttpServletResponse response) {
        return "event/declareEvent";
    }


    /**
     * 申告事件
     *
     * @param leave
     * @return
     */
    @RequestMapping(value = "/declareEvent", method = RequestMethod.POST)
    public String declareEvent(@ModelAttribute("event") EventPO leave) {
        try {
//            ProcessInstance processInstance = workflowService.startWorkflow(leave, "leave");
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

}
