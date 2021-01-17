package ng.tellerassist.tellerassist.servicesImplementation;

import ng.tellerassist.tellerassist.entity.TellerAssistBenchmarkSpeed;
import ng.tellerassist.tellerassist.entity.TellerAssistUserRole;
import ng.tellerassist.tellerassist.serviceDefinition.TransactionServiceDefinition;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class TellerAssistTransactionServices implements TransactionServiceDefinition {

    @Inject
    private SessionFactory sessionFactory;

    @Override
    @Transactional()
    public void saveEntity(Class<?> refClass) {
        if (refClass != null) {
            this.sessionFactory.getCurrentSession().save(refClass);
        }
    }

    @Transactional()
    @Override
    public Object loadEntity(Class refClass) {

        Object returnObj = this.sessionFactory.getCurrentSession().createCriteria(refClass).list().get(0);

        if (returnObj != null) {
            return returnObj;
        }

        return new Object();
    }

    @Override
    public Double getCurrentAvgSpeed(LocalDateTime currentDate) {
        double avgSpeed = 0.0f;

        String avgSpeedQuery = "SELECT ROUND(COUNT(t.branch_id) / SUM(((24 * 60) * (request_processed_date - request_creation_date) ) ), 3 ) Speed "
                + "FROM trandetails t, branch_details b "
                + "WHERE t.branch_id = b.branch_id AND "
                + "(EXTRACT(day from request_processed_date) =:day ) AND "
                + "(EXTRACT(month from request_processed_date) =:month ) AND "
                + "(EXTRACT(year from request_processed_date) =:year ) "
                + "order by Speed desc";

        SQLQuery sqlQuery = this.sessionFactory.getCurrentSession().createSQLQuery(avgSpeedQuery);

        sqlQuery.setParameter("day", currentDate.getDayOfMonth());
        sqlQuery.setParameter("month", currentDate.getMonthValue());
        sqlQuery.setParameter("year", currentDate.getYear());

        if (sqlQuery.uniqueResult() == null) {
            avgSpeed = 0.0f;
            return avgSpeed;
        }
        avgSpeed = ((BigDecimal) sqlQuery.uniqueResult()).doubleValue();
        return avgSpeed;
    }

    @Override
    public Object findEntityById(Class refClass, Long id) {
        Criteria crit = this.sessionFactory.getCurrentSession().
                createCriteria(refClass).
                add(Restrictions.eq("id", id));

        if (crit.uniqueResult() != null) {
            return (Object) crit.uniqueResult();
        }

        return new Object();
    }

    @Override
    public Double getBranchSpeed(LocalDateTime timeOfDay, Integer branchId) {
        double branchSpeed = 0.0f;
        String branchSpeedQueryString = "SELECT ROUND(COUNT(t.branch_id) / SUM(((24 * 60) * (request_processed_date - request_creation_date) ) ), 3 ) Speed "
                + "FROM trandetails t, branch_details b "
                + "WHERE t.branch_id = b.branch_id AND "
                + "t.branch_id = :branchId AND"
                + "(EXTRACT(year from request_processed_date) = :year ) AND "
                + "(EXTRACT(day from request_processed_date) = :day ) AND "
                + "(EXTRACT(month from request_processed_date) = :month ) AND "
                + "(EXTRACT(Hour from to_timestamp(CAST(request_processed_date as timestamp))) <= :hour )";

        SQLQuery branchSpeedQuery = this.sessionFactory.getCurrentSession().createSQLQuery(branchSpeedQueryString);

        branchSpeedQuery.setParameter("branchId", branchId);
        branchSpeedQuery.setParameter("year", timeOfDay.getYear());
        branchSpeedQuery.setParameter("day", timeOfDay.getDayOfMonth());
        branchSpeedQuery.setParameter("month", timeOfDay.getMonthValue());
        branchSpeedQuery.setParameter("hour", timeOfDay.getHour());

        if (branchSpeedQuery.uniqueResult() == null) {
            return branchSpeed;
        }
        return ((BigDecimal) branchSpeedQuery.uniqueResult()).doubleValue();
    }

    @Override
    public Integer branchesBelowBenchmarkCountPerTimePeriod(LocalDateTime timeOfDay) {

        TellerAssistBenchmarkSpeed benchmarkSpeed = (TellerAssistBenchmarkSpeed) this.loadEntity(TellerAssistBenchmarkSpeed.class);
        int branchesBelowCount = 0;

        String queryString = "SELECT t.branch_id, b.branch_name, "
                + " COUNT(t.branch_id),ROUND((COUNT(t.branch_id) / SUM(((24 * 60) * (request_processed_date - request_creation_date) ) )), 3 ) as Speed  "
                + " FROM trandetails t, branch_details b "
                + " WHERE t.branch_id = b.branch_id AND "
                + " (EXTRACT(year from request_processed_date) =:year ) AND "
                + " (EXTRACT(day from request_processed_date) =:day ) AND "
                + " (EXTRACT(month from request_processed_date) =:month ) AND "
                + " ((:hour - EXTRACT(Hour from to_timestamp(CAST(request_processed_date as timestamp)))) < 1 ) AND "
                + " ((:minute - EXTRACT(Minute from to_timestamp(CAST(request_processed_date as timestamp)))) <= 5 ) "
                + " GROUP BY t.branch_id, b.branch_name ";

        SQLQuery belowCountQuery = this.sessionFactory.getCurrentSession().createSQLQuery(queryString);

        belowCountQuery.setParameter("year", timeOfDay.getYear());
        belowCountQuery.setParameter("day", timeOfDay.getDayOfMonth());
        belowCountQuery.setParameter("month", timeOfDay.getMonthValue());
        belowCountQuery.setParameter("hour", timeOfDay.getHour());
        belowCountQuery.setParameter("minute", timeOfDay.getMinute());

        List<Object[]> speedList = (ArrayList<Object[]>) belowCountQuery.list();

        if (!speedList.isEmpty()) {
            for (Object[] currentRow : speedList) {
                if (currentRow[2] != null) {
                    double speedValue = ((BigDecimal) currentRow[2]).doubleValue();

                    if (speedValue < benchmarkSpeed.getConfigValue()) {
                        branchesBelowCount += 1;
                    }
                }
            }
        }

        return branchesBelowCount;
    }

    @Override
    public Integer totalBankTrnxPerTimePeriod(LocalDateTime timeOfDay) {
        Integer totalTrnxCount = 0;

        String totalTrnxString = "SELECT count(*) from TranDetails "
                + "WHERE request_processed_date is not null and request_status = 0 "
                + "AND (extract(year from request_processed_date) =:year )  "
                + "AND (extract(month from request_processed_date) =:month ) "
                + "AND (extract(day from request_processed_date) =:day ) "
                + "AND (:hour - (extract(hour from to_timestamp(cast(request_processed_date as timestamp)))) < 1 ) "
                + "AND (:minute - (extract(Minute from to_timestamp(cast(request_processed_date as timestamp)))) <= 5 ) ";

        SQLQuery totalTrnxQuery = this.sessionFactory.getCurrentSession().createSQLQuery(totalTrnxString);

        totalTrnxQuery.setParameter("year", timeOfDay.getYear());
        totalTrnxQuery.setParameter("month", timeOfDay.getMonthValue());
        totalTrnxQuery.setParameter("day", timeOfDay.getDayOfMonth());
        totalTrnxQuery.setParameter("hour", timeOfDay.getHour());
        totalTrnxQuery.setParameter("minute", timeOfDay.getMinute());

        if (totalTrnxQuery.uniqueResult() == null) {
            return totalTrnxCount;
        }

        totalTrnxCount = ((BigDecimal) totalTrnxQuery.uniqueResult()).intValue();
        return totalTrnxCount;
    }

    @Override
    public Integer branchRequestsOnQueueCountPerTimePeriod(LocalDateTime timeOfDay) {
        int requestsCount = 0;

        String requestsQueueStr = "SELECT count(*) from TranDetails "
                + "WHERE request_processed_date is not null and request_status = 0 AND BRANCH_ID =:branchId  "
                + "AND (extract(year from request_processed_date) =:year )  "
                + "AND (extract(month from request_processed_date) =:month ) "
                + "AND (extract(day from request_processed_date) =:day ) "
                + "AND (:hour - (extract(hour from to_timestamp(cast(request_processed_date as timestamp)))) < 1  ) "
                + "AND (:minute - (extract(Minute from to_timestamp(cast(request_processed_date as timestamp)))) <= 5 ) ";

        SQLQuery requestQueueQuery = this.sessionFactory.getCurrentSession().createSQLQuery(requestsQueueStr);

        requestQueueQuery.setParameter("year", timeOfDay.getYear());
        requestQueueQuery.setParameter("month", timeOfDay.getMonthValue());
        requestQueueQuery.setParameter("day", timeOfDay.getDayOfMonth());
        requestQueueQuery.setParameter("hour", timeOfDay.getHour());
        requestQueueQuery.setParameter("minute", timeOfDay.getMinute());

        if (requestQueueQuery.uniqueResult() == null) {
            return requestsCount;
        }
        requestsCount = ((BigDecimal) requestQueueQuery.uniqueResult()).intValue();
        return requestsCount;
    }

    @Override
    public List<String> allTellerNamesByBranch(Integer branchId) {
        List<String> tellerList = new ArrayList<String>();

        String tellerNamesStr = "select distinct requestProcessor from TransactionDetail t where requestProcessor is not null and t.branchDetail.id =:branch_id";
        Query tellerNamesQuery = this.sessionFactory.getCurrentSession().createQuery(tellerNamesStr);

        tellerNamesQuery.setParameter("branch_id", branchId);
        tellerList = (ArrayList<String>) tellerNamesQuery.list();

        if (tellerList.isEmpty()) {
        }
        return tellerList;
    }

    @Override
    public Integer allTellersCountByBranch(Integer branchId) {
        int tellerCount = 0;
        String tellerCountStr = "select count(distinct t.requestProcessor) from TransactionDetail t where t.requestProcessor is not null AND t.branchDetail.id =:branch_id";
        Query tellerCountQuery = this.sessionFactory.getCurrentSession().createQuery(tellerCountStr);

        tellerCountQuery.setParameter("branch_id", branchId);

        if (tellerCountQuery.uniqueResult() == null) {
            return tellerCount;
        }
        tellerCount = ((Long) tellerCountQuery.uniqueResult()).intValue();
        return tellerCount;
    }

    @Override
    public Map<String, Integer> trnxCountByEachTellerPerTimePeriod(LocalDateTime timeOfDay, Integer branchId) {
        Map<String, Integer> returnMap = new HashMap<String, Integer>();

        for (String name : this.allTellerNamesByBranch(branchId)) {
            String trnxCountStr = "SELECT count(request_id) from trandetails "
                    + " WHERE request_processed_date is not null and request_status = 0 and upper(replace(request_processor,' ','')) = upper(replace(:name,' ',''))"
                    + " AND branch_id =:branchId "
                    + " AND (:year = extract(year from request_processed_date)) "
                    + " AND (:month = extract(month from request_processed_date)) "
                    + " AND (:day = extract( day from request_processed_date)) "
                    + " AND (extract(hour from to_timestamp(cast(request_processed_date as timestamp))) <= :hour ) ";
            SQLQuery trnxCountQuery = this.sessionFactory.getCurrentSession().createSQLQuery(trnxCountStr);

            trnxCountQuery.setParameter("name", name);
            trnxCountQuery.setParameter("branchId", branchId);
            trnxCountQuery.setParameter("year", timeOfDay.getYear());
            trnxCountQuery.setParameter("month", timeOfDay.getMonthValue());
            trnxCountQuery.setParameter("day", timeOfDay.getDayOfMonth());
            trnxCountQuery.setParameter("hour", timeOfDay.getHour());

            if (trnxCountQuery.uniqueResult() != null) {
                Integer count = ((BigDecimal) trnxCountQuery.uniqueResult()).intValue();
                returnMap.put(name, count);
            }
        }

        return returnMap;
    }

    @Override
    public Map<String, Double> trnxTimeByEachTellerPerTimePeriod(LocalDateTime timeOfDay, Integer branchId) {
        Map<String, Double> returnMap = new HashMap();

        for (String name : this.allTellerNamesByBranch(branchId)) {
            String trnxTimeStr = "SELECT  round(sum((24 * 60 )*(request_processed_date - request_creation_date ) ),4) from trandetails "
                    + "WHERE request_processed_date is not null and request_status = 0 and upper(replace(request_processor,' ','')) = upper(replace(:name,' ','')) "
                    + "AND branch_id =:branchId "
                    + "AND (:day = extract(day from request_processed_date )) "
                    + "AND (:month = extract(month from request_processed_date )) "
                    + "AND (:year = extract(year from request_processed_date )) "
                    + "AND (extract(hour from to_timestamp(cast(request_processed_date as timestamp))) <= :hour )";

            SQLQuery trnxTimeQuery = this.sessionFactory.getCurrentSession().createSQLQuery(trnxTimeStr);

            trnxTimeQuery.setParameter("name", name);
            trnxTimeQuery.setParameter("branchId", branchId);
            trnxTimeQuery.setParameter("day", timeOfDay.getDayOfMonth());
            trnxTimeQuery.setParameter("month", timeOfDay.getMonthValue());
            trnxTimeQuery.setParameter("year", timeOfDay.getYear());
            trnxTimeQuery.setParameter("hour", timeOfDay.getHour());

            double value = 0.0d;
            if (trnxTimeQuery.uniqueResult() != null) {
                value = ((BigDecimal) trnxTimeQuery.uniqueResult()).doubleValue();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    @Override
    public Map<String, Double> trnxAmountByEachTellerPerTimePeriod(LocalDateTime timeOfDay, Integer branchId) {
        Map<String, Double> returnMap = new HashMap<String, Double>();

        for (String name : this.allTellerNamesByBranch(branchId)) {
            String tellerAmountStr = "SELECT sum(request_amount), request_processor from trandetails "
                    + "WHERE request_processor is not null  and request_processed_date is not null "
                    + "AND branch_id =:branchId AND upper(replace(request_processor,' ','')) = upper(replace(:name,' ','')) "
                    + "AND (:year = extract(year from request_processed_date )) "
                    + "AND (:month = extract(month from request_processed_date )) "
                    + "AND (:day = extract(day from request_processed_date ) ) "
                    + "AND (extract(hour from to_timestamp(cast(request_processed_date as timestamp))) <= :hour ) "
                    + "GROUP BY request_processor ";

            SQLQuery tellerAmountQuery = this.sessionFactory.getCurrentSession().createSQLQuery(tellerAmountStr);
            tellerAmountQuery.setParameter("branchId", branchId);
            tellerAmountQuery.setParameter("name", name);
            tellerAmountQuery.setParameter("year", timeOfDay.getHour());
            tellerAmountQuery.setParameter("month", timeOfDay.getMonthValue());
            tellerAmountQuery.setParameter("day", timeOfDay.getDayOfMonth());
            tellerAmountQuery.setParameter("hour", timeOfDay.getHour());

            List<Object[]> objList = (ArrayList<Object[]>) tellerAmountQuery.list();

            if (!objList.isEmpty()) {
                for (Object[] currentRow : objList) {
                    if (currentRow[0] != null) {
                        returnMap.put(name, ((BigDecimal) currentRow[0]).doubleValue());
                    } else {
                        returnMap.put(name, 0.0d);
                    }

                }
            } else {
                returnMap.put(name, 0.0d);
            }
        }
        return returnMap;
    }

    @Override
    public Integer tellersAboveBenchmarkCount(Integer branchId) {
        int tellersCount = 0;

        HashMap<String, Double> trnxMap = (HashMap<String, Double>) this.trnxTimeByEachTellerPerTimePeriod(LocalDateTime.now(), branchId);
        TellerAssistBenchmarkSpeed benchmark = (TellerAssistBenchmarkSpeed) this.loadEntity(TellerAssistBenchmarkSpeed.class);

        for (String key : trnxMap.keySet()) {
            if ((trnxMap.get(key)) > benchmark.getConfigValue()) {
                tellersCount += 1;
            }
        }
        return tellersCount;
    }

    @Override
    public Integer tellersBelowBenchmarkCount(Integer branchId) {
        int tellersCount = 0;

        HashMap<String, Double> trnxMap = (HashMap<String, Double>) this.trnxTimeByEachTellerPerTimePeriod(LocalDateTime.now(), branchId);
        TellerAssistBenchmarkSpeed benchmark = (TellerAssistBenchmarkSpeed) this.loadEntity(TellerAssistBenchmarkSpeed.class);

        for (String key : trnxMap.keySet()) {
            if ((trnxMap.get(key)) < benchmark.getConfigValue()) {
                tellersCount += 1;
            }
        }

        return tellersCount;
    }

    @Override
    public Map<String, Double> avgSpeedByEachTellerPerTimePeriod(LocalDateTime timeOfDay, Integer branchId) {
        Map<String, Double> avgSpeedMap = new HashMap();
        Map<String, Integer> trnxCountMap = (HashMap<String, Integer>) this.trnxCountByEachTellerPerTimePeriod(timeOfDay, branchId);
        Map<String, Double> trnxTimeMap = (HashMap<String, Double>) this.trnxTimeByEachTellerPerTimePeriod(timeOfDay, branchId);

        for (String key : trnxCountMap.keySet()) {
            if (trnxTimeMap.get(key) > 0d && trnxCountMap.get(key) > 0) {
                double value = 0.0f;
                value = trnxCountMap.get(key) / trnxTimeMap.get(key);
                avgSpeedMap.put(key, value);
            }
            if ((trnxTimeMap.get(key) <= 0d) && (trnxCountMap.get(key) <= 0)) {
                avgSpeedMap.put(key, 0.0d);
            }
        }
        return avgSpeedMap;
    }

    @Override
    public List<Double> avgTrnxPerTimePeriodSpeedList(LocalDateTime timeOfDay) {
        List<Double> speedList = new ArrayList();
        double avgSpeed = 0.0f;
        int minuteInterval = 5;

        while (minuteInterval > 0) {
            String avgSpeedStr = "SELECT ROUND(COUNT( REQUEST_ID ) / SUM( ( 24 * 60) * ( REQUEST_PROCESSED_DATE - REQUEST_TRAN_DATE)), 5) FROM TRANDETAILS "
                    + "WHERE REQUEST_PROCESSED_DATE IS NOT NULL AND REQUEST_STATUS = 1  "
                    + "AND (  :year = extract(year from REQUEST_TRAN_DATE ) ) "
                    + "AND (:month = extract(month from REQUEST_TRAN_DATE) ) "
                    + "AND ( :day  = extract(day from REQUEST_TRAN_DATE ) ) "
                    + "AND (  :hour - extract( Hour from to_timestamp(cast(REQUEST_PROCESSED_DATE as timestamp)) ) = 0 ) "
                    + "AND ( ( :minute - :minuteInterval ) - extract( Minute from to_timestamp(cast(REQUEST_PROCESSED_DATE as timestamp ))) ) <= 5";

            SQLQuery avsSpeedQuery = this.sessionFactory.getCurrentSession().createSQLQuery(avgSpeedStr);

            avsSpeedQuery.setParameter("year", timeOfDay.getDayOfMonth());
            avsSpeedQuery.setParameter("month", timeOfDay.getMonthValue());
            avsSpeedQuery.setParameter("day", timeOfDay.getYear());
            avsSpeedQuery.setParameter("hour", timeOfDay.getHour());
            avsSpeedQuery.setParameter("minute", timeOfDay.getMinute());
            avsSpeedQuery.setParameter("minuteInterval", minuteInterval);

            if (avsSpeedQuery.uniqueResult() != null) {
                avgSpeed = ((BigDecimal) avsSpeedQuery.uniqueResult()).doubleValue();
                speedList.add(avgSpeed);
            } else {
                speedList.add(0.0d);
            }
            minuteInterval--;
        }
        return speedList;
    }

    @Override
    public Double trnxSpeedPerTimePeriod(LocalDateTime timeOfDay) {
        double trnxSpeed = 0.0f;

        String trnxSpeedStr = "SELECT  round((sum( ( 24 * 60 ) * ( request_processed_date - request_creation_date ) ) / count( * )) , 4 ) from trandetails "
                + "WHERE request_processed_date is not null and request_status = 1 "
                + "AND (:day = extract(day from request_tran_date)) "
                + "AND (:month = extract(month from request_tran_date))"
                + "AND (:year = extract(year from request_tran_date)) "
                + "AND (:hour - (extract(hour from to_timestamp(cast(request_processed_date as timestamp)))) =  0 ) "
                + "AND (:minute - (extract(Minute from to_timestamp(cast(request_processed_date as timestamp)))) <= 5)";
        SQLQuery trnxSpeedQuery = this.sessionFactory.getCurrentSession().createSQLQuery(trnxSpeedStr);

        trnxSpeedQuery.setParameter("day", timeOfDay.getDayOfMonth());
        trnxSpeedQuery.setParameter("month", timeOfDay.getMonthValue());
        trnxSpeedQuery.setParameter("year", timeOfDay.getYear());
        trnxSpeedQuery.setParameter("hour", timeOfDay.getHour());
        trnxSpeedQuery.setParameter("minute", timeOfDay.getMinute());

        if (trnxSpeedQuery.uniqueResult() != null) {
            trnxSpeed = ((BigDecimal) trnxSpeedQuery.uniqueResult()).doubleValue();
            return trnxSpeed;
        }
        return trnxSpeed;
    }

    @Override
    public Integer totalBranchTrnxPerDayCount(LocalDateTime timeOfDay, Integer branchId) {
        int totalTrnxCount = 0;

        String totalTrnxStr = "SELECT COUNT(REQUEST_ID) FROM TRANDETAILS "
                + "WHERE BRANCH_ID = ( SELECT BRANCH_ID FROM BRANCH_DETAILS WHERE BRANCH_ID =:branchId ) "
                + " AND (:day = extract(day from REQUEST_TRAN_DATE) ) "
                + " AND (:month = extract(month from REQUEST_TRAN_DATE)) "
                + " AND (:year = extract(year from REQUEST_TRAN_DATE) ) ";
        SQLQuery totalTrnxQuery = this.sessionFactory.getCurrentSession().createSQLQuery(totalTrnxStr);
        totalTrnxQuery.setParameter("branchId", branchId);
        totalTrnxQuery.setParameter("day", timeOfDay.getDayOfMonth());
        totalTrnxQuery.setParameter("month", timeOfDay.getMonthValue());
        totalTrnxQuery.setParameter("year", timeOfDay.getYear());

        if (totalTrnxQuery.uniqueResult() != null) {
            totalTrnxCount = ((BigDecimal) totalTrnxQuery.uniqueResult()).intValue();
            return totalTrnxCount;
        }
        return totalTrnxCount;
    }

    @Override
    public Integer completedBranchTrnxCountPerTimePeriod(LocalDateTime timeOfDay, Integer branchId) {
        int trnxCount = 0;
        String trnxCountStr = "SELECT count(REQUEST_ID) FROM TRANDETAILS WHERE (extract(day from request_tran_date ) =:day) "
                + "AND (extract( year from request_tran_date ) =:year ) AND (extract( month FROM request_tran_date) =:month )"
                + "AND REQUEST_STATUS = 1 AND BRANCH_ID =:branch_id";

        SQLQuery trnxCountQuery = this.sessionFactory.getCurrentSession().createSQLQuery(trnxCountStr);
        trnxCountQuery.setParameter("day", timeOfDay.getDayOfMonth());
        trnxCountQuery.setParameter("year", timeOfDay.getYear());
        trnxCountQuery.setParameter("month", timeOfDay.getMonthValue());
        trnxCountQuery.setParameter("branch_id", branchId);

        if (trnxCountQuery.uniqueResult() != null) {
            trnxCount = ((BigDecimal) trnxCountQuery.uniqueResult()).intValue();
            return trnxCount;
        }
        return trnxCount;
    }

    @Override
    public Integer incompleteBankTrnxCountPerTimePeriod(LocalDateTime timeOfDay) {
        int nonCompleteTrnxCount = 0;
        String trnxCountStr = "SELECT COUNT(TRANDETAILS.REQUEST_ID) FROM TRANDETAILS WHERE REQUEST_STATUS = 0 "
                + "AND (:day = extract(day from request_tran_date)) "
                + "AND (:year = extract(year from request_tran_date)) "
                + "AND (:month = extract(month from request_tran_date)) "
                + "AND (:hour - (extract(hour from to_timestamp(cast(request_processed_date as timestamp)))) = 0 ) "
                + "AND (:minute - (extract(Minute from to_timestamp(cast(request_processed_date as timestamp)))) <= 5 ) ";
        SQLQuery trnxCountQuery = this.sessionFactory.getCurrentSession().createSQLQuery(trnxCountStr);
        trnxCountQuery.setParameter("day", timeOfDay.getDayOfMonth());
        trnxCountQuery.setParameter("year", timeOfDay.getYear());
        trnxCountQuery.setParameter("month", timeOfDay.getMonthValue());
        trnxCountQuery.setParameter("hour", timeOfDay.getHour());
        trnxCountQuery.setParameter("minute", timeOfDay.getMinute());

        if (trnxCountQuery.uniqueResult() != null) {
            nonCompleteTrnxCount = ((BigDecimal) trnxCountQuery.uniqueResult()).intValue();
            return nonCompleteTrnxCount;
        }
        return nonCompleteTrnxCount;
    }

    @Override
    public List<String> allTrnxTypes() {
        ArrayList<String> trnxTypes = new ArrayList();
        String[] trnxTypesArray = {"Cash_Deposit", "Cheque_Deposit", "Cheque_Withdrawal", "Cash_Withdrawal"};
        trnxTypes.addAll(Arrays.asList(trnxTypesArray));
        return (ArrayList<String>) trnxTypes;
    }

    @Override
    public Map<String, Integer> incompleteBranchTrnxDetail(LocalDateTime timeOfDay, Integer branchId) {
        Map<String, Integer> returnMap = new HashMap();

        for (String trnxType : this.allTrnxTypes()) {
            String incompleteStr = "SELECT COUNT(request_id) from trandetails WHERE (upper(request_type) = upper(:trnxType ) ) "
                    + "AND ( extract(year from REQUEST_TRAN_DATE ) =:year ) "
                    + "AND (extract(month from REQUEST_TRAN_DATE ) =:month ) "
                    + "AND ( extract(day from REQUEST_TRAN_DATE ) =:day ) "
                    + "AND REQUEST_STATUS = 0 AND BRANCH_ID =:branchId "
                    + "AND request_processed_date is null";

            SQLQuery incompleteQuery = this.sessionFactory.getCurrentSession().createSQLQuery(incompleteStr);
            incompleteQuery.setParameter("trnxType", trnxType);
            incompleteQuery.setParameter("year", timeOfDay.getYear());
            incompleteQuery.setParameter("month", timeOfDay.getMonthValue());
            incompleteQuery.setParameter("day", timeOfDay.getDayOfMonth());
            incompleteQuery.setParameter("branchId", branchId);

            if (incompleteQuery.uniqueResult() != null) {
                returnMap.put(trnxType, ((BigDecimal) incompleteQuery.uniqueResult()).intValue());
            } else {
                returnMap.put(trnxType, 0);
            }
        }

        return returnMap;
    }

    @Override
    public Map<String, Integer> completedBranchTrnxDetail(LocalDateTime timeOfDay, Integer branchId) {
        Map<String, Integer> returnMap = new HashMap();

        for (String trnxType : this.allTrnxTypes()) {
            String completeTrnxStr = "SELECT COUNT(request_id) from trandetails WHERE request_processed_date is not null AND (upper(request_type) = upper(:trnxType ) ) "
                    + " AND ( extract(year from request_processed_date ) =:year ) "
                    + " AND (extract(month from request_processed_date ) =:month ) "
                    + " AND ( extract(day from request_processed_date ) =:day ) "
                    + " AND REQUEST_STATUS = 1 AND BRANCH_ID =:branchId ";
            SQLQuery completeTrnxQuery = this.sessionFactory.getCurrentSession().createSQLQuery(completeTrnxStr);

            completeTrnxQuery.setParameter("trnxType", trnxType);
            completeTrnxQuery.setParameter("year", timeOfDay.getYear());
            completeTrnxQuery.setParameter("month", timeOfDay.getMonthValue());
            completeTrnxQuery.setParameter("day", timeOfDay.getDayOfMonth());
            completeTrnxQuery.setParameter("branchId", branchId);

            if (completeTrnxQuery.uniqueResult() != null) {
                returnMap.put(trnxType, ((BigDecimal) completeTrnxQuery.uniqueResult()).intValue());
            } else {
                returnMap.put(trnxType, 0);
            }
        }

        return returnMap;
    }

    @Override
    public Integer incompleteBranchTrnxCountPerDay(LocalDateTime timeOfDay, Integer branchId) {
        int incompleteTrnxCount = 0;

        String incompleteStr = "SELECT COUNT(REQUEST_ID) FROM TRANDETAILS "
                + "  WHERE REQUEST_STATUS = 0 AND BRANCH_ID = ( SELECT BRANCH_ID FROM BRANCH_DETAILS WHERE BRANCH_ID =:branchId )"
                + "  AND (extract(day from request_tran_date) =:day ) "
                + "  AND (extract(month from request_tran_date) =:month )"
                + "  AND (extract(year from request_tran_date) =:year )";

        SQLQuery incompleteQuery = this.sessionFactory.getCurrentSession().createSQLQuery(incompleteStr);

        incompleteQuery.setParameter("branchId", branchId);
        incompleteQuery.setParameter("day", timeOfDay.getDayOfMonth());
        incompleteQuery.setParameter("month", timeOfDay.getMonthValue());
        incompleteQuery.setParameter("year", timeOfDay.getYear());

        if (incompleteQuery.uniqueResult() != null) {
            incompleteTrnxCount = ((BigDecimal) incompleteQuery.uniqueResult()).intValue();
            return incompleteTrnxCount;
        }
        return incompleteTrnxCount;
    }
    
    @Override
    public List<? extends Object>  findAll(Class<?> refClass)
    {
        List<? extends Object > returnList = (ArrayList<? extends Object >)this.sessionFactory.getCurrentSession().createCriteria(refClass).list();
        return returnList;
    }
    @Override
    @Transactional(readOnly = true)
    public List<TellerAssistUserRole> findRoles() {
        List<TellerAssistUserRole> returnList = ((ArrayList<TellerAssistUserRole>)this.sessionFactory.getCurrentSession().createCriteria(TellerAssistUserRole.class).list());
        if (returnList != null && (!returnList.isEmpty())) {
            return returnList;
        }
        return new ArrayList<TellerAssistUserRole>();
    }
    
    @Override
    public Object findByPropertyNameValue( Class<?> refClass,String propertyName, String propertyValue)
    {
        List<? extends Object> returnList = (ArrayList<Object>)this.sessionFactory.getCurrentSession().createCriteria(refClass).add(Restrictions.eq(propertyName, propertyValue));
        return null;
    }
}
