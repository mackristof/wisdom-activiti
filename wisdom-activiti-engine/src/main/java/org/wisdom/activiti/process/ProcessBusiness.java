package org.wisdom.activiti.process;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import java.io.InputStream;
import java.util.List;

public interface ProcessBusiness {

    List<ProcessDefinition> processes();

    boolean deleteProcess(String processDefinitionId);

    List<ProcessInstance> instances();

    List<ProcessInstance> instances(String key, String deployment, String id);

    boolean deleteInstance(String id);

    InputStream getDiagram(String processDefinitionId);

    List<ProcessDefinition> processesByKey(String processDefinitionId);

    ProcessDefinition processById(String processDefinitionId);
}