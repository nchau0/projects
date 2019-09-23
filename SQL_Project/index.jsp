<!-- Name: Cadilnis Chau
Course: CNT4714 
Project 4 - Servlets 
 -->

<!DOCTYPE html>

<html lang="en">
<head>
   <title>Project 4 Servlet</title>
   <meta charset="utf-8" />
   <meta http-equiv="refresh"
	content="0; url=http://localhost:8080/Project4">
   <style type="text/css">
    <!--
	    body{background-color: blue;}
	-->
	</style>
</head>

<body>
	
	<h1 style="text-align: center; color: white;">Welcome to the Project 4 Remote Database Management System</h1>
	
	<hr style="color: white;">
	
	<div id="middle" style="text-align: center; color: white;">

		<p>
			You are connected to the Project 4 database.<br>
			Please enter any valid SQL query or update statement.<br>
			If no query/update command is given the Execute button will display all suppliers information in the database.<br>
			All execution results will appear below.
		</p>	
		
		<form method="POST" action='logic' name="index">
			<textarea id="textarea" name="textarea" rows="10" cols="80" style="background-color: orange;"></textarea>
			<br/>
			<input type="submit" value="Execute Command" name="execute">
			<input type="reset" value="Clear Form" name="clear">
			<button type = "button" onclick = "myFunction()">Clear Form</button>
		</form>

		<script> function myFunction(){
			document.getElementById("textarea").value = "";
		</script>

	</div>
	
	<hr style="color: white;">
	
	<div id="footer" style="text-align: center; color: white;">
		<h3>Database Results:</h3>
		<%
			String results = "Run a Query";
			results = (String)request.getAttribute("results");
			PrintWriter mOut = response.getWriter();
			if(results == null){
				System.out.println(results);
				results = "";
			}
		%>
		
		<%= results %>
	</div>
</body>
</html>