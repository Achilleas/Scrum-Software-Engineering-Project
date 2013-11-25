var xmlHttp;

var intervalFunc=setInterval(function(){
		refreshTable();
	},interval);

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
		if (xmlHttp.responseText != "") {
			var ele = document.getElementById('content');
			ele.innerHTML = xmlHttp.responseText;
		} else {
			clearInterval(intervalFunc);
		}
    }
}

// get table of information from server
function refreshTable() {
				
	xmlHttp = genXmlHttp();
	if (xmlHttp == null) {
		alert("Your browser does not support AJAX");
		return;
	}
	
	var url_final = url + "?sid=" + Math.random(); // Use random number to avoid using a cached file
	console.log(url_final);
	xmlHttp.onreadystatechange = stateHandler;
  
  	xmlHttp.open("GET", url_final, true);
  	xmlHttp.send(null);
}
