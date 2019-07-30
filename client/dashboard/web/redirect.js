var restartInterval = 5000; //5 seconds
function redirectPage() {
	var retryInterval = 30000; //1 minute
	var retryErrorInterval = 10000 //10 seconds
	xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {	
	 if(xhr.readyState == 4) {
		 if(xhr.status == 200){
			document.getElementById('jeesl-uhd').src = 'http://192.168.1.171:8080/jeesl/style/dashboard/uhd';
			window.restartInterval = retryInterval;	
		  }
		  else{
			document.getElementById('jeesl-uhd').src = 'page-not-found.jpeg';
			window.restartInterval = retryErrorInterval; 
		  }
	  }
	};
	xhr.open('GET', 'http://192.168.1.171:8080/jeesl/style/dashboard/uhd', true);
	xhr.send();
	setTimeout(redirectPage, window.restartInterval);
};	
//reload page on start					
window.onload =  setTimeout(redirectPage,window.restartInterval);    
