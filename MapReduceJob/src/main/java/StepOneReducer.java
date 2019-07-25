import myconstants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import utils.BigramKey;

import java.io.IOException;

//result is w1 w2 \tdacade\tocuurance\t c(w1)
public class StepOneReducer extends Reducer<BigramKey, IntWritable, Text, Text> {
    private int wordCounter;

    @Override
    protected void reduce(BigramKey key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int total = 0;
        for (IntWritable i : values) {
            total = total + i.get();
        }
        switch (key.getType()) {
            case OneGram:
                wordCounter = total;
                break;
            case BiGram:
                context.getCounter("decades",""+key.getDecade()).increment(total);
                context.write(new Text(key.getLeft() +
                        StringUtils.SPACE +
                        key.getRight() +
                        Constants.TAB_CHAR +
                        key.getDecade()
                ), new Text("" + total + Constants.TAB_CHAR + wordCounter));
                break;
        }
    }
}
