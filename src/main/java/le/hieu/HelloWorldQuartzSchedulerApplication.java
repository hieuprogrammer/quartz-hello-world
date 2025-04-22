package le.hieu;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class HelloWorldQuartzSchedulerApplication {
    public static void main(String[] args) throws SchedulerException {
        // 1. Defining the JobDetail
        JobDetail jobDetail = buildHelloWorldJobDetail();

        // 2. Creating a Trigger that fires every second
        Trigger trigger = buildHelloWorldTrigger();

        // 3. Get a scheduler instance and start it
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        // 4. Schedule the job
//        scheduler.scheduleJob(jobDetail, trigger);

        // Scheduling CRON jobs
        JobDetail cronJobDetail = buildCronJobDetail();
        Trigger cronJobTrigger = buildCronJobTrigger("0/1 * * ? * * *");
        scheduler.scheduleJob(cronJobDetail, cronJobTrigger);
    }

    private static Trigger buildHelloWorldTrigger() {
        return TriggerBuilder.newTrigger()
                                        .withIdentity("helloJob", "group1")
                                        .startNow()
                                        .withSchedule(
                                            SimpleScheduleBuilder.simpleSchedule()
                                            .withIntervalInSeconds(1)
                                            .repeatForever())
                                        .build();
    }

    private static JobDetail buildHelloWorldJobDetail() {
        return JobBuilder.newJob(HelloWorldJob.class)
                        .withIdentity("helloJob", "group1")
                        .build();
    }

    private static JobDetail buildCronJobDetail() {
        return JobBuilder.newJob(HelloWorldJob.class)
                .withIdentity("cronJob_" + System.currentTimeMillis(), "group1")
                .build();
    }

    private static Trigger buildCronJobTrigger(String cronExpression) {
        return TriggerBuilder.newTrigger()
                .withIdentity("cronTrigger_" + System.currentTimeMillis(), "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
    }
}