package com.census.support.batchJob.tablet;

import com.census.support.helper.response.PaginatedResponse;
import com.census.support.ticket.TicketDTO;
import com.census.support.util.PaginatorService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
public class TabletController {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @Autowired
    private TabletService tabletService;

    public Map<String,String > clientParams;

    @GetMapping("tabletInfo/getList")
    public ResponseEntity<?> getAllPaginatedResponse(HttpServletRequest request,
                                                     @RequestParam Map<String,String> clientParams) {
        this.clientParams = clientParams;
        PaginatorService ps = new PaginatorService(request);
        ps.sortDir = "asc";
        Page<TabletDTO> page = this.tabletService.getAllPaginatedLists(this.clientParams, ps.pageNum, ps.pageSize, ps.sortField, ps.sortDir);
        List<TabletDTO> listData = page.getContent();

        return new ResponseEntity<>(new PaginatedResponse(true,200,"ok",page.getTotalElements(),
                page.getTotalPages(),ps.sortDir.equals("asc") ? "desc": "asc",page.getNumber(), Arrays.asList(listData.toArray())), HttpStatus.OK);
    }

    @PostMapping("job/importTablets")
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
    @GetMapping("searchTabletBySimNo")
    public ResponseEntity<?> searchTabletBySimNo(@RequestParam String simNo) {
       return tabletService.searchTabletBySimNo(simNo);
    }

    //search Tablet by barCode
    @GetMapping("searchTabletByBarCode")
    public ResponseEntity<?> searchTabletByBarCode(@RequestParam String barCode) {
        return tabletService.searchTabletByBarCode(barCode);
    }
}
