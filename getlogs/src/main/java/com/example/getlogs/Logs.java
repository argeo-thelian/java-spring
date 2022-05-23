package com.example.getlogs;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "logs", value = "/logs")
public class Logs extends HttpServlet {
    private String message;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String filestr = request.getParameter("file");
        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String filePath = "/opt/IBM/logs/" + filestr + ".log";
            File downloadFile = new File(filePath);
            inputStream = new FileInputStream(downloadFile);

            String relativePath = getServletContext().getRealPath("");

            ServletContext context = getServletContext();

            String mimeType = context.getMimeType(filePath);
            if (mimeType == null) {
                //Set to binary type if MIME mapping not found
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            outputStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outputStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null){
                inputStream.close();
            }
            if (outputStream != null){
                outputStream.close();
            }

        }





        /*
        String id = request.getParameter("id");
        System.out.println("id: " + id);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        switch (id) {
            case "1":
                message = "Sisact.log";
                break;
            case "2":
                message = "Sisact2.log";
                break;
            case "3":
                message = "Sisact3.log";
                break;
            default:
                message = "Sisact.log";
                break;
        }
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
        */

    }

}
