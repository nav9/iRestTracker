package irest;

import java.time.Instant;

//TODO: Functions in this class need serious improvement. Use serialization or CSV or JSON
public class StrainTracker {
    public int secondsUserIsStrained = 0;//Unit: seconds
    public double rateOfStrainDecrease = 3;//Assuming for every hour of work, 20 min of rest is required, so 60/20=3
    public double rateOfStrainIncrease = 1;//This needs experimentation for best value
    public long timeUserWasLastActive = Instant.now().getEpochSecond();
    
    StrainTracker() {}
    
    public void loadDataAndAssign(final String[] line) {        
        int i = 0;
        System.out.println("Assigning data:");
        int secondsOfStrain = Integer.parseInt(line[i++]); System.out.println(" "+secondsOfStrain);
        rateOfStrainDecrease = Double.parseDouble(line[i++]); System.out.println(" "+rateOfStrainDecrease);
        double strainIncreaseRate = Double.parseDouble(line[i++]); System.out.println(" "+strainIncreaseRate);
        timeUserWasLastActive = Long.parseLong(line[i++]); System.out.println(" "+timeUserWasLastActive);
        System.out.println("Elapsed time in sec: "+getElapsedTimeInSeconds(line)+" and secOfStrain:"+secondsOfStrain);
        if (getElapsedTimeInSeconds(line) > secondsOfStrain) {//means the user got enough of rest
            secondsUserIsStrained = 0;
            System.out.println("Elapsed time greater, so assigning secondsUserIsStrained="+secondsUserIsStrained);
        } else {
            secondsUserIsStrained = secondsOfStrain;
            rateOfStrainIncrease = strainIncreaseRate;
            System.out.println("secondsUserIsStrained="+secondsUserIsStrained+", rateOfStrainIncrease="+rateOfStrainIncrease);
        }
    }
    
    public long getElapsedTimeInSeconds(final String[] line) {
        int i = 0;
        i++;//secondsUserIsStrained = Integer.parseInt(line[i++]);
        i++;//rateOfDecrease = Double.parseDouble(line[i++]);
        i++;//rateOfIncrease = Double.parseDouble(line[i++]);
        return Instant.now().getEpochSecond() - Long.parseLong(line[i++]);
    }        
    
    public String getAsStringForWriting() {
        System.out.println("secondsStrained: "+secondsUserIsStrained);
        timeUserWasLastActive = Instant.now().getEpochSecond();
        return Integer.toString(secondsUserIsStrained)+","
                +Double.toString(rateOfStrainDecrease)+","
                +Double.toString(rateOfStrainIncrease)+","
                +Long.toString(timeUserWasLastActive);
    }    
}
