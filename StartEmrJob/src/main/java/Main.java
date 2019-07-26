import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClientBuilder;
import com.amazonaws.services.elasticmapreduce.model.*;
import myconstants.Constants;
import org.apache.log4j.BasicConfigurator;

import static myconstants.Constants.DEFAULT_REGION;


public class Main {
    private static final String EMR_RELEASE_LABEL="emr-5.16.0";
    private static final String HADOOP_VERSION="2.8.4";
    private static final String EC2_KEY_NAME="ptitsyn_bgu";
    private static final String REGION="us-west-2";
    private static final String SUBNET_ID="subnet-0df26357";
    private static final int NUM_OF_INSTANCES=20;
    private static final String INSTANCE_TYPE= InstanceType.M4Large.toString();
    public static void main(String[] args) {
//        System.out.println(args[0]);
//        System.out.println(args[1]);
//        System.exit(1);
        BasicConfigurator.configure();
        AmazonElasticMapReduce mapReduce =
                AmazonElasticMapReduceClientBuilder
                        .standard()
                        .withRegion(DEFAULT_REGION)
                        .build();

        System.out.println("Connected to EMR");
//        String language = args[0];
//        String localAggregation = args[1];
//        String oneGramURL, twoGramURL;
//
//        if(language.equals("eng")){
//            oneGramURL=ONE_GRAM_URL_ENG;
//            twoGramURL=TWO_GRAM_URL_ENG;
//        }
//        else{
//            oneGramURL=ONE_GRAM_URL_HEB;
//            twoGramURL=TWO_GRAM_URL_HEB;
//        }

        // Configure step 0 to enable debugging
        StepConfig stepConfigDebug = new StepConfig()
                .withName("Enable Debugging")
                .withActionOnFailure("TERMINATE_JOB_FLOW")
                .withHadoopJarStep(new HadoopJarStepConfig()
                        .withJar("command-runner.jar")
                        .withArgs("state-pusher-script"));

        // Configure first step, input is 1-gram, 2-gram
        StepConfig stepConfig1 = new StepConfig()
                .withName("StepOne")
                .withActionOnFailure("TERMINATE_JOB_FLOW")
                .withHadoopJarStep(new HadoopJarStepConfig()
                        .withJar(Constants.STEP_ONE_JAR)
                        .withMainClass("PairExtractor")
                        .withArgs(args[0], args[1])
                        );

        JobFlowInstancesConfig instances = new JobFlowInstancesConfig()
                .withInstanceCount(NUM_OF_INSTANCES)
                .withMasterInstanceType(INSTANCE_TYPE)
                .withSlaveInstanceType(INSTANCE_TYPE)
                .withHadoopVersion(HADOOP_VERSION)
                .withEc2KeyName(EC2_KEY_NAME)
                .withKeepJobFlowAliveWhenNoSteps(true);


        // Create a flow request including all the steps
        RunJobFlowRequest runFlowRequest = new RunJobFlowRequest()
                .withName("PairExtractor")
                .withInstances(instances)
                .withSteps(stepConfig1)
                .withLogUri(Constants.LOG_OUT)
                .withReleaseLabel(EMR_RELEASE_LABEL);
        runFlowRequest.setJobFlowRole("EMR_EC2_DefaultRole");
        runFlowRequest.setServiceRole("EMR_DefaultRole");

        RunJobFlowResult runJobFlowResult = mapReduce.runJobFlow(runFlowRequest);
        System.out.println(runJobFlowResult);
    }
}

