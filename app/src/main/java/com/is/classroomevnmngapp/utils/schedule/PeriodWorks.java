package com.is.classroomevnmngapp.utils.schedule;

public interface PeriodWorks {

    public boolean isStartPeriodWork();
    public boolean isExpirePeriodWork();
    public long delayTime(/*String startTime*/);
    public long overTime(/*String endTime*/);

    default boolean isBeforePeriodWork(){ return false; }
    default boolean isAfterPeriodWork(){ return false; }

    default void hoursPeriodWork(String fromH, String toH) { }
    default void daysPeriodWork(String fromD, String toD) { }

}
