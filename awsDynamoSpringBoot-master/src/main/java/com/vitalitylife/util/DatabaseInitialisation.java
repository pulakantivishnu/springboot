package com.vitalitylife.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.vitalitylife.policy.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitialisation implements ApplicationListener<ContextRefreshedEvent> {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private DynamoDBMapper dbMapper;

  @Autowired
  private AmazonDynamoDB dynamoDB;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {

    log.trace("1111111111111111111111111Entering createDatabaseTablesIfNotExist()");
    System.out.print("***********Entering createDatabaseTablesIfNotExist()*******");
    CreateTableRequest request = dbMapper
        .generateCreateTableRequest(Customer.class)
        .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
    try {
      System.out.print("***********Authenticating*******");
      DescribeTableResult result = dynamoDB.describeTable(request.getTableName());
      log.info("Table status {}, {}", request.getTableName(), result.getTable().getTableStatus());

      System.out.print("Table status {}, {}"+request.getTableName());
    } catch (ResourceNotFoundException expectedException) {
      CreateTableResult result = dynamoDB.createTable(request);
      log.info("&&&&&&&&&&&&&&&&&&Exception&&&&&&&&&&&&&&&");
      log.info("Table creation triggered {}, {}", request.getTableName(), result.getTableDescription().getTableStatus());
    }
  }

}
