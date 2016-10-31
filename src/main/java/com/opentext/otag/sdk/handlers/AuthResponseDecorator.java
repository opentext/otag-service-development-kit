/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.handlers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An {@link AuthRequestHandler} implementation that can be used to decorate
 * auth responses returned from the Gateway. If the annotated handler is being
 * used as the primary auth mechanism in the Gateway then this annotation is ignored.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.1
 *
 * @see AuthRequestHandler
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AuthResponseDecorator {
}
