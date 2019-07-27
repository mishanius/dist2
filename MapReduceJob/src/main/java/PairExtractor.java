import myconstants.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import stepthree.FinalBigramKey;
import stepthree.StepThreeMapper;
import stepthree.StepThreeReducer;
import steptwo.StepTwoMapper;
import steptwo.StepTwoReducer;
import steptwo.SteptwoPartitioner;
import utils.BigramKey;

public class PairExtractor {
    private static final Log logger = LogFactory.getLog(PairExtractor.class);
    private static Job job;
    private static Job job2;
    private static Job job3;
    public static void main(String[] args) throws Exception {
        logger.info(String.format("PairExtractor has started args are:%s, %s",args[1], args[2]));
        Configuration conf = new Configuration();
        job = Job.getInstance(conf, Constants.STEP_ONE);
        job.setJarByClass(PairExtractor.class);
        job.setMapperClass(StepOneMapper.class);
        job.setCombinerClass(StepOneCombiner.class);
        job.setPartitionerClass(StepOnePartitioner.class);
        job.setMapOutputKeyClass(BigramKey.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setReducerClass(StepOneReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BigramKey.class);
        job.setInputFormatClass(SequenceFileInputFormat.class);
        FileInputFormat.setInputPaths(job,new Path(Constants.STEP_ONE_INPUT));
        job.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job, new Path(Constants.STEP_ONE_OUTPUT));
        if (!job.waitForCompletion(true)) {
            System.exit(1);
        }
        logger.info("Job 1 is completed");
        job2 = Job.getInstance(conf, Constants.STEP_TWO);
        job.getCounters().getGroup("decades").forEach(PairExtractor::setConfig);
        job2.setJarByClass(PairExtractor.class);
        job2.setMapperClass(StepTwoMapper.class);
        job2.setCombinerClass(StepOneCombiner.class);
        job2.setPartitionerClass(SteptwoPartitioner.class);
        job2.setMapOutputKeyClass(BigramKey.class);
        job2.setMapOutputValueClass(IntWritable.class);
        job2.setReducerClass(StepTwoReducer.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(BigramKey.class);
        job2.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.setInputPaths(job2,new Path(Constants.STEP_TWO_INPUT));
        job2.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job2, new Path(Constants.STEP_TWO_OUTPUT));
        if (!job2.waitForCompletion(true)) {
            System.exit(1);
        }
        logger.info("Job 2 is completed");
        job3 = Job.getInstance(conf, Constants.STEP_TWO);
        job3.getConfiguration().set(Constants.MIN_PMI,args[1]);
        job3.getConfiguration().set(Constants.RELATIVE_PMI,args[2]);
        job2.getCounters().getGroup("rnpmi").forEach(PairExtractor::setConfigRpmi);
        job3.setJarByClass(PairExtractor.class);
        job3.setMapperClass(StepThreeMapper.class);
        job3.setReducerClass(StepThreeReducer.class);
        job3.setNumReduceTasks(1);
        job3.setMapOutputKeyClass(FinalBigramKey.class);
        job3.setMapOutputValueClass(Text.class);
        job3.setOutputKeyClass(Text.class);
        job3.setOutputValueClass(Text.class);
        job3.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.setInputPaths(job3,new Path(Constants.STEP_THREE_INPUT));
        job3.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job3, new Path(Constants.STEP_THREE_OUTPUT));
        if (!job3.waitForCompletion(true)) {
            System.exit(1);
        }

    }

    private static void setConfig(Counter c){
        job2.getConfiguration().setLong(c.getName(), c.getValue());
    }

    private static void setConfigRpmi(Counter c){
        job3.getConfiguration().set(c.getName(), ""+(((double)c.getValue())/10000));
    }
}
