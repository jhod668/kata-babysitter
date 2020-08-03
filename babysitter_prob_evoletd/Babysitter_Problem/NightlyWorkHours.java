package Babysitter_Problem;

import java.time.Duration;

public class NightlyWorkHours {
	/**
	 *  @author Evolet Davidoff
	 */
	
	/** At first I thought that I would need a separate method to calculate the hourlyEarnings by doing hourlyEarnings = rate * hours;
	*   After writing out the algorithm, it made sense to calculate the payrate for each hour worked and then add that up to the totalNightlyEarnings
	*	with each iteration of the for loop. This eliminated the need for that first method and simplified the code.
	*
	*	I ended up implementing all of the constraints as 'if' statements to find if the employee was working within the correct hours.
	*	And also I test what Time range the current endTime falls under, to find the correct rate to apply for each hour worked.
	*
	*	The last part adds their pay for the current hour to the totalNightlyEarnings. This is repeated in a for loop for each hour worked.
	*/
	// Assumptions made: 
	// Employee will always work consecutive hours once started working.
	public static void main(String[] args) {
		
		int[] payRates = {12, 8, 16};
		Duration beginWorkT = Duration.parse("PT17H0M"); 
		Duration endWorkCutoffTAM = Duration.parse("PT28H0M");
		//Duration endWorkCutoffTAM = Duration.parse("PT22H0M");
		int nightlyWagesEarned = calculateNightlyWages(payRates,beginWorkT,endWorkCutoffTAM);
		System.out.println("Nightly wages earned: "+nightlyWagesEarned);
		testEqualWages();

	}
	public static int calculateNightlyWages(int[] payRates, Duration startTime,Duration endTime) {
		
			int totalNightlyEarnings = 0;
			int payRate = 0;
			int hoursWorked;
			
			Duration beginWorkTime = Duration.parse("PT17H0M");
			Duration nineOClockBedtime = Duration.parse("PT21H0M");
			Duration midnight = Duration.parse("PT24H0M");
			Duration endWorkCutoffTimeAM = Duration.parse("PT28H0M");
			
			// Duration's CompareTo method:
			// This method returns an int value where negative value means that otherDuration is larger than this value, 
			//zero means otherDuration is equal to this duration, 
			//and positive value means this duration is larger than the otherDuration.
			
			int startTimeComparedToBeginWorkTime = startTime.compareTo(beginWorkTime); // 0 or positive
		//	System.out.println("startTimeComparedToBeginWorkTime 0 or positive: "+startTimeComparedToBeginWorkTime);
			int endTimeComparedToEndWorkCutoffTimeAM = endTime.compareTo(endWorkCutoffTimeAM); // 0 or negative
		//	System.out.println("endTimeComparedToEndWorkCutoffTimeAM 0 or negative: "+endTimeComparedToEndWorkCutoffTimeAM);	
			
		if(startTimeComparedToBeginWorkTime >= 0 && endTimeComparedToEndWorkCutoffTimeAM <= 0) {
			
			hoursWorked = calcHours(startTime, endTime);
			Duration currentTime = startTime;
			for(int i = 0; i < hoursWorked; i++) {
				
				int currentTimeComparedToNineOClockBedtime = currentTime.compareTo(nineOClockBedtime); // 0 or positive
				int currentTimeComparedToMidnight = currentTime.compareTo(midnight); // 0 or negative
				int currentTimeComparedToEndWorkCutoffTimeAM = currentTime.compareTo(endWorkCutoffTimeAM); // 0 or negative
				
				if(startTimeComparedToBeginWorkTime >= 0 && currentTimeComparedToNineOClockBedtime < 0) {
					payRate = payRates[0];
				}
				else if(currentTimeComparedToNineOClockBedtime >= 0 && currentTimeComparedToMidnight < 0){
					payRate = payRates[1];
				}
				else if(currentTimeComparedToMidnight >= 0 && currentTimeComparedToEndWorkCutoffTimeAM <= 0){
					payRate = payRates[2];
				}
				//System.out.println("PAY: "+payRate);
				//System.out.println("Hoursworked: "+hoursWorked);
				totalNightlyEarnings += payRate;
				currentTime = currentTime.plusHours(1);
				//System.out.println("CurrentTime + 1hr: "+currentTime);
			
			} // end for loop
			
		}
		return totalNightlyEarnings;

	}
	/** Method to find number of hours */
	private static int calcHours(Duration blockedStartTime, Duration blockedEndTime) {
		Duration numOfHoursWorked = blockedEndTime.minus( blockedStartTime ) ;
		long lhoursWrkd = numOfHoursWorked.toHours();
		int ihoursWrkd = (int)lhoursWrkd;
		
		return ihoursWrkd;
	}
	
	  @Test  
	    public static void testEqualWages(){  
		  	int[] payRates = {12, 8, 16};
			Duration beginWorkT = Duration.parse("PT17H0M"); 
			Duration endWorkCutoffTAM = Duration.parse("PT28H0M");
			//Duration midnight = Duration.parse("PT24H0M");
			int nightlyWagesEarned = calculateNightlyWages(payRates,beginWorkT,endWorkCutoffTAM);
			System.out.println("Test Earned: "+nightlyWagesEarned);
			//System.out.println("Midnight: " + midnight.toHours());
	        assertEquals(12,nightlyWagesEarned);  
	      
	    }
	private static void assertEquals(int i, int nightlyWagesEarned) {
		
		if (i == nightlyWagesEarned) {
			System.out.println("Wages are equal::Test Passed");
			}
		
	}  

}
