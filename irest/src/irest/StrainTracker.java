package irest;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//TODO: Functions in this class need serious improvement. Use serialization or CSV or JSON
public class StrainTracker {
    private double defaultRateOfStrainIncrease = 1;
    public int secondsUserIsStrained = 0;//Unit: seconds
    public double rateOfStrainDecrease = 3;//Assuming for every hour of work, 20 min of rest is required, so 60/20=3
    public double rateOfStrainIncrease = defaultRateOfStrainIncrease;//This needs experimentation for best value
    public long timeUserWasLastActive = Instant.now().getEpochSecond();
    private final int secondsUserIsStrainedIndex = 0;
    private final int rateOfStrainDecreaseIndex = 1;
    private final int rateOfStrainIncreaseIndex = 2;
    private final int timeUserWasLastActiveIndex = 3;
    private LogManager log = null;
    public final String timeTrackerFilename = "Tracking/iRest" + ".tracking";
    public final String trackerResultsFilename = "Tracking/Results" + ".results";    
    
    StrainTracker(LogManager logRef) {
        log = logRef;
    }
    
    //TODO: Using "String[] line" like this is prone to bugs. Use a JSON with proper names for each variable
    public void loadDataAndAssign(final String[] line) {
        log.write("Assigning data:");
        int secondsOfStrain = Integer.parseInt(line[secondsUserIsStrainedIndex]); log.write(" "+secondsOfStrain);
        rateOfStrainDecrease = Double.parseDouble(line[rateOfStrainDecreaseIndex]); log.write(" "+rateOfStrainDecrease);
        double strainIncreaseRate = Double.parseDouble(line[rateOfStrainIncreaseIndex]); log.write(" "+strainIncreaseRate);
        timeUserWasLastActive = Long.parseLong(line[timeUserWasLastActiveIndex]); log.write(" "+timeUserWasLastActive);
        log.write("Elapsed time in sec: "+getElapsedTimeInSeconds(line)+" and secOfStrain:"+secondsOfStrain);
        if (getElapsedTimeInSeconds(line) > secondsOfStrain) {//means the user got enough of rest
            secondsUserIsStrained = 0;
            rateOfStrainIncrease = defaultRateOfStrainIncrease;
            log.write("Elapsed time greater. User got enough rest, so assigning secondsUserIsStrained="+secondsUserIsStrained);
        } else {
            secondsUserIsStrained = secondsOfStrain;
            rateOfStrainIncrease = strainIncreaseRate;
            log.write("secondsUserIsStrained="+secondsUserIsStrained+", rateOfStrainIncrease="+rateOfStrainIncrease);
        }
    }
    
    public long getElapsedTimeInSeconds(final String[] line) {
        return Instant.now().getEpochSecond() - Long.parseLong(line[timeUserWasLastActiveIndex]);
    }        
    
    public String getAsStringForWriting() {
        //System.out.println("secondsStrained: "+secondsUserIsStrained);
        timeUserWasLastActive = Instant.now().getEpochSecond();
        return Integer.toString(secondsUserIsStrained)+","
                +Double.toString(rateOfStrainDecrease)+","
                +Double.toString(rateOfStrainIncrease)+","
                +Long.toString(timeUserWasLastActive);
    }    
    
    public String getTodaysDate() {
        return DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now());            
    }
}
