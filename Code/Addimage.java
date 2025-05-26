package img;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.sql.*;

@WebServlet("/UploadImages")
@MultipartConfig
public class Addimage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the file part from the request
        System.out.println("in do post method done");
        Part file = request.getPart("image");
        String imageFileName = file.getSubmittedFileName();
        System.out.println("Selected image file: " + imageFileName);
        String uploadPath = "C:/Users/Psps0/eclipse-workspace/Wildlife/build/images/" + imageFileName;
        System.out.println(uploadPath);
        try (FileOutputStream fos = new FileOutputStream(uploadPath);
             InputStream is = file.getInputStream()) {
            byte[] data = new byte[is.available()];
            is.read(data);
            fos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Delete temporary file
            System.out.println("finished");
        }
        Connection conn=null;
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/wildlife","root","Psps@2003");
        	
        	PreparedStatement stmt;
        	String query="insert into upload(imageFileName) values(?)";
        	stmt=conn.prepareStatement(query);
        	stmt.setString(1, imageFileName);
        	
        	int rows=stmt.executeUpdate();
        	if(rows>0) {
        		System.out.println("Image added");
        		
        	}
        	else {
        		System.out.println("Failed");
        	}
        	
        }catch(Exception e) {
        	System.out.println(e);
        }
        response.sendRedirect("Display.jsp");
    }
    
}
