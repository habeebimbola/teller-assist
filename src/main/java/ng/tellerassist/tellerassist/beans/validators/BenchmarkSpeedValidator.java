
package ng.tellerassist.tellerassist.beans.validators;

import ng.tellerassist.tellerassist.entity.TellerAssistBenchmarkSpeed;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class BenchmarkSpeedValidator {
    
    public void validate(Errors error, Object object )
    {
        TellerAssistBenchmarkSpeed benchmarkSpeed = (TellerAssistBenchmarkSpeed)object;
        ValidationUtils.rejectIfEmptyOrWhitespace(error, "configValue", "Benchmark Speed Value", "Speed Value Cannot Be Empty.");
        
        if((benchmarkSpeed.getConfigValue() < 0) || (benchmarkSpeed.getConfigValue().isNaN()))
        {
            error.rejectValue("configValue", "Speed Config Value.", "Speed Config Value Input Is Invalid. Please Modify.");
            return;
        }
        if(benchmarkSpeed.getConfigValue().toString().matches("^\\d+\\.?\\d*$"))
        {
            error.rejectValue("configValue", "Speed Config Value", "Speed Config Value Must Be Numeric/Decimal. Please Modify.");
        }
    }
    
    public static void main(String...args )
    {
        int[] array = {1, 4, 6, 3, 2, 2 };
        
        
        for(int index = 0; index < array.length; index++ )
        {
            int prefixSum = 0, suffixSum = 0, suffix = array.length - 1;
            
            for( int cnt = suffix; cnt > index; cnt-- )
            {
                suffixSum += array[cnt];
            }
            for(int cnt = 0; cnt < index; cnt++ )
            {
                prefixSum += array[cnt];
            }
            
            if(prefixSum == suffixSum )
            {
                System.out.printf("Equilibrium index %d\nEquilibrium element %d\n", index, array[index]);
            }
        }
    }
}
