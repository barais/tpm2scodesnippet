/**
 * EasyBeans
 * Copyright (C) 2006 Bull S.A.S.
 * Contact: easybeans@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * --------------------------------------------------------------------------
 * $Id: ClientServletStateless.java 5369 2010-02-24 14:58:19Z benoitf $
 * --------------------------------------------------------------------------
 */

package org.servletclient_ejb3;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.istic.ejb.BusinessInterface;

/**
 * Servlet's client for the stateless session bean.
 */
public class ClientServletStateless extends HttpServlet {

    /**
     * Serializable class uid.
     */
    private static final long serialVersionUID = 6893863749912962928L;

    /**
     * Called by the server (via the service method) to allow a servlet to
     * handle a GET request.
     * @param request an HttpServletRequest object that contains the request the
     *        client has made of the servlet
     * @param response an HttpServletResponse object that contains the response
     *        the servlet sends to the client
     * @throws IOException if an input or output error is detected when the
     *         servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>");
        out.println("Client of stateless session bean</title>");
        out.println("</head>");
        out.println("<body>");

        // no operation ? displays button for hello world and calculator
        String operation = request.getParameter("operation");
        if (operation != null) {
            if (operation.equals("helloWorld")) {
                displayHelloWorld(out);
            } 
        }
        out.println("<hr width=\"80%\"/>");
        displayDefault(out);


        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    /**
     * Call HelloWorld method.
     * @param out the given writer
     */
    private void displayHelloWorld(final PrintWriter out) {
        out.println("Calling helloWorld() method");
        out.println("<br>");
        try {
            getBean().helloWorld();
            out.println("helloWorld() method called OK.");
        } catch (Exception e) {
            displayException(out, "Cannot call helloworld on the bean", e);
        }
    }

    /**
     * By default, call helloWorld method.
     * @param out the given writer
     */
    private void displayDefault(final PrintWriter out) {
        out.println("<form method=get action=\"\" enctype=\"multipart/form-data\">");
        out.println("<p><input type=hidden name=\"operation\" value=\"helloWorld\"></p>");
        out.println("<p><input type=submit value=\"hello world !\"></p>");
        out.println("</form>");
    }


    /**
     * If there is an exception, print the exception.
     * @param out the given writer
     * @param errMsg the error message
     * @param e the content of the exception
     */
    private void displayException(final PrintWriter out, final String errMsg, final Exception e) {
        out.println("<p>Exception : " + errMsg);
        out.println("<pre>");
        e.printStackTrace(out);
        out.println("</pre></p>");
    }

    /**
     * Lookup the stateless bean and gets a reference on it.
     * @return the stateless bean business interface.
     * @throws Exception if the bean cannot be retrieved.
     */
    private BusinessInterface getBean() throws Exception {
        Context initialContext = new InitialContext();
        Object o = initialContext.lookup("JPA2Bean");

        if (o instanceof BusinessInterface) {
        	BusinessInterface statelessBean = (BusinessInterface) o;
            return statelessBean;
        }
        throw new Exception("Cannot cast object into StatelessRemote");

    }

}
