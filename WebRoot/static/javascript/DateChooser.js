window.onload = init;
 
// Global variables for the currently selected year, month and day
var selectedYear;   // 4-digit year
var selectedMonth;  // 1 to 12 for Jan to Dec
var selectedDay;    // 1 to 31
 
// The "onload" handler, runs after the page is fully loaded.
function init() {
	setToday();      // Set global variables
	updateYearOption();
	updateDisplay();
	
	document.getElementById("year").onchange = function() {
		selectedYear=this.value;
		// In case the current day is no longer valid for non-leap year,
		// e.g., 29 Feb 2001. Set to the last year of the month
		updateDayDisplay();
	}

	document.getElementById("month").onchange = function() {
		selectedMonth=this.value;
		// In case the current day is no longer valid, e.g., 31 in Feb.
		// Set to the last year of the month
		updateDayDisplay();
	}

	document.getElementById("day").onchange = function() {
		selectedDay=this.value;
	}

	/* Unused function
	document.getElementById("btnNow").onclick = function() {
		setToday();
		updateDisplay();
	} */
}
 
// Set global variable selectedYear, selectedMonth and selectedDay
// to today.
function setToday() {
   var now = new Date();
   selectedYear = now.getFullYear();   // 4-digit year
   selectedMonth = now.getMonth() + 1; // 1 to 12 for Jan to Dec
   selectedDay = now.getDate();        // 1 to 31
}
 
// Update the year, month, day and day-in-week display according
// to the selected values.
function updateDisplay() {
   // Set the value of text fields and select the correct options
   document.getElementById("year").value = selectedYear;
   updateMonthDisplay();
   updateDayDisplay();
}
 
function updateMonthDisplay() {
   document.getElementById("month" + selectedMonth).selected = true;
}
 
function updateDayDisplay() {
   var elm = document.getElementById("day");
   elm.innerHTML = "";
   var daysInMonth = getDaysInMonth(selectedYear, selectedMonth);
   // The selectedDay is no longer valid. Set to the last day of month
   if (selectedDay > daysInMonth) {
      selectedDay = daysInMonth;
   }
   var options = "";
   for (var day = 1; day <= daysInMonth; day++) {
      options += "<option value='" + day + "'";
      if (day == selectedDay) {
         options += " selected";
      }
      options += ">" + day + "</option>";
   }
   elm.innerHTML = options;
}

function updateYearOption() {
	var now = new Date();
    thisYear = now.getFullYear(); 
	
	var elm = document.getElementById("year");
	elm.innerHTML = "";

	var options = "";
	for (var year = 1900; year <= thisYear; year++) {
		options += "<option value='" + year + "'";
		if (year == selectedYear) {
			options += " selected";
		}
		options += ">" + year + "</option>";
	}
	elm.innerHTML = options;
}

// Unused function
function updateDayInWeekDisplay() {
   document.getElementById("dayInWeek").value
      = getDayInWeek(selectedYear, selectedMonth, selectedDay);
}
