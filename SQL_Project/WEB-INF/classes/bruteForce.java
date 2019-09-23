/*
* Name: Cadilnis Chau
* Course: CNT4714
* Project 4 - Servlets
*/

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class bruteForce extends HttpServlet {

    int flag = 0;

    public bruteForce() {
    }

    protected void bruteForce(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        //parameter get 
        String input = request.getParameter("userInput");
        input = input == null ? "" : input;

        //hard-code the HTML tags
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            out.println("<title>Remote Database Management System</title>");
            out.println("<style type=\"text/css\">");
            out.println("body { text-align: center;  color: white;}");
            out.println("textarea {background-color: gold;}");
            out.println("table.error{ margin: auto; border-width: 3px; }");
            out.println("td.error{  background-color: red; border-width: 1px; font-size: larger; color: white; }");
            out.println("table.update{ margin: auto; border-width: 3px; }");
            out.println("td.update{ background-color: lime; border-width: 5px; font-size: larger;  color: black;  font-weight:bold   }");
            out.println("td.update2{  background-color: lime; border-width: 1px; font-size: larger; font-weight:bold   color: white;   }");
            out.println("table.result{  margin: auto; border-width: 3px; }");
            out.println("td.oddRow{  background-color: silver;  border-width: 1px; color: black; }"); //Odd rows 
            out.println("td.evenRow{  background-color: white; border-width: 1px;  color: black; }");  //Even Rows 
            out.println("th.result{  background-color: red;  border-width: 1px; font-weight: bold; color: black; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body bgcolor=\"Blue \">");
            out.println("<h1>Welcome to the Project 4 Remote Database Management System</h1>");
            out.println("<hr>");
            out.println("<p>You are connected to the Project 4 database.<br>");
            out.println("Please enter any valid SQL query or update statement.<br>");
            out.println("If no query/update command is given the Execute button will display all suppliers information in the database.<br>");
            out.println("All execution results will appear below.</p>");
            out.println("<form method=\"POST\" action=\"bruteForce\" name=\"bruteForce\">");
            out.println("<textarea id=\"inputArea\" name=\"userInput\" cols=\"80\" rows=\"10\">" + input + "</textarea>");
            out.println("<br>");
            out.println("<br/>");
            out.println("<input type=\"submit\" value=\"Execute Command\" name=\"execute\"></button>");
            out.println("<button type=\"button\" onclick=\"myFunction()\">Clear Form</button>");
            out.println("</form>");
            out.println("<script>");
            out.println("function myFunction(){ document.getElementById(\"inputArea\").value=\" \";}");
            out.println("</script>");
            out.println("<hr>");
            out.println("<h3>Database Results:</h3>");

            out.println(CreateResultsTable(input));

            flag = 1; //increment flag for trigger

            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close(); //exit 
        }
    }

    private boolean isCommandQuery(String command) {
        String[] lines = command.split("[\r\n]");

        for (String line : lines) {
            line = line.trim();
            
            //search for # condition
            if (!line.startsWith("#")) {
                return line.startsWith("select");
            }
        }

        return false;
    }

    private String CreateResultsTable(String input)
            throws SQLException {
        if (flag == 0) {
            //give back empty string at start
            return "";
        }
        
        //By default, if nohting is entered in the text-area, display supplier information
        if ((input == null) || (input.isEmpty())) {
            input = "select * from suppliers";
        }
        return executeQuery(input);
    }

    private String executeQuery(String command)
            throws SQLException {
        //attempt connection 
        dbConnection dbConnection = new dbConnection();
        try {
            //hard code the credentials, as advised from professor
            dbConnection.connect("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/project4", "root", "root");
        } catch (Exception e) {
            //generic error print 
            return formatError(e);
        }
        
        boolean commandTypeIsQuery = isCommandQuery(command.toLowerCase());
        ResultSet result = null;
        int updateResult = 0;

        flag = 1;
        
        
        
        int returnNone;
        if (commandTypeIsQuery) {
            result = dbConnection.executeQuery(command);
        } else {
            try {
                String suppliersSnum = "";
                boolean updateSupplier = false;

                //Filter for any capital statements and then analyze 
                //parse through strings
                if (command.toLowerCase().contains("insert into shipments")) {
                    int first = command.indexOf("(");
                    int last = command.indexOf(")");
                    String temp = command.substring(first + 1, last);
                    temp = temp.replaceAll("'", "");
                    temp = temp.replaceAll(" ", "");
                    String[] brokenString = temp.split(",");
                
                //brute force logic 
                    for (String word : brokenString) {
                        try {
                            if (Integer.valueOf(word).intValue() >= 100) {
                                updateSupplier = true;
                            }
                        } catch (NumberFormatException e) {
                            if (word.startsWith("S")) {
                                suppliersSnum = word;
                            }
                        }
                    }
                }
                
                //Condition checking 
                //look for keywords "update shipments" and follow the statements here 
                if (command.toLowerCase().contains("update shipments")) {
                    ResultSet updateTest = null;
                    updateTest = dbConnection.executeQuery("select * from shipments where quantity < 90 and pnum = 'P3'");
                    
                    //when found, update the following and reset flag 
                    if (updateTest.next()) {
                        updateTest.beforeFirst();
                        updateSupplier = true;
                    }
                }

                //When updating suppliers
                //restart counter (returnNone)and initialize update 
                if (updateSupplier) {
                    returnNone = 0;
                    int updatingRequest = 0;
                    
                    //Create table before shipments 
                    returnNone = dbConnection.executeUpdate("create table beforeShipments like shipments");
                    returnNone = 0;
                    
                    //update the table from command 
                    returnNone = dbConnection.executeUpdate("insert into beforeShipments select * from shipments");
                    returnNone = 0;
                    
                    //update status 
                    updateResult = dbConnection.executeUpdate(command);
                    updatingRequest = dbConnection.executeUpdate("update suppliers set status = status + 5 where snum in (select distinct snum from shipments left join beforeShipments using (snum, pnum, jnum, quantity) where beforeShipments.snum is null);");
                    
                    //status
                    returnNone = dbConnection.executeUpdate("DROP TABLE beforeShipments");
                    return formatUpdateResult(updateResult, updatingRequest);
                }
                
                //finish updating with updateResult through connection 
                updateResult = dbConnection.executeUpdate(command);
                return formatUpdateResult(updateResult);
            } catch (SQLException ex) {
                //print generic error code
                return formatError(ex);
            }
        }

        try {
            return formatQueryResult(result);
        } catch (SQLException e) {
            return formatError(e);
        } finally {
            try {
                result.close();
            } catch (SQLException e) {
                Logger.getLogger(bruteForce.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private String formatQueryResult(ResultSet result)
        throws SQLException {
        
        StringBuilder newString = new StringBuilder();
        ResultSetMetaData metaData = result.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        //rectify the tables by appending results 
        newString.append("<table class=\"result\" border=\"2\">");
        newString.append("<tr>");

        for (int i = 1; i <= columnCount; i++) {
            newString.append("<th class=\"result\">");
            newString.append(metaData.getColumnName(i));
            newString.append("</th>");
        }

        while (result.next()) {
            newString.append("<tr>");
            for (int i = 1; i <= columnCount; i++) {
                newString.append("<td class=\"" + (result.getRow() % 2 == 0 ? "evenRow" : "oddRow") + "\">");
                Object obj = result.getObject(i);

                if (obj == null) {
                    newString.append("<i>NULL</i>");
                } else {
                    newString.append(obj.toString());
                }

                newString.append("</td>");
            }
            newString.append("</tr>");
        }

        newString.append("</table>");

        return newString.toString();
    }

    private String formatUpdateResult(int updateResult) {
        return "<table class=\"update\" border=\"2\"> <tr> <td class=\"update\"> The statement executed successfully.<br />" + updateResult + " row(s) affected.<br/>" + " </td>" + "</tr>" + " </table>";
    }

    private String formatUpdateResult(int updateResult, int updatingRequest) {
        return "<table class=\"update\" border=\"2\"> <tr> <td class=\"update\"> The statement executed successfully.<br />" + updateResult + " row(s) affected.<br/>" + " </td>" + " </tr>" + " <tr>" + " <td class=\"update2\"><strong>" + "  Business bruteForce Detected - Updating Supplier Status<br /><br />" + "  Businesss Logic updated " + updatingRequest + " supplier status marks.</strong>" + " </td>" + " </tr>" + "  </table>";
    }

    private String formatError(Exception e) {
        return "<table class=\"error\" border=\"2\"> <tr> <td class=\"error\">  <b>Error executing the SQL statement:</b><br />" + e.getMessage() + " " + " </td>" + "</tr>" + "</table>";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            bruteForce(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(bruteForce.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            bruteForce(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(bruteForce.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
