# MapReduce performance evaluation on EC2 (HDFS HA) vs EMR  
## Application - Online spam reviewers detection  
### CSC 591(002) Data Intensive Computing Fall ‘17  
**Team 3  
Divya Guttikonda (dguttik), Nithya Kumar (nkumar8), Sahithi Guddeti (sguddet)**  

**Abstract**  
Online product reviews are becoming prevalent due to the veracity of reviews provided by the users. This particularly helps a lot of users in their decision-making process during product purchase, for example Amazon product reviews. One major problem that exists is ‘opinion spamming’ where fraudulent reviewers write manipulative spam reviews to promote or demote a product. Since this is a large scale distributed problem, analyzing and handling huge volumes of data is limited by storage and cost constraints. In this project, we have dealt with the above-mentioned problem by proposing a MapReduce application framework to detect online spam reviewers and by building & comparing two infrastructure models viz., Hadoop HA on EC2 (with EBS) and EMR (with S3) to handle huge volumes of input data. Our project successfully detects spammers on different product reviews big datasets [1] and also evaluates the performance of MapReduce on the built EC2 and EMR clusters. The performance evaluation is done considering the aspects of application execution time, instance costs, storage costs, number of input splits, number of map and reduce tasks, and the number of output file partitions. Our conclusions are focused on the optimality and cost-effectiveness of our MapReduce application in detecting spammers and on the infrastructure decision to be taken under different data-intensive scenarios.  

[Project Claims](https://github.ncsu.edu/CSC591-DIC/Team3Project/blob/master/Claims.md)

[Project Report](https://github.ncsu.edu/CSC591-DIC/Team3Project/blob/master/Final%20Paper%20DIC%20dguttik_nkumar8_sguddet_v1.pdf)  

[Project Presentation](https://github.ncsu.edu/CSC591-DIC/Team3Project/blob/master/CSC%20591%20DIC%20Project%20Presentation%20.pdf)

[Hadoop cluster over EC2 instances](https://github.ncsu.edu/CSC591-DIC/Team3Project/tree/master/Hadoop%20cluster%20setup)

[EMR Architecture](https://github.ncsu.edu/CSC591-DIC/Team3Project/tree/master/Images)  

[Spam Detection MapReduce Application Source Code](https://github.ncsu.edu/CSC591-DIC/Team3Project/tree/master/src/com/spamreviews/detection)

[Hadoop over EC2 results](https://github.ncsu.edu/CSC591-DIC/Team3Project/tree/master/Screenshots/EC2-HDFS-%20Results)

[EMR results](https://github.ncsu.edu/CSC591-DIC/Team3Project/tree/master/Screenshots/EMR%20Results)


