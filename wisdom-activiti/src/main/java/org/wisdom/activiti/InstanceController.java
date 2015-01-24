/*
 * #%L
 * Wisdom-Framework
 * %%
 * Copyright (C) 2013 - 2014 Wisdom Framework
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.wisdom.activiti;

import com.fasterxml.jackson.databind.JsonNode;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.felix.ipojo.annotations.Requires;
import org.wisdom.activiti.process.ProcessBusiness;
import org.wisdom.api.DefaultController;
import org.wisdom.api.annotations.*;
import org.wisdom.api.content.Json;
import org.wisdom.api.http.HttpMethod;
import org.wisdom.api.http.Result;
import org.wisdom.api.security.Authenticated;
import org.wisdom.api.templates.Template;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Your first Wisdom Controller.
 */
@Controller
@Path("/activiti")
@Authenticated("Monitor-Authenticator")
public class InstanceController extends DefaultController {

    /**
     * Injects a template named 'instances'.
     */
    @View("instances")
    Template instances;

    /**
     * Injects a template that detail instance.
     */
    @View("instance")
    Template instance;


    /**
     * Injects activiti repositoryService.
     */
    @Requires
    ProcessBusiness processBusiness;

    @Requires
    Json json;

    /**
     * The action method returning the welcome page. It handles
     * HTTP GET request on the "/" URL.
     *
     * @return the welcome page
     */
    @Route(method = HttpMethod.GET, uri = "/instances")
    public Result instances() {
        if (request().accepts("text/html")) {
            return ok(render(instances, "processIds", "All processes", "instances", processBusiness.instances()));
        } else {
            return ok(processBusiness.instances()).json();
        }
    }



    @Route(method = HttpMethod.DELETE, uri="/instance/{id}")
    public Result delete(@Parameter("id") String id){
        return ok(processBusiness.deleteInstance(id));
    }

    /**
     * The action returns details of a given instance process id.
     *
     * @param id of instance process
     * @return
     */
    @Route(method = HttpMethod.GET, uri = "/instance/{id}")
    public Result instance( @PathParameter("id") String id) {
        if (request().accepts("application/json")) {
            //return ok(processBusiness.instances(processKey, processDeployment, processId)).json();
        }
        final ProcessInstance processInstance = processBusiness.instanceById(id);
        final Map<String, Object> processVariables = processBusiness.getInstanceVariables(processInstance.getProcessInstanceId());
        final Task task = processBusiness.getcurrentTask(processInstance.getProcessInstanceId());
        Map<String,Object> mapDisplay = new HashMap<>();
        mapDisplay.put("instanceId", id);
        mapDisplay.put("processInstance",processInstance);
        mapDisplay.put("processVariables", processVariables);
        mapDisplay.put("currentTaskName", task.getName());
        return ok(render(instance,mapDisplay));
    }

    @Route(method = HttpMethod.PUT, uri = "/instance/{id}/variables")
    public Result setInstanceVariables( @PathParameter("id") String id, @NotNull @Body JsonNode param){
        if (request().accepts("application/json")) {
            final ProcessInstance processInstance = processBusiness.instanceById(id);
            Map<String , Object> variables = new HashMap<>();


            processBusiness.setInstanceVariables(processInstance.getProcessInstanceId(),variables);
            return ok();
        }
        return badRequest();
    }
}
