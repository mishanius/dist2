package steptwo;

import myconstants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import utils.BigramKey;

import java.io.IOException;

//result is w1 w2 \t dacade\t ocuurance\t c(w1)\t c(w2)  \pmi
public class StepTwoReducer extends Reducer<BigramKey, IntWritable, Text, Text> {
    private int word2Counter;
    private long totalIndacade;
    private double pmi = -1;

    @Override
    protected void reduce(BigramKey key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int total = 0;
        double npmi = -1;
        for (IntWritable i : values) {
            total = total + i.get();
        }

        switch (key.getType()) {
            case OneGram:
                word2Counter = total;
                break;
            case BiGram:
                totalIndacade = context.getConfiguration().getLong(""+key.getDecade(), 0);
                pmi = calculatePmi(key.getW1CountAsInt(), word2Counter, total);
                npmi = calculateNPmi(total);
                context.getCounter("rnpmi",""+key.getDecade()).increment((long)(npmi*10000));
                context.write(new Text(key.getRight() +
                        StringUtils.SPACE +
                        key.getLeft() +
                        Constants.TAB_CHAR +
                        key.getDecade()
                ), new Text("" +
                        Constants.TAB_CHAR +
                        npmi
                ));
        }
    }

    private double calculatePmi(int countW1, int countW2, int pairCount) {
        return Math.log(pairCount) + Math.log(this.totalIndacade) - Math.log(countW1) - Math.log(countW2);
    }

    private float calculateProbobility(int count) {
        return (float)count / this.totalIndacade;
    }

    private double calculateNPmi(int pairCount) {
        return pmi / (-Math.log(calculateProbobility(pairCount)));
    }

    private double calculateNPmi(int countW1, int countW2, int pairCount) {
        pmi = calculatePmi(countW1, countW2, pairCount);
        return calculateNPmi(pairCount);
    }
}
