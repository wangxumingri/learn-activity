package com.wxss.activity;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;

public class App {
    public static void main(String[] args) {
        // 初始化表
//         init();
//         部署流程
//         deployProcess();

//         启动流程
//        startProcess();

        // 查询用户待执行任务
//        流程定义ID: myProcess_1:2:12504
//流程实例ID: 15001
//当前活动ID: null;
        System.out.println("Mark 的任务:");
        queryTaskByUser("Mark","myProcess_1");
        System.out.println("Jane 的任务:");
        queryTaskByUser("Jane","myProcess_1");
//        System.out.println("Mark完成任务...");
        completeTask();
//
//        System.out.println("-----");
//
//        System.out.println("Mark 的任务:");
//        queryTaskByUser("Mark","myProcess_1");
//        System.out.println("Jane 的任务:");
//        queryTaskByUser("Jane","myProcess_1");

    }


    public static void completeTask(){
        TaskService taskService = getProcessEngine().getTaskService();
        // 根据任务ID完成任务
//        taskService.complete("20005");
        taskService.complete("22502");
    }
    /**
     * 根据流程定义Key和负责人查询 待处理任务
     * @param user
     * @param processDefKey
     */
    public static void queryTaskByUser(String user,String processDefKey){
        TaskService taskService = getProcessEngine().getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey(processDefKey) // 要查询的流程
                .taskAssignee(user) // 该流程下，指定用户
                .list();
        if (taskList != null && taskList.size() >0){
            for (Task task : taskList) {
                System.out.println("任务Id:" + task.getId());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务:" + task.getOwner());
                System.out.println("任务所属流程ID:" + task.getProcessInstanceId());
            }
        }
    }

    public static ProcessEngine getProcessEngine(){
        //创建ProcessEngineConfiguration对象
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti-cfg.xml");
        //创建ProcessEngine对象
        return configuration.buildProcessEngine();
    }

    /**
     * 流程定义ID: myProcess_1:1:7504
     * 流程实例ID: 10001
     * 当前活动ID: null
     */
    public static void startProcess(){
        RuntimeService runtimeService = getProcessEngine().getRuntimeService();
        // 根据流程定义Key启动流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_1");
        System.out.println("流程定义ID: "+processInstance.getProcessDefinitionId());
        System.out.println("流程实例ID: "+processInstance.getProcessInstanceId());
        System.out.println("当前活动ID: "+processInstance.getActivityId());
    }

    /**
     * 部署流程
     */
    public static void deployProcess(){
        RepositoryService repositoryService = getProcessEngine().getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .name("测试部署1")
                .addClasspathResource("MerchantAudit.bpmn")
                .addClasspathResource("MerchantAudit.png")
                .deploy();

        System.out.println("Id: "+deploy.getId());
        System.out.println("DeploymentTime: "+deploy.getDeploymentTime());
        System.out.println("Key: "+deploy.getKey());
        System.out.println("Name: "+deploy.getName());
    }
    /**
     * 初始化表
     */
    private static void init() {
        //创建ProcessEngineConfiguration对象
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti-cfg.xml");
        //创建ProcessEngine对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        System.out.println("processEngine = " + processEngine);
    }


}
