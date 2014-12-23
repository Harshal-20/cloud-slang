/*******************************************************************************
* (c) Copyright 2014 Hewlett-Packard Development Company, L.P.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Apache License v2.0 which accompany this distribution.
*
* The Apache License is available at
* http://www.apache.org/licenses/LICENSE-2.0
*
*******************************************************************************/

package org.openscore.lang.compiler;

import org.openscore.lang.compiler.configuration.SlangCompilerSpringConfig;
import org.openscore.lang.entities.CompilationArtifact;
import org.openscore.api.ExecutionPlan;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/*
 * Created by orius123 on 05/11/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SlangCompilerSpringConfig.class)
public class CompileFlowWithMultipleStepsTest {

    @Autowired
    private SlangCompiler compiler;

    @Test
    public void testCompileFlowBasic() throws Exception {
        URI flow = getClass().getResource("/flow_with_multiple_steps.yaml").toURI();
        URI operation = getClass().getResource("/operation.yaml").toURI();

        Set<File> path = new HashSet<>();
        path.add(new File(operation));

        CompilationArtifact compilationArtifact = compiler.compileFlow(new File(flow), path);
        ExecutionPlan executionPlan = compilationArtifact.getExecutionPlan();
        Assert.assertNotNull("execution plan is null", executionPlan);
        Assert.assertEquals("there is a different number of steps than expected", 10, executionPlan.getSteps().size());
        Assert.assertEquals("execution plan name is different than expected", "basic_flow", executionPlan.getName());
        Assert.assertEquals("the dependencies size is not as expected", 3, compilationArtifact.getDependencies().size());
    }
}