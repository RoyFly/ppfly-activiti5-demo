package com.ppfly.demo.common.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ppfly.demo.common.entities.PlainModel;
import com.ppfly.demo.common.exception.DeployException;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.explorer.util.XmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * 模型管理
 * Created by ppfly on 2017/9/19.
 */
@Controller
@RequestMapping("/models")
public class ModelController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProcessEngine processEngine;
    @Autowired
    ObjectMapper objectMapper;

    /**
     * 捕获 "发布模型为流程定义" 异常
     *
     * @return
     */
    @ExceptionHandler({DeployException.class})
    public ModelAndView handleDeployException(Exception exception) {
        log.info(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView("fail");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

    /**
     * 新建一个空模型
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/createNewModel")
    public String createNewModel(org.springframework.ui.Model spmvcModel, @Valid PlainModel plainModel, BindingResult result) throws UnsupportedEncodingException {
        if (result.hasErrors()) {
            spmvcModel.addAttribute("MSG", "出错啦！");
            return "createNewModel";//新建空模型界面
        }
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //初始化一个空模型
        Model model = repositoryService.newModel();
        //设置一些默认信息
        int revision = 1;
        Random random = new Random();
        int randomNum = random.nextInt(100);
        String key = "process" + randomNum;

        String name = plainModel.getName();
        String description = plainModel.getDescription();
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());

        repositoryService.saveModel(model);//act_re_model
        String modelId = model.getId();
        log.info("model新增Id：" + modelId);

        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(modelId, editorNode.toString().getBytes("utf-8"));
        return "success";
    }

    /**
     * 删除模型
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteModel(@PathVariable("id") String id) {
        try {
            RepositoryService repositoryService = processEngine.getRepositoryService();
            repositoryService.deleteModel(id);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseEntity<String> responseEntity = new ResponseEntity<>("fail", HttpStatus.OK);
            return responseEntity;
        }
        ResponseEntity<String> responseEntity = new ResponseEntity<>("success", HttpStatus.OK);
        return responseEntity;
    }

    /**
     * 发布模型为流程定义
     *
     * @param modelId
     * @return
     * @throws Exception
     */
    @RequestMapping("{modelId}/deployment")
    public String deploy(@PathVariable("modelId") String modelId) throws Exception {
        //获取模型
        RepositoryService repositoryService = processEngine.getRepositoryService();
        byte[] modelEditorSourceBytes = repositoryService.getModelEditorSource(modelId);
        byte[] modelEditorSourceExtraBytes = repositoryService.getModelEditorSourceExtra(modelId);

        if (modelEditorSourceBytes == null) {
            throw new DeployException("模型数据为空，请先设计流程并成功保存，再进行发布。");
        }

        JsonNode modelNode = new ObjectMapper().readTree(modelEditorSourceBytes);

        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (bpmnModel.getProcesses().size() == 0) {
            throw new DeployException("数据模型不符要求，请至少设计一条主线流程。");
        }
        byte[] bpmnModelBytes = new BpmnXMLConverter().convertToXML(bpmnModel);

        Model model = repositoryService.getModel(modelId);
        //发布流程
        String processName = model.getName() + ".bpmn20.xml";
//        String png = model.getName() + ".png"; //todo 部署时只指定bpmn一个文件，activiti会在部署时解析bpmn文件内容自动生成png图片资源，但是user task 会中文乱码
        Deployment deployment = repositoryService.createDeployment()
                .name(model.getName())
                .addString(processName, new String(bpmnModelBytes, "UTF-8"))
                .deploy();
        model.setDeploymentId(deployment.getId());
        repositoryService.saveModel(model);//update
        return "success";
    }

    /**
     * 导入现有流程文件
     *
     * @param uploadfile
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public void deployUploadedFile(@RequestParam("file") MultipartFile uploadfile) {
        InputStreamReader in = null;
        RepositoryService repositoryService = processEngine.getRepositoryService();
        try {
            try {
                String fileName = uploadfile.getOriginalFilename();
                if (fileName.endsWith(".bpmn20.xml") || fileName.endsWith(".bpmn")) {
                    XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
                    in = new InputStreamReader(new ByteArrayInputStream(uploadfile.getBytes()), "UTF-8");
                    XMLStreamReader xtr = xif.createXMLStreamReader(in);
                    BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

                    if (bpmnModel.getMainProcess() == null || bpmnModel.getMainProcess().getId() == null) {
//                        notificationManager.showErrorNotification(Messages.MODEL_IMPORT_FAILED,
//                                i18nManager.getMessage(Messages.MODEL_IMPORT_INVALID_BPMN_EXPLANATION));
                        System.out.println("err1");
                    } else {

                        if (bpmnModel.getLocationMap().isEmpty()) {
//                            notificationManager.showErrorNotification(Messages.MODEL_IMPORT_INVALID_BPMNDI,
//                                    i18nManager.getMessage(Messages.MODEL_IMPORT_INVALID_BPMNDI_EXPLANATION));
                            System.out.println("err2");
                        } else {

                            String processName = null;
                            if (StringUtils.isNotEmpty(bpmnModel.getMainProcess().getName())) {
                                processName = bpmnModel.getMainProcess().getName();
                            } else {
                                processName = bpmnModel.getMainProcess().getId();
                            }
                            Model modelData;
                            modelData = repositoryService.newModel();
                            ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
                            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processName);
                            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
                            modelData.setMetaInfo(modelObjectNode.toString());
                            modelData.setName(processName);

                            repositoryService.saveModel(modelData);

                            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
                            ObjectNode editorNode = jsonConverter.convertToJson(bpmnModel);

                            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
                        }
                    }
                } else {
//                    notificationManager.showErrorNotification(Messages.MODEL_IMPORT_INVALID_FILE,
//                            i18nManager.getMessage(Messages.MODEL_IMPORT_INVALID_FILE_EXPLANATION));
                    System.out.println("err3");
                }
            } catch (Exception e) {
                String errorMsg = e.getMessage().replace(System.getProperty("line.separator"), "<br/>");
//                notificationManager.showErrorNotification(Messages.MODEL_IMPORT_FAILED, errorMsg);
                System.out.println("err4");
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
//                    notificationManager.showErrorNotification("Server-side error", e.getMessage());
                    System.out.println("err5");
                }
            }
        }
    }
}
