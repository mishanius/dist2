# Usage
1. Create folders in S3 and update Constants.java:
    - create /public/ass2 folders set ASS2ROOT acordingly
    - create jars folder 
    - create logs folder
2. create the following roles in IAM : 
    - EMR_DefaultRole: with AmazonElasticMapReduceRole policy
    - EMR_EC2_DefaultRole: with AmazonElasticMapReduceforEC2Role  policy
3. compile the modules in the project to get MapReduceJob.jar and StartEmrJob.jar
4. upload MapReduceJob.jar to your jars folder in S3
  ## To run the cluster
```
    java -jar StartEmrJob PairExtractor <npmi threshold> <rnpmi threshold>
```
# Bad Examples
1. Califor nia (spalling mistake)
2. AMITAI ETZIONI (other language and name)
3. BRAD PITT (a name)
4. Vulvar vestibulitis (good)
5. Russkaia mysl (other language)
6. chek mat (spelling mistake)
7. CATAL HUYUK
8. Yesh Gvul (other language)
9. COOMe COOMe
10. Yosefa Loshitzky
11. Oneg Shabat
12. Yesh Gvul

# Good Examples
1. Ear Plugs
2. Pizza Hut
3. HELLO HELLO
4. Hello World
5. WALK ACROSS
6. EXPENSES PAID
7. SAVE LIVES 
8. Whipping Boy
9. PROPAGATION CONSTANT
10. MARINE ENVIRONMENT
