package com.vitalitylife;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.regions.Regions;

@Configuration
public class ApplicationConfiguration {

  //private final static String DYNAMODB_ENDPOINT_DEFAULT_VALUE = "http://localhost:8000";

  private final Logger log = LoggerFactory.getLogger(getClass());

  //@Value("${dynamoDbEndpoint:" + DYNAMODB_ENDPOINT_DEFAULT_VALUE + "}")
  private String dynamoDbEndpoint;

  @Bean
  public AmazonDynamoDB amazonDynamoDb() {

    /*log.trace("Entering amazonDynamoDb()");
    AmazonDynamoDB client = new AmazonDynamoDBClient().withRegion(Regions.AP_SOUTH_1)).build();
    log.info("Using DynamoDb endpoint {}", dynamoDbEndpoint);
    client.setEndpoint(dynamoDbEndpoint);
    return client;*/
    BasicAWSCredentials awsCreds = new BasicAWSCredentials("access_key_id", "secret_key_id");
    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();


    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.AP_SOUTH_1)
            .build();
    return client;

  }

  @Bean
  public DynamoDBMapper dynamoDbMapper(AmazonDynamoDB amazonDynamoDB) {

    log.trace("Entering dynamoDbMapper()");
    return new DynamoDBMapper(amazonDynamoDB);
  }

}
