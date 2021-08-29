(function(){
	var pageOrchestrator = new PageOrchestrator();
	var marketingAnswers, statisticalAnswers;
	
	window.addEventListener("load", () => {
		user = sessionStorage.getItem("username");
		if (user == null) {
			window.location.href = "index.html";
		} else {
				pageOrchestrator.start();
			}
	}, false);
	
	document.getElementById("backToMarketing_button").addEventListener('click', (e) => {
		e.preventDefault();
		pageOrchestrator.start();
	});

	document.getElementById("goToStatistics_button").addEventListener('click', (e) => {
		e.preventDefault();
		pageOrchestrator.showStatsPart();
	});
	
	document.getElementById("submitAnswers_button").addEventListener('click', (e) => {
		e.preventDefault();
		
	});
	
	/*
	document.getElementById("logoutButton").addEventListener('click', (e) => {
		window.sessionStorage.removeItem('username');
	});
	*/
	
	function PageOrchestrator() {
		marketingAnswers = new MarketingAnswers();
		statisticalAnswers = new StatisticalAnswers();

		this.start = function() {
			marketingAnswers.show();
			statisticalAnswers.hide();
		}
		
		this.showStatsPart = function() {
			marketingAnswers.hide();
			statisticalAnswers.show();
		}
	}
	
	function MarketingAnswers() {
		
		this.hide = function() {
			document.getElementById("marketingAnswers_div").style.display = "none";
		}
		
		this.show = function() {
			document.getElementById("marketingAnswers_div").style.display = "block";
		}
		
	}
	
	function StatisticalAnswers() {
		
		this.hide = function() {
			document.getElementById("statisticalAnswers_div").style.display = "none";
		}
		
		this.show = function() {
			document.getElementById("statisticalAnswers_div").style.display = "block";
		}
		
	}
	
	
})
