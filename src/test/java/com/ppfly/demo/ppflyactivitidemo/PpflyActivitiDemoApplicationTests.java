package com.ppfly.demo.ppflyactivitidemo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PpflyActivitiDemoApplicationTests {

    ProcessEngine processEngine = null; // 流程引擎对象

    @Test
    public void contextLoads() {
    }

    @Before
    public void initProcessEngine() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }

//    /**
//     * 查询正在执行的组任务列表
//     */
//    @Test
//    public void findGroupCandidate() {
//        // 任务ID
//        String taskId = "12545";
//        List<IdentityLink> list = processEngine.getTaskService()
//                .getIdentityLinksForTask(taskId);
//        if (list != null && list.size() > 0) {
//            for (IdentityLink identityLink : list) {
//                System.out.println("任务ID：" + identityLink.getTaskId());
//                System.out.println("流程实例ID："
//                        + identityLink.getProcessInstanceId());
//                System.out.println("用户ID：" + identityLink.getUserId());
//                System.out.println("工作流角色ID：" + identityLink.getGroupId());
//                System.out.println("#########################################");
//            }
//        }
//    }
//
//
//    /**
//     * 查询组任务1
//     */
//    @Test
//    public void findGroupTaskList1() {
//        List<String> candidateGroups = new ArrayList<>();
//        candidateGroups.add("wangwu");
//        List<Task> list = processEngine.getTaskService()//
//                .createTaskQuery()//
//                .taskCandidateGroupIn(candidateGroups)// 参与者，组任务查询
//                .list();
//        if (list != null && list.size() > 0) {
//            for (Task task : list) {
//                System.out.println("任务ID：" + task.getId());
//                System.out.println("任务的办理人：" + task.getAssignee());
//                System.out.println("任务名称：" + task.getName());
//                System.out.println("任务的创建时间：" + task.getCreateTime());
//                System.out.println("流程实例ID：" + task.getProcessInstanceId());
//                System.out.println("#######################################");
//            }
//        }
//    }
//
//
//    /**
//     * 查询组任务2
//     */
//    @Test
//    public void findGroupTaskList2() {
//        List<Task> list = processEngine.getTaskService()//
//                .createTaskQuery()//
//                .taskCandidateGroup("wangwu")// 参与者，组任务查询
//                .list();
//        if (list != null && list.size() > 0) {
//            for (Task task : list) {
//                System.out.println("任务ID：" + task.getId());
//                System.out.println("任务的办理人：" + task.getAssignee());
//                System.out.println("任务名称：" + task.getName());
//                System.out.println("任务的创建时间：" + task.getCreateTime());
//                System.out.println("流程实例ID：" + task.getProcessInstanceId());
//                System.out.println("#######################################");
//            }
//        }
//    }
//
//
//    /**查询组任务*/
//    @Test
//    public void findGroupTaskList() {
//        // 任务办理人
//        String candidateUser = "xiaoA";
//        List<Task> list = processEngine.getTaskService()//
//                .createTaskQuery()//
//                .taskCandidateUser(candidateUser)// 参与者，组任务查询
//                .list();
//        if (list != null && list.size() > 0) {
//            for (Task task : list) {
//                System.out.println("任务ID：" + task.getId());
//                System.out.println("任务的办理人：" + task.getAssignee());
//                System.out.println("任务名称：" + task.getName());
//                System.out.println("任务的创建时间：" + task.getCreateTime());
//                System.out.println("流程实例ID：" + task.getProcessInstanceId());
//                System.out.println("#######################################");
//            }
//        }
//    }



}
