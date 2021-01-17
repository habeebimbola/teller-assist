package ng.tellerassist.tellerassist.serviceDefinition;

import ng.tellerassist.tellerassist.entity.TellerAssistUserRole;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional()
public interface TransactionServiceDefinition {

    public abstract void saveEntity(Class<?> refClass);

    public abstract Object loadEntity(Class<?> refClass);

    public abstract Double getCurrentAvgSpeed(LocalDateTime currentDate);

    public abstract Object findEntityById(Class refClass, Long id);

    public abstract Double getBranchSpeed(LocalDateTime timeOfDay, Integer branchId);

    public abstract Integer branchesBelowBenchmarkCountPerTimePeriod(LocalDateTime timeOfDay);

    public abstract Integer totalBankTrnxPerTimePeriod(LocalDateTime timeOfDay);

    public abstract Integer branchRequestsOnQueueCountPerTimePeriod(LocalDateTime timeOfDay);

    public abstract List<String> allTellerNamesByBranch(Integer branchId);

    public abstract Integer allTellersCountByBranch(Integer branchId);

    public abstract Map<String, Integer> trnxCountByEachTellerPerTimePeriod(LocalDateTime timeOfDay, Integer branchId);

    public abstract Map<String, Double> trnxTimeByEachTellerPerTimePeriod(LocalDateTime timeOfDay, Integer branchId);

    public abstract Map<String, Double> trnxAmountByEachTellerPerTimePeriod(LocalDateTime timeOfDay, Integer branchId);

    public abstract Integer tellersAboveBenchmarkCount(Integer branchId);

    public abstract Integer tellersBelowBenchmarkCount(Integer branchId);

    public abstract Map<String, Double> avgSpeedByEachTellerPerTimePeriod(LocalDateTime timeOfDay, Integer branchId);

    public abstract List<Double> avgTrnxPerTimePeriodSpeedList(LocalDateTime timeOfDay);

    public abstract Double trnxSpeedPerTimePeriod(LocalDateTime timeOfDay);

    public abstract Integer totalBranchTrnxPerDayCount(LocalDateTime timeOfDay, Integer branchId);

    public abstract Integer completedBranchTrnxCountPerTimePeriod(LocalDateTime timeOfDay, Integer branchId);

    public abstract Integer incompleteBankTrnxCountPerTimePeriod(LocalDateTime timeOfDay);

    public abstract List<String> allTrnxTypes();

    public abstract Map<String, Integer> incompleteBranchTrnxDetail(LocalDateTime timeOfDay, Integer branchId);

    public abstract Map<String, Integer> completedBranchTrnxDetail(LocalDateTime timeOfDay, Integer branchId);

    public abstract Integer incompleteBranchTrnxCountPerDay(LocalDateTime timeOfDay, Integer branchId);

    public abstract List<? extends Object> findAll(Class<?> refClass);

    public abstract List<TellerAssistUserRole> findRoles();
    
    public abstract Object findByPropertyNameValue( Class<?> refClass, String propertyName, String propertyValue);
}
