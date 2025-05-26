package img;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Display
 */
@WebServlet("/Display")
public class Display extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Display() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		System.out.println("in do post method done");
		String imageId=request.getParameter("imageId");
		int id=Integer.parseInt(imageId);
		Connection conn=null;
        int imgId=0;
        String imgFileName=null;
		try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/wildlife","root","Psps@2003");
        	
        	Statement stmt;
        	String query="select * from upload";
        	stmt=conn.createStatement();
        	ResultSet rs=stmt.executeQuery(query);
        	
        	while(rs.next()) {
        		if(rs.getInt("imageId")==id) {
        			imgId=rs.getInt("imageId");
        			imgFileName=rs.getString("imageFileName");
        		}
        	}
        	
        }catch(Exception e) {
        	System.out.println(e);
        }
		RequestDispatcher rd;
		request.setAttribute("id",String.valueOf(imgId));
		request.setAttribute("img",imgFileName);
		rd=request.getRequestDispatcher("Display.jsp");
		rd.forward(request, response);;
	}

}
