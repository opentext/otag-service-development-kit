/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.handlers;

import com.opentext.otag.sdk.client.v3.ServiceClient;
import com.opentext.otag.sdk.types.v3.management.DeploymentResult;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * All AppWorks services must supply a <strong>single</strong> method that lets the
 * Gateway know they have completed their startup sequence, successfully or not, as the case
 * may be. This is important as we do not want administrators or other Gateway
 * API users from interfering in a services startup sequence, and we want to know
 * if your service started correctly or not anyway to ease development and debugging.
 * We ignore this annotation if it is found on an abstract type so ensure you provide
 * a concrete implementation.
 * <p>
 * The {@link DeploymentResult} type can be used in conjunction with
 * {@link ServiceClient#completeDeployment} to fulfil the service's responsibility.
 * <p>
 * AppWorks services will be inspected at deploy time to ensure they meet the
 * requirement imposed by this annotation and enforced by the management agent.
 * As mentioned tools are supplied to meet this commitment. If the annotated
 * method does not relay the result of the deployment to the Gateway, then it will not
 * be available.
 * <p>
 * An annotation is used in place of some handler interface or other marker type as
 * we really wouldn't know when to call a startup complete method supplied in
 * some type, its up the service itself to decide this.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.1
 *
 * @see com.opentext.otag.sdk.types.v3.management.DeploymentResult
 * @see ServiceClient#completeDeployment(DeploymentResult)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AWServiceStartupComplete {
}
