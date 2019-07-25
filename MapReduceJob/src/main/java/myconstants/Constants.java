package myconstants;

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

    public static String RELATIVE_PMI = "MinPMI";
    public static String MIN_PMI = "RelativePMI";
    public static String STEP_ONE = "step1";
    public static String STEP_TWO = "step2";
    public static String TAB_CHAR = "\t";
    public static final String STEP_ONE_INPUT = "src/input/2gram";
    public static final String STEP_ONE_OUTPUT = "src/step1_output";
    public static final String STEP_TWO_INPUT = STEP_ONE_OUTPUT + "/part-r-00000";
    public static final String STEP_TWO_OUTPUT = "c:/hadoop/step2_output";
    public static final String STEP_THREE_INPUT = STEP_TWO_OUTPUT + "/part-r-00000";
    public static final String STEP_THREE_OUTPUT = "src/step3_output";

    public static final String EMR_RELEASE_LABEL="emr-5.16.0";
    public static final String HADOOP_VERSION="2.8.4";
    public static final String EC2_KEY_NAME="dsps-ass1";
    public static final String REGION="us-west-2";
    public static final String SUBNET_ID="subnet-0df26357";
    public static final int NUM_OF_INSTANCES=20;
//    public static final String INSTANCE_TYPE= InstanceType.M4Large.toString();

    public static final String BUCKET_NAME="assignment2-dsps-2";
    public static final String BUCKET_URL= "s3n://"+BUCKET_NAME+"/";
    public static final String LOG_DIR=BUCKET_URL+"logs/";

    public static final String ONE_GRAM_URL_HEB="s3://datasets.elasticmapreduce/"
            + "ngrams/books/20090715/heb-all/1gram/data";
    public static final String TWO_GRAM_URL_HEB="s3://datasets.elasticmapreduce/"
            + "ngrams/books/20090715/heb-all/2gram/data";
    public static final String ONE_GRAM_URL_ENG="s3://datasets.elasticmapreduce/"
            + "ngrams/books/20090715/eng-us-all/1gram/data";
    public static final String TWO_GRAM_URL_ENG="s3://datasets.elasticmapreduce/"
            + "ngrams/books/20090715/eng-us-all/2gram/data";

    public static final String STEP1_JAR= BUCKET_URL+"jars/step1.jar";
    public static final String STEP2_JAR= BUCKET_URL+"jars/step2.jar";
    public static final String STEP3_JAR= BUCKET_URL+"jars/step3.jar";

    public static final String STEP1_OUTPUT=BUCKET_URL+"step1output/";
    public static final String STEP2_OUTPUT=BUCKET_URL+"step2output/";
    public static final String STEP3_OUTPUT=BUCKET_URL+"step3output/";
}
