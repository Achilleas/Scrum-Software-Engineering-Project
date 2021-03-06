var xmlHttp;

//XMLHttpRequest object generator
function genXmlHttp() {
	var xmlHttp;
	
	if (window.XMLHttpRequest) {
		// for IE7+, Firefox, Chrome, Opera, Safari
	  	xmlHttp=new XMLHttpRequest();
	} else {
		// for IE6, IE5
		xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	return xmlHttp;
}

// state change handler function
function stateHandler() {
	if(xmlHttp.readyState == 4)	{ // when request is complete
    	if (xmlHttp.responseText == "true") {
    		username_available = true;
    		document.getElementById('username-available').style.display = 'inline';
    		document.getElementById('username-error').style.display = 'none';
    	} else {
    		username_available = false;
    		document.getElementById('username-available').style.display = 'none';
    		document.getElementById('username-error').style.display = 'inline';
    	}
    }
}

// get table of information from server
function userCheck() {
	
	var username;
	
	username = document.getElementById("username").value;		// input value for game name field
		
	xmlHttp = genXmlHttp();
	if (xmlHttp == null) {
		alert("Your browser does not support AJAX");
		return;
	}
	var url = "/servlets/user-check?" + "username=" + username;	
	url = url + "&sid=" + Math.random(); // Use random number to avoid using a cached file

    xmlHttp.onreadystatechange = stateHandler;
    
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
}
