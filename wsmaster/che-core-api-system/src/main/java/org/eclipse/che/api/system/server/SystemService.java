/*******************************************************************************
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.api.system.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.eclipse.che.api.core.ConflictException;
import org.eclipse.che.api.system.shared.dto.SystemStateDto;
import org.eclipse.che.dto.server.DtoFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * REST API for system state management.
 *
 * @author Yevhenii Voevodin
 */
@Api("/system")
@Path("/system")
public class SystemService {

    private final SystemManager manager;

    @Inject
    public SystemService(SystemManager manager) {
        this.manager = manager;
    }

    @POST
    @Path("/stop")
    @ApiOperation("Stops system services. Prepares system to shutdown")
    @ApiResponses({@ApiResponse(code = 204, message = "The system is preparing to stop"),
                   @ApiResponse(code = 409, message = "Stop has been already called")})
    public void stop() throws ConflictException {
        manager.stopServices();
    }

    @GET
    @Path("/state")
    @Produces("application/json")
    @ApiOperation("Gets current system state")
    @ApiResponses(@ApiResponse(code = 200, message = "The response contains system status"))
    public SystemStateDto getState() {
        return DtoFactory.newDto(SystemStateDto.class).withStatus(manager.getSystemStatus());
    }
}
