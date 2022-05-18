package com.census.support.batchJob.tablet;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class TabletController {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @Autowired
    private TabletService tabletService;

    @PostMapping("/job/importTablets")
    public void importCsvToDBJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    // search by simNo or barCode
    @GetMapping("/searchTabletBySimNo")
    public ResponseEntity<?> searchTabletBySimNo(@RequestParam String simNo) {
       return tabletService.searchTabletBySimNo(simNo);
    }

    //search Tablet by barCode
    @GetMapping("/searchTabletByBarCode")
    public ResponseEntity<?> searchTabletByBarCode(@RequestParam String barCode) {
        return tabletService.searchTabletByBarCode(barCode);
    }
}
