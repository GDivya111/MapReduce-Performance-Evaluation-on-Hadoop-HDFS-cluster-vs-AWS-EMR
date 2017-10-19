##
Initially, 4 AWS EC2 instances are taken for the project under AWS free tier.
Each Instance Specifications: 
a.	Ubuntu Server 16.04 LTS (HVM), SSD Volume Type
b.	64-bit
c.	 RAM - 1GB
d.	Storage - 8GB
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
   
   HADOOP_CONF=/home/ubuntu/hadoop/conf
   HADOOP_PREFIX=/home/ubuntu/hadoop
   JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64
   PATH=$PATH:$HADOOP_PREFIX/bin
   
   check if they are set by running 'source ~/.profile' , 'set env' and echo $HADOOP_CONF on the shell
 
 3. Connect Master,SSN and slave nodes
   *copy generated public key file hadoopec2cluster.pem to '/home/ubuntu/hadoop'
   *In every node run 'ssh-agent /bin/sh'
   *ssh-add haddopec2cluster.pem
   *Add permissions using 'chmod 644 .ssh/authorized_keys' && 'chmod 400 hadoopec2cluster.pem'
   *Connect 

    
  

