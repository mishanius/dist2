package utils;

import myconstants.Constants;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;


import javax.annotation.Nullable;
import javax.annotation.Nonnull;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class BigramKey implements WritableComparable<BigramKey> {
    private IntWritable decade;
    private Text left;
    private Text right;
    private Comparable primaryIndex;
    private Comparable secondaryIndex;
    private IntWritable type;

    private IntWritable totalInDecade;

    private Text w1Count;
    public BigramKey() {
        this(-1,Constants.KeyType.Undefined, "#undefined#", null);
    }
    public BigramKey(int decade,@Nonnull Constants.KeyType type, @Nonnull String left, @Nullable String right) {
        this.decade = new IntWritable(decade);
        this.left = new Text(left);
        this.right = new Text(right==null?left:right);
        this.type = new IntWritable(type.ordinal());
        this.primaryIndex = this.left; //this is the default
        this.secondaryIndex = this.right; //this is the default
        this.w1Count = new Text("");
        this.totalInDecade = new IntWritable(1);
    }

    public BigramKey(int decade, String left, @Nullable String right) {
        this(decade, Constants.KeyType.BiGram, left, right);
    }

    public BigramKey(int decade, String left, String right, String w1Count) {
        this(decade, Constants.KeyType.BiGram, left, right);
        this.w1Count = new Text(w1Count);
    }

    public BigramKey(int decade, String left) {
        this(decade, Constants.KeyType.OneGram, left, null);
    }

    public BigramKey(int decade) {
        this(decade, Constants.KeyType.Decade, "" + decade, null);
    }

    public String getLeft() {
        return left.toString();
    }

    @Nonnull
    public String getRight() {
        return right.toString();
    }

    public void swapIndexes(){
        Comparable temp = this.primaryIndex;
        this.primaryIndex=this.secondaryIndex;
        this.secondaryIndex = temp;
    }

    public Comparable getPrimaryIndex() {
        return primaryIndex;
    }

    public Comparable getSecondaryIndex() {
        return secondaryIndex;
    }

    public Constants.KeyType getType() {
        return Constants.KeyType.values()[type.get()];
    }

    public int compareTo(@Nonnull BigramKey o) {
        if (this.getDecade()==o.getDecade()){
            if(this.getType()==Constants.KeyType.Decade || o.getType()==Constants.KeyType.Decade) {
                return this.getType().compareTo(o.getType());
            }
            if (this.getPrimaryIndex().equals(o.getPrimaryIndex())){
                if(this.getType().compareTo(o.getType())!=0) {
                    return this.getType().compareTo(o.getType());
                }
                else if(this.getType()==Constants.KeyType.BiGram){
                    return this.getSecondaryIndex().compareTo(o.getSecondaryIndex());
                }
                else{
                    return 0;
                }
            }
            return this.getLeft().compareTo(o.getLeft());
        }
        return this.getDecade()>o.getDecade()?1:-1;
    }

    public Text getW1Count() {
        return w1Count;
    }

    public Integer getW1CountAsInt() {
        return Integer.parseInt(w1Count.toString());
    }



    @Override
    public void write(DataOutput dataOutput) throws IOException {
        this.left.write(dataOutput);
        this.right.write(dataOutput);
        this.decade.write(dataOutput);
        this.type.write(dataOutput);
        this.getW1Count().write(dataOutput);
        this.totalInDecade.write(dataOutput);
    }



    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.left.readFields(dataInput);
        this.right.readFields(dataInput);
        this.decade.readFields(dataInput);
        this.type.readFields(dataInput);
        this.getW1Count().readFields(dataInput);
        this.totalInDecade.readFields(dataInput);
    }

    public Integer getTotalInDecade() {
        return totalInDecade.get();
    }

    public void setTotalInDecade(Integer totalInDecade) {

        this.totalInDecade = new IntWritable(totalInDecade);
    }

    public int getDecade() {
        return decade.get();
    }

}