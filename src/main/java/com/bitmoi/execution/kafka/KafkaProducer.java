//package com.bitmoi.execution.kafka;
//
//import com.bitmoi.execution.domain.Execute;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaProducer {
//
//    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
//    public static final String PUSH_ALARMS = "PUSH-ALARMS";
//    @Autowired
//    private KafkaTemplate<String, Execute> kafkaTemplate;
//    public void saveExecuteMessage(Execute execute){
//        logger.info(String.format("saveExecuteMessage created -> %s", execute));
//        this.kafkaTemplate.send(PUSH_ALARMS,execute);
//    }
//
//    @Autowired
//    public KafkaProducer(KafkaTemplate<String, Execute> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//}
