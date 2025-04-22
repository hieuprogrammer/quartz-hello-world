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
        scheduler.scheduleJob(jobDetail, trigger);
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
}