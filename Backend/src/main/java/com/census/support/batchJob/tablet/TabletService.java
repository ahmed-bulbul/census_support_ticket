package com.census.support.batchJob.tablet;

import com.census.support.helper.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class TabletService {
    @Autowired
    TabletRepository tabletRepository;


    public ResponseEntity<?> searchTabletBySimNo(String simNo) {
        Tablet tablet = tabletRepository.findBySimNo(simNo);
        if (tablet == null) {
            return new ResponseEntity<>(new BaseResponse(false, "Tablet not found", HttpStatus.NOT_FOUND.value()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(true, "Tablet found", HttpStatus.OK.value(), tablet), HttpStatus.OK);
    }

    public ResponseEntity<?> searchTabletByBarCode(String barCode) {
        try {
            System.out.println("barCode: " + barCode);
            Tablet tablet = tabletRepository.getByBarCode(barCode);
            if (tablet == null) {
                return new ResponseEntity<>(new BaseResponse(false, "Tablet not found", HttpStatus.NOT_FOUND.value()), HttpStatus.OK);
            }
            return new ResponseEntity<>(new BaseResponse(true, "Tablet found", HttpStatus.OK.value(), tablet), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new BaseResponse(false, " Something went wrong : "+e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.OK);
        }

    }
}
