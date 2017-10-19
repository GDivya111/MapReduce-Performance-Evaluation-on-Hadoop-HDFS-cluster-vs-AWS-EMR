##
Initially, 4 AWS EC2 instances are taken for the project under AWS free tier.
Each Instance Specifications: 
*	Ubuntu Server 16.04 LTS (HVM), SSD Volume Type
*	64-bit
*	 RAM - 1GB
*	Storage - 8GB
These instances are used for serving as 1 Master node,1 Secondary name node, 2 Slave data nodes.
Generate a key pair(hadoopec2cluster.pem) file during instance configuration for created group of instances 
Putty gen is used to generate a private key(hadoopec2cluster.ppk) for these instances through a public key and used to connect to instances using ssh from putty.
username : ubuntu, password: empty
1. Hostnames of each instance are noted down and added in '/etc/hosts' file of the system.
2.Run the below command script in every unstance to install hadoop
  * sudo apt-get update
  * run 'java -verssion ' to check if java is installed , if not install
  * Install java using 'sudo apt install openjdk-amd64'
  * Download hadoop 2.8.1 from Apache - 'wget http://apache.claz.org/hadoop/common/hadoop-2.8.1/hadoop.2.8.1.tar.gz'
  * tar -xzvf hadoop-2.8.1.tar.gz
  * move created folder to '/home/ubuntu/hadoop' folder
  * set environment variables in '~/.profile' file by adding following lines at the last
    'HADOOP_CONF=/home/ubuntu/hadoop/conf' <br/>
    'HADOOP_PREFIX=/home/ubuntu/hadoop' <br/>
    'JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64' <br/>   
    'PATH=$PATH:$HADOOP_PREFIX/bin' <br/>
     
   check if they are set by running 'source ~/.profile' , 'set env' and echo $HADOOP_CONF on the shell
 
 3. Connect Master,SSN and slave nodes
   * Copy generated public key file hadoopec2cluster.pem to '/home/ubuntu/hadoop'
   * In every node run 'ssh-agent /bin/sh'
   * ssh-add haddopec2cluster.pem
   * Add permissions using 'chmod 644 .ssh/authorized_keys' && 'chmod 400 hadoopec2cluster.pem'
   * Connect from Master to SSN and slave nodes using 'ssh ubuntu@hostname'
     Note : hostnames of ec2 instances are changed continously as public DNS option is taken
 
 4. Cluster set up
 * sudo vi $HADOOP_CONF/hadoop-env.sh
 * add line 'export JAVA_HOME= /usr/lib/jvm/java-1.8.0-openjdk-amd64' in the open file
 * Save and exit
 * sudo vi $HADOOP_CONF/core-site.xml
 * Add two properties to name the file system and directory , save the file 
   ```xml
   <configuration>
     <property>
        <name>fs.default.name</name>
         <value>hdfs://ec2-18-221-252-19.us-east-2.compute.amazonaws.com</value>
     </property>

     <property>
        <name>hadoop.tmp.dir</name>
        <value>/home/ubuntu/hdfstmp</value>
     </property>
   </configuration>
  ```
  * configure hdfs-site file - sudo vi $HADOOP_PREFIX/hdfs-site.xml
  * Add properties to enable replication factor and set it to 2 as we have only 2 data nodes currently
  ```xml
    <configuration>
      <property>
          <name>dfs.replication</name>
          <value>2</value>
      </property>
      <property>
           <name>dfs.permissions</name>
           <value>false</value>
      </property>
   </configuration>
  ```
  * In open mapred-site.xml file to set map reduce jobs
  * add following properties in the file $HADOOP_CONF/mapred-site.xml
  ```xml
    <configuration>
      <property>
        <name>mapred.job.tracker</name>
        <value>hdfs://ec2-18-221-252-19.us-east-2.compute.amazonaws.com:8021</value>
    </property>
   </configuration>
```
     hdfs://ec2-18-221-252-19.us-east-2.compute.amazonaws.com - location of job tracker (Master node) and listening port (8021)
     
   * copy above configured files to other instsnces using the command 'scp hadoop-env.sh core-site.xml hdfs-site.xml mapred-site.xml 
     ubuntu@hostname:/home/ubuntu/hadoop/conf'
   * Add Secondary name node daemon by adding SSN hostname in master machine's $HADOOP_CONF/masters file
   * Similarly configure slaves in Master's $HADOOP_CONF/slaves file by adding datanodes hostnames
   * Copy above files to SSN
   * masters file in slaves will be empty and their slaves file is updated wit host name of other slave node
   * To start hadoop daaemon, run below commands<br/>
       'hadoop HadoopNameNode -format'<br/>
       'cd $HADOOP_CONF' <br/>
       'start-all.sh'<br/>
   * SSN , data nodes are started running
