package vianditasONG.server;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import vianditasONG.modelos.servicios.reportes.ReportadorMain;

public class App {

    public static void main(String[] args) {

        Server.init();
        startScheduler();
    }

    private static void startScheduler() {
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler = sf.getScheduler();
            JobDetail job1 = JobBuilder.newJob(ReportadorMain.class)
                    .withIdentity("cronReportes", "grupo22")
                    .usingJobData("Info", "Valor")
                    .build();

            Trigger trigger1 = TriggerBuilder.newTrigger()
                    .withIdentity("inventoryTrigger", "grupo1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInMinutes(5)
                            .repeatForever())
                    .build();
            scheduler.scheduleJob(job1, trigger1);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}


