package onegramjob;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import javax.annotation.Nonnull;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class OnegramKey implements WritableComparable<OnegramKey> {
    private IntWritable decade;
    private Text left;

    public OnegramKey() {
        this(-1, "*");
    }

    public OnegramKey(int decade, String left) {
        this.decade = new IntWritable(decade);
        this.left = new Text(left);
    }



    public String getLeft() {
        return left.toString();
    }

    public int compareTo(@Nonnull OnegramKey o) {
        if (this.getDecade()==o.getDecade()){
            if(this.left!=null && o.left!=null)
                return this.left.compareTo(o.left);
            return -1;
        }
        return this.getDecade()>o.getDecade()?1:-1;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        left.write(dataOutput);
        decade.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        left.readFields(dataInput);
        decade.readFields(dataInput);
    }

    public int getDecade() {
        return decade.get();
    }
}
