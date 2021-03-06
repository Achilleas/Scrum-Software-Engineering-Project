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

//state change handler function
function stateHandler2() {
	if(xmlHttp.readyState == 4)	{ // when request is complete
		if (xmlHttp.responseText != "") {

			var delay = 5000; //Your delay in milliseconds
			var URL = "../servlets/profile";

			document.write(xmlHttp.responseText);
			document.write("You will be redirect to <a href='" + URL + "'>profile page</a> in 5 seconds.");
			setTimeout(function(){ window.location = URL; }, delay);
		}
	}
}

//get table of information from server
function checkSession() {
	xmlHttp = genXmlHttp();
	if (xmlHttp == null) {
		alert("Your browser does not support AJAX");
		return;
	}

	var url = "/servlets/session";
	url = url + "?sid=" + Math.random(); // Use random number to avoid using a cached file

	xmlHttp.onreadystatechange = stateHandler2;
  
  	xmlHttp.open("GET", url, true);
  	xmlHttp.send(null);
}