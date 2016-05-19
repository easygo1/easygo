package com.easygo.control;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;


@WebServlet("/upservlet")
public class UploadServlet extends HttpServlet implements javax.servlet.Servlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public UploadServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

        // print query string or body parameter
        // response.getWriter().print(new String(request.getParameter("qmsg").getBytes("ISO-8859-1"), "GBK"));


        SmartUpload smartUpload = new SmartUpload();
        try {
        	
            smartUpload.initialize(this.getServletConfig(), request, response);
            smartUpload.upload();
            com.jspsmart.upload.File smartFile = smartUpload.getFiles().getFile(0);
            if (!smartFile.isMissing()) {
                String saveFileName = "/data/" + smartFile.getFileName();
                smartFile.saveAs(saveFileName, smartUpload.SAVE_PHYSICAL);

                // print multipart parameter
                response.getWriter().print(" ok: " + saveFileName);
                //+ ", msg: " + smartUpload.getRequest().getParameter("msg"));
            } else {
                response.getWriter().print("missing...");
            }
        } catch (Exception e) {
            response.getWriter().print(e);
        }
    }
	

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
