/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package com.sun.tools.xjc.servlet;

import java.io.IOException;

import javax.servlet.ServletException;

import com.sun.xml.bind.webapp.LongProcessServlet;

/**
 * Launches {@link Compiler} from the form input.
 * 
 * @author
 * 	Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public class XJCServlet extends LongProcessServlet {
    
    protected void run() throws ServletException, IOException {
        // check if the browser supports session
        if( request.getSession(false)==null ) {
            response.sendRedirect("cookieDisabled.jsp");
            return;
        }
        
        super.run();
    }
    
    protected Thread createTask() throws ServletException, IOException {
        return Compiler.get(request);
    }

    protected void renderResult( Thread task ) throws ServletException, IOException {
        Compiler compiler = (Compiler)task;
        
        // forward to the result
        if( compiler.getZipFile()==null )
            response.sendRedirect("compileError.jsp");
        else
            response.sendRedirect("compileSuccess.jsp");
    }
    
    
    

    protected String getProgressTitle() {
        return "Compiling your schema";
    }

    protected String getProgressMessage() {
        return "your browser will be redirected to the result page once the compilation is completed";
    }

}