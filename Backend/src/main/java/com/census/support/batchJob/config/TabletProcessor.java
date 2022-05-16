package com.census.support.batchJob.config;

import com.census.support.batchJob.tablet.Tablet;
import org.springframework.batch.item.ItemProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TabletProcessor implements ItemProcessor<Tablet,Tablet> {


    @Override
    public Tablet process(Tablet item) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse("2022-05-15 00:00:00");

        //applying condition to filter the data
        if(item.getDeliveryDate().after(date1)){
            return item;
        }else {
            return null;
        }

       // return item;

    }
}