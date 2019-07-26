package myconstants;

import org.apache.commons.lang3.StringUtils;

public class Constants {
    public enum KeyType {
        //used for total word count
        Decade,
        //used for One word count
        OneGram,
        //used for applying the formula at stage 2
        BiGram,
        Undefined,
    }

    public static final String BUCKET_NAME = "michael-dror-distrebuted";
    public static final String BUCKET_URL = "s3n://" + BUCKET_NAME ;
    public static final String LOG_DIR = BUCKET_URL + "logs/";

    //amazon
    public static final String DEFAULT_REGION = "us-east-1";
    public static final String ASS2ROOT = BUCKET_URL + "/public/ass2";
    public static final String JARS = ASS2ROOT + "/jars";
    public static final String STEP_ONE_JAR = JARS + "/MapReduceJob.jar";
    public static final String STEP_ONE_OUTPUT = ASS2ROOT + "/step1";
    public static final String LOG_OUT = ASS2ROOT + "/logs";

    public static String RELATIVE_PMI = "RelativePMI";
    public static String MIN_PMI = "MinPMI";
    public static String STEP_ONE = "step1";
    public static String STEP_TWO = "step2";
    public static String STEP_THREE = "step3";
    public static String TAB_CHAR = "\t";
    private static final String ENGLISH_CORPUS="s3://datasets.elasticmapreduce/ngrams/books/20090715/eng-us-all/2gram/data";
    public static final String STEP_ONE_INPUT = ENGLISH_CORPUS;//"s3://michael-dror-distrebuted/public/test_input/2gram";
    public static final String STEP_TWO_INPUT = STEP_ONE_OUTPUT;
    public static final String STEP_TWO_OUTPUT = ASS2ROOT + "/" + STEP_TWO;
    public static final String STEP_THREE_INPUT = STEP_TWO_OUTPUT;
    public static final String STEP_THREE_OUTPUT = ASS2ROOT + "/" + STEP_THREE;
    public static final String EMR_RELEASE_LABEL = "emr-5.16.0";
    public static final String HADOOP_VERSION = "2.8.4";
    public static final String EC2_KEY_NAME = "dsps-ass1";
    public static final String REGION = "us-west-2";
    public static final String SUBNET_ID = "subnet-0df26357";
    public static final int NUM_OF_INSTANCES = 20;
//        public static final String INSTANCE_TYPE= InstanceType.M4Large.toString();


    public static final String ONE_GRAM_URL_HEB = "s3://datasets.elasticmapreduce/"
            + "ngrams/books/20090715/heb-all/1gram/data";
    public static final String TWO_GRAM_URL_HEB = "s3://datasets.elasticmapreduce/"
            + "ngrams/books/20090715/heb-all/2gram/data";
    public static final String ONE_GRAM_URL_ENG = "s3://datasets.elasticmapreduce/"
            + "ngrams/books/20090715/eng-us-all/1gram/data";
    public static final String TWO_GRAM_URL_ENG = "s3://datasets.elasticmapreduce/"
            + "ngrams/books/20090715/eng-us-all/2gram/data";
}